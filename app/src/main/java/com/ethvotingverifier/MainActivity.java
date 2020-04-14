package com.ethvotingverifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.ethvotingverifier.fragments.ContractFragment;
import com.ethvotingverifier.fragments.HomeFragment;
import com.ethvotingverifier.fragments.TransactionsFragment;
import com.ethvotingverifier.fragments.WalletFragment;
import com.ethvotingverifier.models.Wallet;
import com.ethvotingverifier.retrofit.GetDataService;
import com.ethvotingverifier.retrofit.ResponseEtherScanTransactions;
import com.ethvotingverifier.retrofit.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static ResponseEtherScanTransactions walletTransactions;

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

        loadWalletTransactions();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }

    private void loadWalletTransactions() {

        GetDataService getDataService = RetrofitInstance.getInstance().create(GetDataService.class);

        Call<ResponseEtherScanTransactions> allTransactions = getDataService.getAllTransactions("0x92dd3a3F22e8713604fFF872248808C0a574E56D");
        allTransactions.enqueue(new Callback<ResponseEtherScanTransactions>() {
            @Override
            public void onResponse(Call<ResponseEtherScanTransactions> call, Response<ResponseEtherScanTransactions> response) {
                walletTransactions = response.body();
            }

            @Override
            public void onFailure(Call<ResponseEtherScanTransactions> call, Throwable t) {

            }
        });
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
