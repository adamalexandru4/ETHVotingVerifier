package com.ethvotingverifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.ethvotingverifier.fragments.ContractFragment;
import com.ethvotingverifier.fragments.HomeFragment;
import com.ethvotingverifier.fragments.TransactionsFragment;
import com.ethvotingverifier.fragments.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private ContractFragment contractFragment = new ContractFragment();
    private HomeFragment homeFragment = HomeFragment.newInstance("title1", "subtitle");
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
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container_fragment, homeFragment).commit();
                return true;
            case R.id.navigation_contract:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container_fragment, contractFragment).commit();
                return true;
            case R.id.navigation_transactions:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container_fragment, transactionsFragment).commit();
                return true;
            case R.id.navigation_wallet:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container_fragment, walletFragment).commit();
                return true;
        }

        return false;
    }
}
