package com.ethvotingverifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.ethvotingverifier.database.AppDatabase;
import com.ethvotingverifier.database.User;
import com.ethvotingverifier.fragments.settings.activities.ContractConfigurationActivity;
import com.ethvotingverifier.models.Election;
import com.ethvotingverifier.models.Wallet;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;

public class SplashScreenActivity extends AppCompatActivity implements Election.DeployContractListener {
    private static int TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                String walletFilePath = sharedPreferences.getString("walletFilePath", "");
                String passphrase = sharedPreferences.getString("walletFilePassword", "");
                if(!walletFilePath.isEmpty() && !passphrase.isEmpty()) {
                    new LoadSavedSettings().execute(walletFilePath, passphrase);
                } else {
                    intent = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIME_OUT);
    }

    @Override
    public void deployContractResult(String message) {

    }

    public class LoadSavedSettings extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... lists) {
            /********* LOAD WALLET ********/
            String walletPath = lists[0];
            String passphrase = lists[1];

            Credentials credentials = null;
            try {
                credentials = WalletUtils.loadCredentials(passphrase, walletPath);
            } catch (IOException | CipherException e) {
                return "Error wallet";
            }

            Wallet.instance.setCredentials(credentials, walletPath);

            /********* LOAD CONTRACT ******/
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
            String contractAddress = sharedPreferences.getString("contractAddress", "");
            String electionName = sharedPreferences.getString("electionName", "");
            if(!contractAddress.isEmpty() && !electionName.isEmpty()) {
                try {
                    Election.instance.deployContractAtAddress(electionName, contractAddress, null);
                } catch (Exception e) {
                    return "Error contract";
                }
            }

            try {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                if(appDatabase.votesDao().getUser(credentials.getAddress()) == null) {
                    User user = new User(credentials.getAddress());
                    appDatabase.votesDao().insertUser(user);
                }
            }
            catch (Exception e) {
                return "Error database";
            }

            return "No errors";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = null;
            if(result.equals("No errors")) {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            } else if (result.equals("Error wallet") || result.equals("Error database")) {
                Toast.makeText(getApplicationContext(), "Error while loading wallet saved settings", Toast.LENGTH_LONG).show();
                intent = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
            } else if (result.equals("Error contract")) {
                Toast.makeText(getApplicationContext(), "Error while loading contract saved settings", Toast.LENGTH_LONG).show();
                intent = new Intent(SplashScreenActivity.this, ContractConfigurationActivity.class);
            }

            if(intent != null) {
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Unknown error. Reload application.", Toast.LENGTH_LONG).show();
            }
        }
    }
}