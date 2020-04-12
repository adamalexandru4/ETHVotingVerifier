package com.ethvotingverifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.ethvotingverifier.wallet.CreateWalletActivity;
import com.ethvotingverifier.wallet.ImportWalletActivity;

public class StartScreenActivity extends AppCompatActivity {

    Button createWalletButton, importWalletButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_screen);

        createWalletButton = findViewById(R.id.create_wallet_button);
        importWalletButton = findViewById(R.id.import_wallet_button);

        createWalletButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartScreenActivity.this, CreateWalletActivity.class);
            startActivity(intent);
            onPause();
        });

        importWalletButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartScreenActivity.this, ImportWalletActivity.class);
            startActivity(intent);
            onPause();
        });


    }
}
