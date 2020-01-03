package ir.maktabsharif.onlinshop.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.adapters.CategoryAdapter;
import ir.maktabsharif.onlinshop.adapters.SliderAdapter;
import ir.maktabsharif.onlinshop.adapters.ProductAdapter;
import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;
import ir.maktabsharif.onlinshop.utils.SliderQualifier;
import ir.maktabsharif.onlinshop.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel mHomeViewModel;
    private ProductAdapter mAdapter;
    private CategoryAdapter mCategoryAdapter;
    private SliderAdapter mSliderAdapter;
    private RecyclerView mOnSaleRecyclerView;
    private RecyclerView mTopRatedRecyclerView;
    private RecyclerView mRecentRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private RecyclerView mCategoriesRecyclerView;
    private SliderView mSliderView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        setupRequest();

    }

    private void setupRequest() {
        mHomeViewModel.wooCommerceRequest(RequestQualifier.ON_SALE_PRODUCTS, TAG, Product.class);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.RECENT_PRODUCTS, TAG, Product.class);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.TOP_RATED_PRODUCT, TAG, Product.class);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.POPULAR_PRODUCT, TAG, Product.class);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.MAIN_CATEGORIES, TAG, Category.class);
        mHomeViewModel.wooCommerceRequest(TAG, 608, SliderQualifier.HOME_SLIDER);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setupRecycler(root);

        setupObservers();

        return root;
    }

    private void setupObservers() {

        mHomeViewModel.getRecentProductLiveData().observe(this, products -> setupAdapter(products, mRecentRecyclerView));
        mHomeViewModel.getOnSaleProductLiveData().observe(this, products -> setupAdapter(products, mOnSaleRecyclerView));
        mHomeViewModel.getTopRatedProductLiveData().observe(this, products -> setupAdapter(products, mTopRatedRecyclerView));
        mHomeViewModel.getPopularProductLiveData().observe(this, products -> setupAdapter(products, mPopularRecyclerView));
        mHomeViewModel.getHomeCategoryLiveData().observe(this, this::setupCategoryAdapter);
        mHomeViewModel.getSliderLiveData().observe(this, this::setupSliderAdapter);
    }

    private void setupRecycler(View root) {

        mSliderView = root.findViewById(R.id.imageSlider);
        mOnSaleRecyclerView = root.findViewById(R.id.on_sale_recycler_view);
        mTopRatedRecyclerView = root.findViewById(R.id.top_rated_recycler_view);
        mRecentRecyclerView = root.findViewById(R.id.recent_recycler_view);
        mPopularRecyclerView = root.findViewById(R.id.popular_recycler_view);
        mCategoriesRecyclerView = root.findViewById(R.id.categories_recycler_view);

        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));

        mOnSaleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mRecentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupAdapter(List<Product> products, RecyclerView recyclerView) {
        mAdapter = new ProductAdapter(getContext(), products);
        recyclerView.setAdapter(mAdapter);
    }

    private void setupCategoryAdapter(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        mCategoriesRecyclerView.setAdapter(mCategoryAdapter);
    }

    private void setupSliderAdapter(Product product) {
        mSliderAdapter = new SliderAdapter(getContext(), product, SliderQualifier.HOME_SLIDER);
        mSliderView.setSliderAdapter(mSliderAdapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations.
        //:WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mSliderView.setIndicatorAnimation(IndicatorAnimations.DROP);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeViewModel.cancelBatchRequest(TAG);
    }
}