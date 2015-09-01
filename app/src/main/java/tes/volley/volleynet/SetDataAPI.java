package tes.volley.volleynet;

/**
 * Created by Kucing Imut on 8/24/15.
 */
public class SetDataAPI {


    public static String getDataParamURL(String limit_page, String API_link) {

        String alamatAPIServer = Konstans.URL_SERVER + API_link + limit_page;

        return alamatAPIServer;
    }







}
