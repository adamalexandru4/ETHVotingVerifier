package com.ethvotingverifier.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ethvotingverifier.R;
import com.google.android.material.snackbar.Snackbar;
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

public class CreateWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        Button generateWalletButton = findViewById(R.id.generate_wallet_button);
        generateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadNewWallet runnable = new ThreadNewWallet(v);
                new Thread(runnable).start();
            }
        });
    }

    class ThreadNewWallet implements Runnable {

        View currentView;
        public ThreadNewWallet(View v) {
            this.currentView = v;
        }

        @Override
        public void run() {
            LinearLayout layoutPassphrase = findViewById(R.id.set_passphrase_layout);
            layoutPassphrase.setVisibility(View.GONE);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            EditText passphraseEditText = findViewById(R.id.passphrase);
            passphraseEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);

            String passphrase = passphraseEditText.getText().toString();

            setupBouncyCastle();
            File walletFile = createNewWallet(currentView, passphrase);
            if(walletFile.exists()) {
                try {
                    Credentials walletCredentials = WalletUtils.loadCredentials(passphrase, walletFile);
                    String walletAddress = walletCredentials.getAddress();
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(walletAddress, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        ImageView qrCodeImageView = findViewById(R.id.qr_code);
                        TextView pubAddressTextView = findViewById(R.id.public_address);

                        pubAddressTextView.setText(walletAddress);
                        qrCodeImageView.setImageBitmap(bitmap);

                        progressBar.setVisibility(View.GONE);
                        findViewById(R.id.wallet_generated_layout).setVisibility(View.VISIBLE);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } catch (IOException | CipherException e) {
                    showSnackbarWithMargins(Snackbar.make(currentView, e.getMessage(), Snackbar.LENGTH_LONG), 32, 32);
                }
            }
        }
    }

    private File createNewWallet(View v, String passphrase) {

        File walletFile = null;
        String statusMessage = "";
        try {

            String fileName = null;
            File directory = new File(v.getContext().getFilesDir().getAbsolutePath());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                fileName = WalletUtils.generateLightNewWalletFile(
                        passphrase,
                        directory);
            }

            String path = v.getContext().getFilesDir().getAbsolutePath() + "/" + fileName;
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
}
