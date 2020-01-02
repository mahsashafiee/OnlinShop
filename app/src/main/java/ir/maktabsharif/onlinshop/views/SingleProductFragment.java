package ir.maktabsharif.onlinshop.views;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.viewmodels.SingleProductViewModel;

public class SingleProductFragment extends Fragment {

    public static final String ARGUMENT_PRODUCT_ID = "argument_product_id";
    private SingleProductViewModel mViewModel;

    public static SingleProductFragment newInstance(long productId) {

        Bundle args = new Bundle();
        args.putLong(ARGUMENT_PRODUCT_ID, productId);

        SingleProductFragment fragment = new SingleProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_product_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SingleProductViewModel.class);
        // TODO: Use the ViewModel
    }

}
