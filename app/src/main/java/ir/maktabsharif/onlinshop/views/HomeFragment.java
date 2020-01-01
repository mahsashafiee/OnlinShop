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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.adapters.ColorCategoryAdapter;
import ir.maktabsharif.onlinshop.adapters.DiscountSliderAdapter;
import ir.maktabsharif.onlinshop.adapters.ProductAdapter;
import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;
import ir.maktabsharif.onlinshop.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel mHomeViewModel;
    private ProductAdapter mAdapter;
    private ColorCategoryAdapter mColorCategoryAdapter;
    private DiscountSliderAdapter mSliderAdapter;
    private RecyclerView mProductsRecyclerView;
    private RecyclerView mCategoriesRecyclerView;
    private SliderView mSliderView;
    private List<String> mProductsPaths = new ArrayList<String>() {{ add("products"); }};
    private List<String> mCategoriesPaths = new ArrayList<String>(mProductsPaths) {{ add("categories"); }};
    private Map<String, String> mProductsQueryParam = new HashMap<String, String>() {{
        put("orderby", "date");
    }};
    private Map<String, String> mCategoriesQueryParam = new HashMap<String, String>() {{
        put("parent", String.valueOf(0));
    }};

    private Map<String, String> mSliderQueryParam = new HashMap<String, String>() {{
        put("parent", String.valueOf(119));
    }};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        setupRequest();

    }

    private void setupRequest() {
        mHomeViewModel.wooCommerceRequest(RequestQualifier.PRODUCTS, TAG, Product.class, mProductsPaths, mProductsQueryParam);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.CATEGORIES, TAG, Category.class, mCategoriesPaths, mCategoriesQueryParam);
        mHomeViewModel.wooCommerceRequest(RequestQualifier.SLIDER, TAG, Category.class, mCategoriesPaths, mSliderQueryParam);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setupRecycler(root);

        setupObservers();

        return root;
    }

    private void setupObservers() {

        mHomeViewModel.getProductLiveData().observe(this, products -> setupAdapter(products));
        mHomeViewModel.getCategoryLiveData().observe(this, categories -> setupCategoryAdapter(categories));
        mHomeViewModel.getSliderLiveData().observe(this, categories -> setupSliderAdapter(categories));
    }

    private void setupRecycler(View root) {
        mSliderView = root.findViewById(R.id.imageSlider);
        mProductsRecyclerView = root.findViewById(R.id.on_sale_recycler_view);
        mCategoriesRecyclerView = root.findViewById(R.id.categories_recycler_view);
        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
        mProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    public void setupAdapter(List<Product> products) {
        mAdapter = new ProductAdapter(getContext(), products);
        mProductsRecyclerView.setAdapter(mAdapter);
    }

    public void setupCategoryAdapter(List<Category> categories) {
        mColorCategoryAdapter = new ColorCategoryAdapter(getContext(), categories);
        mCategoriesRecyclerView.setAdapter(mColorCategoryAdapter);
    }

    public void setupSliderAdapter(List<Category> categories) {
        mSliderAdapter = new DiscountSliderAdapter(getContext(), categories);
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