package ir.maktabsharif.onlinshop.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.Constants;

public class WooCommerceService {

    private static final String TAG = "WooCommerceService";

    private static WooCommerceService sInstance;

    public static WooCommerceService getInstance() {
        if (sInstance == null)
            sInstance = new WooCommerceService();
        return sInstance;
    }

    private MutableLiveData<List<Product>> mProductData = new MutableLiveData();
    private MutableLiveData<List<Category>> mCategoryData = new MutableLiveData();


    private WooCommerceService() {
    }

    public <T> JsonArrayRequest wooCommerceRequest(String TAG, Class<T> tClass, @NonNull String path, @NonNull String orderby) {

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, tClass);
        final JsonAdapter<List<T>> adapter = moshi.adapter(type);

        Response.Listener<JSONArray> listener = response -> {
            try {
                List<T> models = adapter.fromJson(response.toString());

                setLiveDataValue(tClass, models);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Response.ErrorListener errorListener = error -> Log.e(this.TAG, "onErrorResponse: ", error);

        JsonArrayRequest request = new JsonArrayRequest(getStringURL(path, orderby), listener, errorListener);
        request.setTag(TAG);

        return request;

    }

    private String getStringURL(@NonNull String path, @NonNull String orderby) {
        return Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath(path)
                .appendQueryParameter("consumer_key", Constants.CONSUMER_KEY)
                .appendQueryParameter("consumer_secret", Constants.CONSUMER_SECRET)
                .appendQueryParameter("orderby", orderby)
                .build()
                .toString();
    }

    public MutableLiveData<List<Product>> getProductData() {
        return mProductData;
    }

    public MutableLiveData<List<Category>> getCategoryData() {
        return mCategoryData;
    }

    private <T> void setLiveDataValue(Class<T> type, List<T> models) {

        if (type.equals(Product.class))
            mProductData.setValue((List<Product>) models);

        else if (type.equals(Category.class))
            mCategoryData.setValue((List<Category>) models);

    }
}
