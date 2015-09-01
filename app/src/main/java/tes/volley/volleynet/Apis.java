package tes.volley.volleynet;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

import tes.volley.gsonparser.ArrayBerita;
import tes.volley.gsonparser.GsonRekuest;

/**
 * Created by Kucing Imut on 8/25/15.
 */
public class Apis {





    public static GsonRekuest<ArrayBerita> getGsonRequestAllBerita (
            String urls,
            Response.Listener<ArrayBerita> listenerok,
            Response.ErrorListener errorListener
    ) {

        GsonRekuest<ArrayBerita> gsonrequest = new GsonRekuest<>(
                Request.Method.GET,urls,ArrayBerita.class, null,
                listenerok,errorListener
        );

        DefaultRetryPolicy retrycoba = new DefaultRetryPolicy(2500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        gsonrequest.setRetryPolicy(retrycoba);

        return gsonrequest;
    }






















}
