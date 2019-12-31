package ir.maktabsharif.onlinshop.network;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class WooCommerceRequestQueue {

    private static WooCommerceRequestQueue sInstance;
    private static Context sContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static synchronized WooCommerceRequestQueue getInstance(Context context) {
        if (sInstance == null)
            sInstance = new WooCommerceRequestQueue(context);
        return sInstance;
    }

    private WooCommerceRequestQueue(Context context) {

        sContext = context.getApplicationContext();
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(sContext);
        return mRequestQueue;
    }
}
