package ir.maktabsharif.onlinshop.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.adapters.ProductAdapter;
import ir.maktabsharif.onlinshop.utils.FragmentQualifier;
import ir.maktabsharif.onlinshop.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity implements ProductAdapter.SetProductOnClickListener {

    public static Intent newIntent(Context target){
        Intent intent = new Intent(target, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onProductClicked(@NonNull long id) {
        Intent intent = HostFragmentActivity.newIntent(this,
                FragmentQualifier.SINGLE_PRODUCT_FRAGMENT,
                false,
                id);

        startActivity(intent);
    }
}
