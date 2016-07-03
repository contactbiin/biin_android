package com.biin.biin.Managers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ramirezallan on 5/2/16.
 */
public class BNNetworkManager {

    private static String encoding = "US-ASCII";

    private static BNNetworkManager ourInstance = new BNNetworkManager();
    private static boolean production = false;

    private static String URL_BASE_DEV = "https://dev-biin-backend.herokuapp.com";
    private static String URL_BASE = "https://www.biin.io";

    private static String URL_AUTH_BIINIE = "/auth";

    private static String URL_SITES = "/requestSites/0";
    private static String URL_CATEGORIES = "/requestElementsForCategory";
    private static String URL_SHOWCASES = "/requestElementsForShowcase";

    private static String URL_BIINIE = "/mobile/biinies";
    private static String URL_INITIALDATA = "/mobile/initialData";

    private static String URL_PRIVACY_POLICY = "/privacy.html";
    private static String URL_TERMS_OF_USE = "/termsofuse.html";

    private static String URL_FOLLOW = "/follow";
    private static String URL_UNFOLLOW = "/unfollow";

    private static String URL_LIKE = "/like";
    private static String URL_UNLIKE = "/unlike";

    protected static BNNetworkManager getInstance() {
        return ourInstance;
    }

    private BNNetworkManager() {
    }

    public String getPrivacyPolicies(String lang){
        String url = URL_BASE + "/" + lang + "/" + URL_PRIVACY_POLICY;
        return url;
    }

    public String getTermsOfUse(String lang){
        String url = URL_BASE + "/" + lang + "/" + URL_TERMS_OF_USE;
        return url;
    }

    public String getUrlBase() {
        if(production){
            return URL_BASE;
        }else{
            return URL_BASE_DEV;
        }
    }

    public String getUrlBiinie(String identifier) {
        return getUrlBase() + URL_BIINIE + "/" + identifier;
    }

    public String getUrlInitialData(String identifier, String location) {
        return getUrlBase() + URL_INITIALDATA + "/" + identifier + "/" + location;
    }

    public String getAuthUrl(String user, String pass){
        try{
            user = URLEncoder.encode(user.trim(), encoding);
            pass = URLEncoder.encode(pass.trim(), encoding);
        }catch (UnsupportedEncodingException e){ }

        String url = getUrlBase() + URL_BIINIE + URL_AUTH_BIINIE + "/" + user + "/" + pass;
        return url;
    }

    public String getRegisterUrl(String name, String lastName, String email, String pass, String gender, String date){
        try{
            name = URLEncoder.encode(name.trim(), encoding);
            lastName = URLEncoder.encode(lastName.trim(), encoding);
            email = URLEncoder.encode(email.trim(), encoding);
            pass = URLEncoder.encode(pass.trim(), encoding);
//            date = URLEncoder.encode(date, encoding);
        }catch (UnsupportedEncodingException e){ }

        String url = getUrlBase() + URL_BIINIE + "/" + name + "/" + lastName + "/" + email + "/" + pass + "/" + gender + "/" + date;
        return url;
    }

    public String getMoreSitesUrl(String identifier){
        String url = getUrlBiinie(identifier) + URL_SITES;
        return url;
    }

    public String getMoreCategoryElementsUrl(String identifier, String category){
        String url = getUrlBiinie(identifier) + URL_CATEGORIES + "/" + category + "/0";
        return url;
    }

    public String getMoreShowcaseElementsUrl(String identifier, String showcase, String site){
        String url = getUrlBiinie(identifier) + URL_SHOWCASES + "/" + site + "/" + showcase + "/1";
        return url;
    }

    public String getLikeUrl(String identifier, boolean like){
        String url = getUrlBiinie(identifier) + (like ? URL_LIKE : URL_UNLIKE);
        return url;
    }




    /*   temporal test data   */

    private static String URL_GET_BIINIES_TEST = URL_BIINIE + "/14804ad3-9cfe-419e-a360-262551331491";
    //    private static String URL_GET_INITIALDATA_TEST = URL_INITIALDATA + "/325b34bc-2691-4d41-9935-59e5bddd395c/9.73854872449546/-83.9987999326416";
    private static String URL_GET_INITIALDATA_TEST = URL_INITIALDATA + "/325b34bc-2691-4d41-9935-59e5bddd395c/";

    public String getUrlGetBiiniesTest() {
        return getUrlBase() + URL_GET_BIINIES_TEST;
    }

    public String getUrlGetInitialDataTest() {
        return getUrlBase() + URL_GET_INITIALDATA_TEST;
    }

    public static void setProduction(boolean production) {
        BNNetworkManager.production = production;
    }

    public static boolean isProduction() {
        return production;
    }
}
