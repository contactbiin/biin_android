package com.biin.biin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.biin.biin.Components.BNProgressViewHolder;
import com.biin.biin.Components.Listeners.IBNSitesLikeListener;
import com.biin.biin.Components.Listeners.IBNLoadMoreSitesListener;
import com.biin.biin.Utils.BNUtils;
import com.biin.biin.Utils.BNUtils.BNStringExtras;
import com.biin.biin.Entities.BNSite;
import com.biin.biin.R;
import com.biin.biin.SitesActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ramirezallan on 5/11/16.
 */
public class BNSiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BNSiteAdapter";
    private static Context context;

    private List<BNSite> sites;
    private ImageLoader imageLoader;
    private boolean showOthers = false;
    private IBNSitesLikeListener sitesListener;

    // load more start
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private IBNLoadMoreSitesListener onLoadMoreListener;
    // load more end

    public BNSiteAdapter(Context context, List<BNSite> sites, IBNSitesLikeListener sitesListener) {
        super();
        this.context = context;
        this.sites = sites;
        this.sitesListener = sitesListener;
        imageLoader = ImageLoader.getInstance();
    }

    public void setShowOthers(boolean showOthers){
        this.showOthers = showOthers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // load more start
        RecyclerView.ViewHolder holder = null;

        if(viewType == VIEW_PROG){

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            v.setLayoutParams(new RecyclerView.LayoutParams((int)(56 * BNUtils.getDensity()), (BNUtils.getWidth() / 2) + (int)(56 * BNUtils.getDensity())));
            holder = new BNProgressViewHolder(v);

        }else if(viewType == VIEW_ITEM) {
        // load more end

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bnsite_item_small, parent, false);
            v.setLayoutParams(new RecyclerView.LayoutParams((sites.size() == 1) ? BNUtils.getWidth() : (BNUtils.getWidth() / 2), (BNUtils.getWidth() / 2) + (int) (56 * BNUtils.getDensity())));
            holder = new BNSiteViewHolder(v);

        // load more start
        }
        // load more end

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        // load more start
        if(viewHolder instanceof BNProgressViewHolder){
            BNProgressViewHolder holder = (BNProgressViewHolder)viewHolder;
            holder.progressBar.setIndeterminate(true);
        }else if(viewHolder instanceof BNSiteViewHolder) {
            BNSiteViewHolder holder = (BNSiteViewHolder) viewHolder;
        // load more end

            final BNSite item = sites.get(position);
            TableRow.LayoutParams params = new TableRow.LayoutParams((sites.size() == 1) ? BNUtils.getWidth() : (BNUtils.getWidth() / 2), (BNUtils.getWidth() / 2) + (int) (56 * BNUtils.getDensity()));

            imageLoader.displayImage(item.getMedia().get(0).getUrl(), holder.ivSite);
            holder.ivSite.setBackgroundColor(item.getOrganization().getPrimaryColor());

            holder.rlSiteLabel.setBackgroundColor(item.getOrganization().getPrimaryColor());
            holder.tvSiteTitle.setText(item.getTitle());
            holder.tvSiteTitle.setTextColor(item.getOrganization().getSecondaryColor());
            holder.tvSiteSubtitle.setText(item.getSubTitle());
            holder.tvSiteSubtitle.setTextColor(item.getOrganization().getSecondaryColor());
            holder.cvSite.setLayoutParams(params);

            if (item.isUserLiked()) {
                holder.ivLike.setVisibility(View.GONE);
                holder.ivLiked.setVisibility(View.VISIBLE);
                holder.ivLiked.setColorFilter(item.getOrganization().getSecondaryColor());
                holder.ivLiked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // informar del unlike al site
                        sitesListener.onSiteUnliked(item.getIdentifier(), position);
                    }
                });
            } else {
                holder.ivLiked.setVisibility(View.GONE);
                holder.ivLike.setVisibility(View.VISIBLE);
                holder.ivLike.setColorFilter(item.getOrganization().getSecondaryColor());
                holder.ivLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // informar del like al site
                        sitesListener.onSiteLiked(item.getIdentifier(), position);
                    }
                });
            }

            holder.cvSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, SitesActivity.class);
                    SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(BNStringExtras.BNSite, item.getIdentifier());
                    editor.commit();
                    i.putExtra(BNStringExtras.BNShowOthers, showOthers);
                    context.startActivity(i);
                }
            });

        // load more start
        }
        // load more end

    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    // load more start
    @Override
    public int getItemViewType(int position) {
        return sites.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setLoaded(){
        loading = false;
    }

    public void setOnLoadMoreListener(IBNLoadMoreSitesListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public BNSiteAdapter(Context context, List<BNSite> sites, IBNSitesLikeListener sitesListener, RecyclerView recyclerView, final boolean isFavourites) {
        this(context, sites, sitesListener);

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMoreSites(isFavourites);
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    // load more end

    public BNSite getSite(int position){
        return sites.get(position);
    }

    public static class BNSiteViewHolder extends RecyclerView.ViewHolder{

        protected CardView cvSite;
        protected RelativeLayout rlSiteLabel;
        protected ImageView ivLike, ivLiked, ivSite;
        protected TextView tvSiteTitle, tvSiteSubtitle;

        public BNSiteViewHolder(View itemView) {
            super(itemView);

            cvSite = (CardView)itemView.findViewById(R.id.cvSite);
            rlSiteLabel = (RelativeLayout)itemView.findViewById(R.id.rlSiteLabel);

            ivSite = (ImageView)itemView.findViewById(R.id.ivSiteSmall);
            ivLike = (ImageView)itemView.findViewById(R.id.ivLike);
            ivLiked = (ImageView)itemView.findViewById(R.id.ivLiked);

            tvSiteTitle = (TextView)itemView.findViewById(R.id.tvSiteTitle);
            tvSiteSubtitle = (TextView)itemView.findViewById(R.id.tvSiteSubtitle);

            Typeface lato_regular = BNUtils.getLato_regular();
            Typeface lato_black = BNUtils.getLato_black();

            tvSiteTitle.setTypeface(lato_black);
            tvSiteSubtitle.setTypeface(lato_regular);
        }
    }

}
