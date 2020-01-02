package ir.maktabsharif.onlinshop.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.adapters.CategoryAdapter;
import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.utils.RequestQualifier;
import ir.maktabsharif.onlinshop.viewmodels.CategoryViewModel;

public class CategoriesFragment extends Fragment {

    private static final String TAG = "CategoriesFragment";

    private CategoryViewModel mCategoryViewModel;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private List<String> mCategoriesPaths = new ArrayList<String>() {{
        add("products");
        add("categories");
    }};

    private Map<String, String> mCategoriesQueryParam = new HashMap<String, String>() {{
        put("per_page", String.valueOf(18));
    }};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        mCategoryViewModel.wooCommerceRequest(RequestQualifier.ALL_CATEGORIES,
                TAG,
                Category.class);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        setupRecycler(root);

        mCategoryViewModel.getMainCategoryLiveData().observe(this, categories -> setupAdapter(categories));
        return root;
    }

    private void setupRecycler(View root) {
        mRecyclerView = root.findViewById(R.id.category_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void setupAdapter(List<Category> categories) {
        mAdapter = new CategoryAdapter(getContext(), categories, true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCategoryViewModel.cancelBatchRequest(TAG);
    }
}