package tes.volley.gsonparser;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
//import java.util.HashMap;
import java.util.Map;

import okio.Buffer;

/**
 * Created by Gulajava Ministudio on 9/1/15.
 */



public class MultipartsRequest<T> extends Request<T> {

    private final Map<String, String> headers;
    private Map<String,File> fileUploads;
    private Map<String,String> stringUploads;
    private final Response.Listener<T> listener;
    private Class<T> clazz;

    private RequestBody requestBody = null;

    private ObjectMapper objekmapper;


//    public MultipartsRequest(String url,
//                                    Response.Listener<T> listener,
//                                    Response.ErrorListener errorListener) {
//        super(Method.POST, url, errorListener);
//
//        this.headers = new HashMap();
//        this.fileUploads = new HashMap();
//        this.stringUploads = new HashMap();
//        this.listener = listener;
//    }

    public MultipartsRequest(String url,
                             Class<T> classs,
                             Map<String, String> headers,
                             Map<String, File> fileUploads,
                             Map<String, String> stringUploads,
                             Response.Listener<T> listener,
                             Response.ErrorListener errorListener) {

        super(Method.POST, url, errorListener);
        this.headers = headers;
        this.fileUploads = fileUploads;
        this.stringUploads = stringUploads;
        this.listener = listener;
        this.clazz = classs;


        objekmapper = new ObjectMapper();
        objekmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objekmapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }



    public MultipartsRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }



    public MultipartsRequest addStringPart(String key, String value) {
        stringUploads.put(key, value);
        return this;
    }



    public MultipartsRequest addFilePart(String key, File file) {
        fileUploads.put(key, file);
        return this;
    }



    private RequestBody buildMultipartEntity() {
        if (requestBody == null) {
            MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            for (String key : stringUploads.keySet()) {
                String value = stringUploads.get(key);
                multipartBuilder.addFormDataPart(key, value);
            }

            for (String key : fileUploads.keySet()) {

                File value = fileUploads.get(key);
                String name = value.getName();
                String contentType = URLConnection.guessContentTypeFromName(name);

                multipartBuilder.addFormDataPart(key, name,
                        RequestBody.create(MediaType.parse(contentType), value));
            }

            requestBody = multipartBuilder.build();
        }

        return requestBody;
    }




    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }



    @Override
    public String getBodyContentType() {
        return buildMultipartEntity().contentType().toString();
    }



    @Override
    public byte[] getBody() throws AuthFailureError {
        Buffer buffer = new Buffer();
        try {
            buildMultipartEntity().writeTo(buffer);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return buffer.readByteArray();
    }



    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        // TODO Auto-generated method stub
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
