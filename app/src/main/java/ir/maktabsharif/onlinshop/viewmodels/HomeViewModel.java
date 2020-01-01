package ir.maktabsharif.onlinshop.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.network.WooCommerceService;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;

public class HomeViewModel extends AndroidViewModel {

    private WooCommerceService mRepository;
    private WooCommerceRequestQueue mRequestQueue;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRequestQueue = WooCommerceRequestQueue.getInstance(application);
        mRepository = WooCommerceService.getInstance();
    }

    public <T> void wooCommerceRequest(RequestQualifier qualifier, String TAG, Class<T> tClass, @NonNull List<String> paths, @NonNull Map<String, String> queryParam) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceRequest(qualifier, TAG, tClass, paths, queryParam));
    }

    public void wooCommerceRequest(String TAG, @NonNull List<String> paths, @NonNull Map<String, String> queryParam) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceRequest(TAG, paths, queryParam));
    }

    public MutableLiveData<List<Product>> getProductLiveData() {
        return mRepository.getProductData();
    }

    public MutableLiveData<List<Category>> getHomeCategoryLiveData() {
        return mRepository.getHomeCategoryData();
    }

    public MutableLiveData<Product> getSliderLiveData(){
        return mRepository.getSliderData();
    }

    public void cancelBatchRequest(String TAG) {
        mRequestQueue.getRequestQueue().cancelAll(TAG);
    }
}