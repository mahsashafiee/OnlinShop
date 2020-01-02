package ir.maktabsharif.onlinshop.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;

import static ir.maktabsharif.onlinshop.utils.Utils.getStringURL;

public class WooCommerceRepository {

    private static final String TAG = "WooCommerceRepository";

    private static WooCommerceRepository sInstance;

    public static WooCommerceRepository getInstance() {
        if (sInstance == null)
            sInstance = new WooCommerceRepository();
        return sInstance;
    }

    private MutableLiveData<List<Product>> mOnSaleProductData = new MutableLiveData();
    private MutableLiveData<List<Product>> mTopRatedProductData = new MutableLiveData();
    private MutableLiveData<List<Product>> mOnPopularProductData = new MutableLiveData();
    private MutableLiveData<List<Product>> mRecentProductData = new MutableLiveData();
    private MutableLiveData<List<Category>> mHomeCategoryData = new MutableLiveData();
    private MutableLiveData<List<Category>> mMainCategoryData = new MutableLiveData();
    private MutableLiveData<Product> mSliderData = new MutableLiveData();


    private WooCommerceRepository() {
    }

    public <T> JsonArrayRequest wooCommerceAsyncRequest(RequestQualifier qualifier, String TAG, Class<T> tClass) {

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, tClass);
        final JsonAdapter<List<T>> adapter = moshi.adapter(type);

        Response.Listener<JSONArray> listener = response -> {
            try {
                List<T> models = adapter.fromJson(response.toString());

                setLiveDataValue(qualifier, models);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Response.ErrorListener errorListener = error -> Log.e(this.TAG, "onErrorResponse: ", error);

        JsonArrayRequest request = new JsonArrayRequest(
                getStringURL(qualifier.getPath(), qualifier.getQueryParam()),
                listener,
                errorListener);
        request.setTag(TAG);

        return request;

    }

    public JsonObjectRequest wooCommerceAsyncRequest(String TAG, int id) {

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.getRawType(Product.class);
        final JsonAdapter<Product> adapter = moshi.adapter(type);

        Response.Listener<JSONObject> listener = response -> {
            try {
                Product product = adapter.fromJson(response.toString());
                mSliderData.setValue(product);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Response.ErrorListener errorListener = error -> Log.e(this.TAG, "wooCommerceAsyncRequest: ", error);

        JsonObjectRequest request = new JsonObjectRequest(
                getStringURL(String.valueOf(id), new HashMap<>()),
                null,
                listener,
                errorListener);

        request.setTag(TAG);
        return request;
    }

    public MutableLiveData<List<Product>> getOnSaleProductData() {
        return mOnSaleProductData;
    }

    public MutableLiveData<List<Category>> getMainCategoryData() {
        return mMainCategoryData;
    }

    public MutableLiveData<List<Category>> getHomeCategoryData() {
        return mHomeCategoryData;
    }

    public MutableLiveData<Product> getSliderData() {
        return mSliderData;
    }

    public MutableLiveData<List<Product>> getTopRatedProductData() {
        return mTopRatedProductData;
    }

    public MutableLiveData<List<Product>> getOnPopularProductData() {
        return mOnPopularProductData;
    }

    public MutableLiveData<List<Product>> getRecentProductData() {
        return mRecentProductData;
    }

    private <T> void setLiveDataValue(RequestQualifier qualifier, List<T> models) {
        switch (qualifier) {
            case ON_SALE_PRODUCTS:
                mOnSaleProductData.setValue((List<Product>) models);
                break;
            case TOP_RATED_PRODUCT:
                mTopRatedProductData.setValue((List<Product>) models);
                break;
            case RECENT_PRODUCTS:
                mRecentProductData.setValue((List<Product>) models);
                break;
            case POPULAR_PRODUCT:
                mOnPopularProductData.setValue((List<Product>) models);
                break;
            case MAIN_CATEGORIES:
                mHomeCategoryData.setValue((List<Category>) models);
                break;
            case ALL_CATEGORIES:
                mMainCategoryData.setValue((List<Category>) models);
                break;

        }
    }
}
