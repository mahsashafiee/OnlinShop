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
import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;

import static ir.maktabsharif.onlinshop.utils.Utils.getStringURL;

public class WooCommerceService {

    private static final String TAG = "WooCommerceService";

    private static WooCommerceService sInstance;

    public static WooCommerceService getInstance() {
        if (sInstance == null)
            sInstance = new WooCommerceService();
        return sInstance;
    }

    private MutableLiveData<List<Product>> mProductData = new MutableLiveData();
    private MutableLiveData<List<Category>> mHomeCategoryData = new MutableLiveData();
    private MutableLiveData<List<Category>> mMainCategoryData = new MutableLiveData();
    private MutableLiveData<Product> mSliderData = new MutableLiveData();


    private WooCommerceService() {
    }

    public <T> JsonArrayRequest wooCommerceRequest(RequestQualifier qualifier, String TAG, Class<T> tClass, List<String> paths, Map<String, String> queryParam) {

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

        JsonArrayRequest request = new JsonArrayRequest(getStringURL(paths, queryParam), listener, errorListener);
        request.setTag(TAG);

        return request;

    }

    public JsonObjectRequest wooCommerceRequest(String TAG, List<String> paths, Map<String, String> queryParams) {
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

        Response.ErrorListener errorListener = error -> Log.e(TAG, "wooCommerceRequest: ", error);

        JsonObjectRequest request = new JsonObjectRequest(getStringURL(paths, queryParams), null, listener, errorListener);
        return request;
    }

    public MutableLiveData<List<Product>> getProductData() {
        return mProductData;
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

    private <T> void setLiveDataValue(RequestQualifier qualifier, List<T> models) {

        switch (qualifier) {
            case PRODUCTS:
                mProductData.setValue((List<Product>) models);
                break;
            case HOMECATEGORIES:
                mHomeCategoryData.setValue((List<Category>) models);
                break;
            case MAINCATEGORY:
                mMainCategoryData.setValue((List<Category>) models);
                break;

        }
    }
}
