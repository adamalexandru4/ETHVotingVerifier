package com.ethvotingverifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ethvotingverifier.encryption.EncryptionHelper;
import com.ethvotingverifier.models.Wallet;
import com.ethvotingverifier.models.Web3;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class SplashScreenActivity extends AppCompatActivity {
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
                    new LoadWallet().execute(walletFilePath, passphrase);
                } else {
                    intent = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIME_OUT);
    }

    public class LoadWallet extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... lists) {
            String walletPath = lists[0];
            String passphrase = lists[1];

            Credentials credentials = null;
            try {
                credentials = WalletUtils.loadCredentials(passphrase, walletPath);
            } catch (IOException | CipherException e) {
                return false;
            }

            Wallet.instance.setCredentials(credentials, walletPath);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error while loading credentials", Toast.LENGTH_LONG).show();
            }
        }
    }
}