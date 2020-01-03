package ir.maktabsharif.onlinshop.views;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.adapters.SliderAdapter;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.SliderQualifier;
import ir.maktabsharif.onlinshop.viewmodels.SingleProductViewModel;

import static ir.maktabsharif.onlinshop.utils.Utils.textProcessor;

public class SingleProductFragment extends Fragment {

    private static final String TAG = "SingleProductFragment";

    private SliderView mSliderView;

    private TextView mProductDescription;
    private TextView mProductName;

    private HierarchicalNavigationButton mNavigation;

    interface HierarchicalNavigationButton{
        void setOnClickListener();
    }

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mNavigation = (HierarchicalNavigationButton) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SingleProductViewModel.class);
        mViewModel.wooCommerceRequest(TAG, Objects.requireNonNull(getArguments())
                .getLong(ARGUMENT_PRODUCT_ID, 0), SliderQualifier.PRODUCT_SLIDER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_single_product, container, false);
        mSliderView = root.findViewById(R.id.imageSlider);
        mProductDescription = root.findViewById(R.id.single_product_description);
        AppCompatImageButton hierarchicalNavigation = root.findViewById(R.id.hierarchical_button_navigation);
        mProductName = root.findViewById(R.id.single_product_name);
        hierarchicalNavigation.setOnClickListener(v -> mNavigation.setOnClickListener());
        mViewModel.getSliderLiveData().observe(this, this::setupUI);
        return root;
    }


    private void setupUI(Product product) {
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), product, SliderQualifier.PRODUCT_SLIDER);
        mSliderView.setSliderAdapter(sliderAdapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations.
        //:WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mSliderView.setIndicatorAnimation(IndicatorAnimations.SCALE);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        mProductDescription.setText(textProcessor(product.getDescription()));

        mProductName.setText(product.getName());
    }

}
