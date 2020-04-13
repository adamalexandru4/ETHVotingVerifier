package com.ethvotingverifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ethvotingverifier.fragments.ContractFragment;
import com.ethvotingverifier.fragments.HomeFragment;
import com.ethvotingverifier.fragments.TransactionsFragment;
import com.ethvotingverifier.fragments.WalletFragment;
import com.ethvotingverifier.models.Wallet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private ContractFragment contractFragment = new ContractFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private TransactionsFragment transactionsFragment = new TransactionsFragment();
    private WalletFragment walletFragment = new WalletFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.container_fragment, homeFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_contract:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.container_fragment, contractFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_transactions:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.container_fragment, transactionsFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_wallet:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.container_fragment, walletFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(homeFragment.isVisible())
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        if(walletFragment.isVisible())
            bottomNavigationView.setSelectedItemId(R.id.navigation_wallet);
        if(transactionsFragment.isVisible())
            bottomNavigationView.setSelectedItemId(R.id.navigation_transactions);
        if(contractFragment.isVisible())
            bottomNavigationView.setSelectedItemId(R.id.navigation_contract);
    }
}
