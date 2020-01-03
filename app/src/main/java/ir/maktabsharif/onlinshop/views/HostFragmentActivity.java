package ir.maktabsharif.onlinshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.utils.FragmentQualifier;

public class HostFragmentActivity extends AppCompatActivity implements SingleProductFragment.HierarchicalNavigationButton {

    public static final String EXTRA_PRODUCT_ID = "extra_product_id";

    public static final String EXTRA_HOST_FRAGMENT = "extra_host_fragment";
    public static final String EXTRA_ADD_TO_BACK_STACK = "extra_add_to_back_stack";

    public static Intent newIntent(Context target, @NonNull FragmentQualifier qualifier, boolean addToBackStack, long id) {
        Intent intent = new Intent(target, HostFragmentActivity.class);
        intent.putExtra(EXTRA_HOST_FRAGMENT, qualifier);
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        intent.putExtra(EXTRA_ADD_TO_BACK_STACK, addToBackStack);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_fragment);

        FragmentQualifier qualifier = (FragmentQualifier) getIntent().getSerializableExtra(EXTRA_HOST_FRAGMENT);
        long id = getIntent().getLongExtra(EXTRA_PRODUCT_ID, 0);

        onFragmentChange(CreateFragment(qualifier, id),
                getIntent().getBooleanExtra(EXTRA_ADD_TO_BACK_STACK, false));

    }

    public void onFragmentChange(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment attachedFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (attachedFragment == null) {
            transaction.replace(R.id.fragment_container, fragment);

            if (addToBackStack)
                transaction.addToBackStack(null).commit();
            else
                transaction.commit();

        }

    }

    public Fragment CreateFragment(FragmentQualifier qualifier, long id) {
        switch (qualifier) {
            case SINGLE_PRODUCT_FRAGMENT:
                return SingleProductFragment.newInstance(id);
        }

        return null;
    }

    @Override
    public void setOnClickListener() {
        startActivity(MainActivity.newIntent(this));
    }
}
