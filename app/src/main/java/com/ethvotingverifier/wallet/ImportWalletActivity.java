package com.ethvotingverifier.wallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.SplashScreenActivity;
import com.ethvotingverifier.StartScreenActivity;
import com.ethvotingverifier.models.Wallet;
import com.google.android.material.snackbar.Snackbar;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import jnr.ffi.Struct;

public class ImportWalletActivity extends AppCompatActivity {

    private Button uploadWalletFileBtn, importWalletBtn;
    private EditText walletFileEditText;

    private String walletPath;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load_wallet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        walletFileEditText = findViewById(R.id.wallet_file_content);

        uploadWalletFileBtn = findViewById(R.id.upload_wallet_button);
        uploadWalletFileBtn.setOnClickListener(v -> {
            new MaterialFilePicker()
                    .withActivity(ImportWalletActivity.this)
                    .withRequestCode(1000)
                    .withHiddenFiles(true)
                    .start();
        });

        importWalletBtn = findViewById(R.id.import_wallet_button);
        importWalletBtn.setOnClickListener(v -> {

                EditText passphraseEditText = findViewById(R.id.passphrase);
                String passphrase = passphraseEditText.getText().toString();

                ProgressBar loading = findViewById(R.id.progressBar);
                loading.setVisibility(View.VISIBLE);

                closeKeyboard(v);
                /*
                * Another thread, not the UI one
                */
                ThreadLoadWallet threadLoadWallet = new ThreadLoadWallet(passphrase, walletPath, v);
                new Thread(threadLoadWallet).start();

        });
    }

    private void changeActivityAfterLoading() {
        Intent intent = new Intent(ImportWalletActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class ThreadLoadWallet implements Runnable {

        private String passphrase;
        private String walletPath;
        private View currentView;

        public ThreadLoadWallet(String passphrase, String walletPath, View currentView) {
            this.passphrase = passphrase;
            this.walletPath = walletPath;
            this.currentView = currentView;
        }

        @Override
        public void run() {
            try {
                Credentials credentials = WalletUtils.loadCredentials(passphrase, walletPath);
                runOnUiThread(() -> {
                    ProgressBar loading = findViewById(R.id.progressBar);
                    loading.setVisibility(View.GONE);

                    changeActivityAfterLoading();
                });

                Wallet.instance.setCredentials(credentials, walletPath);
            }
            catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(ImportWalletActivity.this, "Failed to load credentials. Check passphrase and wallet file!", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private String readJSONFile(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            walletPath = path;
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            walletFileEditText.setText(readJSONFile(filePath));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}