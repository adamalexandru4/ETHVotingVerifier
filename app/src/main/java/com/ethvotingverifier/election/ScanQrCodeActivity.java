package com.ethvotingverifier.election;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ethvotingverifier.R;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent = new Intent();
        intent.putExtra("UUID", rawResult.getText());
        setResult(Activity.RESULT_OK, intent);

        scannerView.stopCamera();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

