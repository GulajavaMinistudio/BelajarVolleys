package tes.volley.volleynet;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kucing Imut on 8/24/15.
 */
public class VolleySingletons {


    public static final String TAG = VolleySingletons.class
            .getSimpleName();

    private static VolleySingletons mVolleySingletons;
    private RequestQueue mRequestQueue;
    private static Context mContext;


    private VolleySingletons(Context context) {

        mContext = context;
        mRequestQueue = getmReqQueue();

    }


    public static synchronized VolleySingletons getInstance(Context context) {

        if (mVolleySingletons == null) {
            mVolleySingletons = new VolleySingletons(context);
        }

        return mVolleySingletons;
    }


    public RequestQueue getmReqQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkhttpStack());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmReqQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getmReqQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
