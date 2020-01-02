package ir.maktabsharif.onlinshop.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.network.WooCommerceRepository;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;

public class CategoryViewModel extends AndroidViewModel {

    private WooCommerceRepository mRepository;
    private WooCommerceRequestQueue mRequestQueue;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        mRepository = WooCommerceRepository.getInstance();
        mRequestQueue = WooCommerceRequestQueue.getInstance(application);

    }

    public <T> void wooCommerceRequest(RequestQualifier qualifier, String TAG, Class<T> tClass) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceAsyncRequest(qualifier, TAG, tClass));
    }

    public MutableLiveData<List<Category>> getMainCategoryLiveData() {
        return mRepository.getMainCategoryData();
    }

    public void cancelBatchRequest(String TAG) {
        mRequestQueue.getRequestQueue().cancelAll(TAG);
    }
}