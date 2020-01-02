package ir.maktabsharif.onlinshop.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.network.WooCommerceRepository;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;

public class HomeViewModel extends AndroidViewModel {

    private WooCommerceRepository mRepository;
    private WooCommerceRequestQueue mRequestQueue;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRequestQueue = WooCommerceRequestQueue.getInstance(application);
        mRepository = WooCommerceRepository.getInstance();
    }

    public <T> void wooCommerceRequest(RequestQualifier qualifier, String TAG, Class<T> tClass) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceAsyncRequest(qualifier, TAG, tClass));
    }

    public void wooCommerceRequest(String TAG, @NonNull int id) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceAsyncRequest(TAG, id));
    }

    public MutableLiveData<List<Product>> getOnSaleProductLiveData() {
        return mRepository.getOnSaleProductData();
    }

    public MutableLiveData<List<Category>> getHomeCategoryLiveData() {
        return mRepository.getHomeCategoryData();
    }

    public MutableLiveData<List<Product>> getRecentProductLiveData() {
        return mRepository.getRecentProductData();
    }

    public MutableLiveData<List<Product>> getTopRatedProductLiveData() {
        return mRepository.getTopRatedProductData();
    }

    public MutableLiveData<List<Product>> getPopularProductLiveData() {
        return mRepository.getOnPopularProductData();
    }

    public MutableLiveData<Product> getSliderLiveData() {
        return mRepository.getSliderData();
    }

    public void cancelBatchRequest(String TAG) {
        mRequestQueue.getRequestQueue().cancelAll(TAG);
    }
}