package com.ethvotingverifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.ethvotingverifier.election.InfoElectionActivity;
import com.ethvotingverifier.election.check_vote.CheckVoteActivity;
import com.ethvotingverifier.election.questions.QuestionsActivity;
import com.ethvotingverifier.election.voters.VotersActivity;
import com.ethvotingverifier.fragments.settings.SettingsFragment;
import com.ethvotingverifier.fragments.home.HomeFragment;
import com.ethvotingverifier.fragments.HistoryFragment;
import com.ethvotingverifier.fragments.home.HomeFragmentListener;
import com.ethvotingverifier.fragments.settings.activities.ContactActivity;
import com.ethvotingverifier.fragments.settings.activities.ContractConfigurationActivity;
import com.ethvotingverifier.fragments.settings.activities.statistics.StatisticsActivity;
import com.ethvotingverifier.fragments.settings.adapters.AdapterListSettingsItems;
import com.ethvotingverifier.fragments.wallet.WalletFragment;
import com.ethvotingverifier.fragments.wallet.WalletFragmentListener;
import com.ethvotingverifier.fragments.dialogs.TransactionInfoDialog;
import com.ethvotingverifier.models.Election;
import com.ethvotingverifier.models.Wallet;
import com.ethvotingverifier.retrofit.GetDataService;
import com.ethvotingverifier.retrofit.ResponseEtherScanTransactions;
import com.ethvotingverifier.retrofit.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        WalletFragmentListener, HomeFragmentListener, AdapterListSettingsItems.ClickOnSettingItemListener

{
    public static ResponseEtherScanTransactions walletTransactions;

    private BottomNavigationView bottomNavigationView;
    private SettingsFragment settingsFragment = new SettingsFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private HistoryFragment historyFragment = new HistoryFragment();
    private WalletFragment walletFragment = new WalletFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!Election.instance.isElectionDeployed) {
            Intent intent = new Intent(MainActivity.this, ContractConfigurationActivity.class);
            intent.putExtra("firstDeploy", true);
            startActivity(intent);
            finish();
            return;
        }

        loadWalletTransactions();

        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void loadWalletTransactions() {

        GetDataService getDataService = RetrofitInstance.getInstance().create(GetDataService.class);

        Call<ResponseEtherScanTransactions> allTransactions = getDataService.getAllTransactions(Wallet.instance.getAddress());
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
                        .commit();
                return true;
            case R.id.navigation_settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.container_fragment, settingsFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
//            case R.id.navigation_transactions:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
//                        .replace(R.id.container_fragment, historyFragment)
//                        .addToBackStack(null)
//                        .commit();
//                return true;
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
//        if(historyFragment.isVisible())
//            bottomNavigationView.setSelectedItemId(R.id.navigation_transactions);
        if(settingsFragment.isVisible())
            bottomNavigationView.setSelectedItemId(R.id.navigation_settings);
    }

    /*********** HOME FRAGMENT *********************/
    @Override
    public void clickOnCheckVote() {
        Intent intent = new Intent(MainActivity.this, CheckVoteActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        onPause();
    }

    @Override
    public void clickOnQuestions() {
        Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        onPause();
    }

    @Override
    public void clickOnElectionInfo() {
        Intent intent = new Intent(MainActivity.this, InfoElectionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        onPause();
    }

    @Override
    public void clickOnVoters() {
        Intent intent = new Intent(MainActivity.this, VotersActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        onPause();
    }


    /*********** TRANSACTIONS FRAGMENT *************/
    @Override
    public void onWalletTransactionClick(int transactionIndex) {

        TransactionInfoDialog transactionInfoDialog = new TransactionInfoDialog();
        transactionInfoDialog.show(getSupportFragmentManager(), "Transaction info dialog");
        //Toast.makeText(this, transactionIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch(position) {
            case 0:
                intent = new Intent(MainActivity.this, StatisticsActivity.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, ContractConfigurationActivity.class);
                break;
            case 2:
                intent = new Intent(MainActivity.this, ContactActivity.class);
                break;
            case 3:
                SharedPreferences sharedPreferences  = getSharedPreferences("sharedPref", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Election.instance.removeInstance();
                intent = new Intent(MainActivity.this, StartScreenActivity.class);
                break;
            default:
                break;
        }

        if(intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            onPause();
        }
    }
}
