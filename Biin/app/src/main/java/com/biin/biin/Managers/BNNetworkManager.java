package com.biin.biin.Managers;

/**
 * Created by ramirezallan on 5/2/16.
 */
public class BNNetworkManager {

    private static BNNetworkManager ourInstance = new BNNetworkManager();
    private static boolean production = false;

    private static String URL_BASE_DEV = "https://dev-biin-backend.herokuapp.com";
    private static String URL_BASE = "https://www.biin.io";

    private static String URL_GET_BIINIES = "/mobile/biinies";
    private static String URL_GET_INITIALDATA = "/mobile/initialData";

    private static String URL_GET_BIINIES_TEST = URL_GET_BIINIES + "/14804ad3-9cfe-419e-a360-262551331491";
    private static String URL_GET_INITIALDATA_TEST = URL_GET_INITIALDATA + "/14804ad3-9cfe-419e-a360-262551331491/9.84076469672071/-83.9502626783081";

    protected static BNNetworkManager getInstance() {
        return ourInstance;
    }

    private BNNetworkManager() {
    }

    public String getUrlBase() {
        if(production){
            return URL_BASE;
        }else{
            return URL_BASE_DEV;
        }
    }

    public String getUrlGetBiinies() {
        return getUrlBase() + URL_GET_BIINIES;
    }

    public String getUrlGetInitialData() {
        return getUrlBase() + URL_GET_INITIALDATA;
    }

    public String getUrlGetBiiniesTest() {
        return getUrlBase() + URL_GET_BIINIES_TEST;
    }

    public String getUrlGetInitialDataTest() {
        return getUrlBase() + URL_GET_INITIALDATA_TEST;
    }


}
