package ir.maktabsharif.onlinshop.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.network.WooCommerceRepository;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.utils.SliderQualifier;

public class SingleProductViewModel extends AndroidViewModel {

    private WooCommerceRequestQueue mRequestQueue;
    private WooCommerceRepository mRepository;

    public SingleProductViewModel(@NonNull Application application) {
        super(application);

        mRequestQueue = WooCommerceRequestQueue.getInstance(application);
        mRepository = WooCommerceRepository.getInstance();
    }
    // TODO: Implement the ViewModel

    public MutableLiveData<Product> getSliderLiveData() {
        return mRepository.getProductSliderData();
    }

    public void cancelBatchRequest(String TAG) {
        mRequestQueue.getRequestQueue().cancelAll(TAG);
    }

    public void wooCommerceRequest(String TAG, @NonNull long id, SliderQualifier qualifier) {
        mRequestQueue.addToRequestQueue(mRepository.wooCommerceAsyncRequest(TAG, id, qualifier));
    }
}
