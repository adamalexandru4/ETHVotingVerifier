package com.ethvotingverifier.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class CreateWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_wallet);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        Button generateWalletButton = findViewById(R.id.generate_wallet_button);
        generateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passphraseEditText = findViewById(R.id.passphrase);
                String passphrase = passphraseEditText.getText().toString();
                EditText passphraseEditText2 = findViewById(R.id.passphrase2);
                String confirmPassphrase = passphraseEditText2.getText().toString();

                TextInputLayout textLayout = findViewById(R.id.filledTextField);
                textLayout.setError(null);
                TextInputLayout confirmTextLayout = findViewById(R.id.filledTextField2);
                confirmTextLayout.setError(null);

                Map<String, String> errorMessage = validationMessage(passphrase, confirmPassphrase);
                if(errorMessage.isEmpty()) {
                    LinearLayout layoutPassphrase = (LinearLayout)findViewById(R.id.set_passphrase_layout);
                    layoutPassphrase.setVisibility(View.GONE);

                    closeKeyboard(v);

                    ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);

                    ThreadNewWallet runnable = new ThreadNewWallet(v, passphrase);
                    new Thread(runnable).start();
                } else {
                    if(errorMessage.get("passphrase") != null)
                        textLayout.setError(errorMessage.get("passphrase"));
                    if(errorMessage.get("confirmpassphrase") != null)
                        confirmTextLayout.setError(errorMessage.get("confirmpassphrase"));
                }
            }
        });

        Button copyPublicAddressButton = findViewById(R.id.copy_public_address_button);
        copyPublicAddressButton.setOnClickListener(v -> {
            TextView publicAddrETH = findViewById(R.id.public_address);
            String publicAddress = publicAddrETH.getText().toString();

            ClipData clip = ClipData.newPlainText("ETH Public Address", publicAddress);
            clipboard.setPrimaryClip(clip);

            Toast toast = Toast.makeText(CreateWalletActivity.this, "Copied public address", Toast.LENGTH_SHORT);
            toast.setGravity( Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        });

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateWalletActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            onPause();
        });
    }

    private Map<String, String> validationMessage(String passphrase, String confirmPassphrase) {
        Map<String, String> errors = new HashMap<String, String>();

        if(passphrase.isEmpty())
           errors.put("passphrase", "You should complete the passphrase field");
        else if(passphrase.length() < 5)
            errors.put("passphrase", "Minimum length is 5 characters");
        if(confirmPassphrase.isEmpty())
            errors.put("confirmpassphrase", "You should complete the confirmation passphrase");
        else if(!passphrase.equals(confirmPassphrase))
            errors.put("confirmpassphrase", "Passhphrases are not maching!");

        return errors;
    }

    class ThreadNewWallet implements Runnable {

        View currentView;
        String passphrase;

        public Bitmap qrCode;
        public String walletAddress;
        public File walletFile;

        public ThreadNewWallet(View v, String passphrase) {
            this.passphrase = passphrase;
            this.currentView = v;
        }

        @Override
        public void run() {
            setupBouncyCastle();
            walletFile = createNewWallet(currentView, passphrase);
            if(walletFile.exists()) {
                try {
                    Credentials walletCredentials = WalletUtils.loadCredentials(passphrase, walletFile);
                    walletAddress = walletCredentials.getAddress();
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(walletAddress, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        qrCode = barcodeEncoder.createBitmap(bitMatrix);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageView qrCodeImageView = findViewById(R.id.qr_code);
                                TextView pubAddressTextView = findViewById(R.id.public_address);

                                pubAddressTextView.setText(walletAddress);
                                qrCodeImageView.setImageBitmap(qrCode);

                                ProgressBar progressBar = findViewById(R.id.progressBar);
                                progressBar.setVisibility(View.GONE);
                                findViewById(R.id.wallet_generated_layout).setVisibility(View.VISIBLE);
                            }
                        });

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } catch (IOException | CipherException e) {
                    showSnackbarWithMargins(Snackbar.make(currentView, e.getMessage(), Snackbar.LENGTH_SHORT), 32, 32);
                }
            }
        }
    }

    private File createNewWallet(View v, String passphrase) {

        File walletFile = null;
        String statusMessage = "";
        try {

            String fileName = null;
            File directory = new File(Environment.getExternalStorageDirectory() + "/Wallets");
            if(!directory.exists())
                directory.mkdir();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                fileName = WalletUtils.generateLightNewWalletFile(
                        passphrase,
                        directory);
            }

            String path = Environment.getExternalStorageDirectory() + "/Wallets" + "/" + fileName;
            walletFile = new File(path);
            if(walletFile.exists())
                statusMessage = "Your wallet file was created successfully";
            else
                statusMessage = "Wallet file wasn't created";
        } catch (NoSuchAlgorithmException | IOException | InvalidAlgorithmParameterException | CipherException | NoSuchProviderException err) {
            statusMessage = err.getMessage();
        }

        showSnackbarWithMargins(Snackbar.make(v, statusMessage, Snackbar.LENGTH_LONG), 32, 32);

        return walletFile;
    }

    private void showSnackbarWithMargins(Snackbar snackbar, int side, int bottom) {

        final View snackbarView = snackbar.getView();

        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.setMargins(params.leftMargin + side, params.topMargin, params.rightMargin + side, params.bottomMargin + bottom);

        snackbarView.setLayoutParams(params);
        snackbar.show();
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
