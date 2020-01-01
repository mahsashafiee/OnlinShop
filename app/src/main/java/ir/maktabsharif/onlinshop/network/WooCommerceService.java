package ir.maktabsharif.onlinshop.network;

import android.util.Log;

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
    private MutableLiveData<List<Category>> mCategoryData = new MutableLiveData();
    private MutableLiveData<List<Category>> mSliderData = new MutableLiveData();


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

    public MutableLiveData<List<Product>> getProductData() {
        return mProductData;
    }

    public MutableLiveData<List<Category>> getCategoryData() {
        return mCategoryData;
    }

    public MutableLiveData<List<Category>> getSliderData() {
        return mSliderData;
    }

    private <T> void setLiveDataValue(RequestQualifier qualifier, List<T> models) {

        switch (qualifier) {
            case PRODUCTS:
                mProductData.setValue((List<Product>) models);
                break;
            case CATEGORIES:
                mCategoryData.setValue((List<Category>) models);
                break;
            case SLIDER:
                mSliderData.setValue((List<Category>) models);
                break;

        }
    }
}
