package tes.volley.gsonparser;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/**
 * Created by Kucing Imut on 8/25/15.
 */
public class JaksonRekuest<T> extends Request<T> {


    private final ObjectMapper objekmapper;

    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;


    public JaksonRekuest(int method, String url, Class<T> clazz, Map<String, String> headers,
                         Response.Listener<T> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);

        objekmapper = new ObjectMapper();
        objekmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objekmapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }



    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(

                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(

                    objekmapper.readValue(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));


        } catch (Exception e) {

            return Response.error(new ParseError(e));

        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
