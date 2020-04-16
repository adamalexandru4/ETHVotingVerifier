package com.ethvotingverifier.election;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ethvotingverifier.R;

public class CheckVoteActivity extends AppCompatActivity {

    EditText UUID_editText;
    public static final int REQUEST_CODE_QR_CODE = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_check_vote);

        UUID_editText = findViewById(R.id.uuid_input);
        Button checkVoteButton = findViewById(R.id.check_vote);
        Button scanQRCode = findViewById(R.id.scan_qr_code);

        checkVoteButton.setOnClickListener(v -> {

        });

        scanQRCode.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, ScanQrCodeActivity.class), REQUEST_CODE_QR_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_QR_CODE:
                if (resultCode == RESULT_OK) {
                    String uuidFromCamera = data.getStringExtra("UUID");
                    UUID_editText.setText(uuidFromCamera);
                }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
