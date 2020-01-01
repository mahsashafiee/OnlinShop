package ir.maktabsharif.onlinshop.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ShoppingCardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShoppingCardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}