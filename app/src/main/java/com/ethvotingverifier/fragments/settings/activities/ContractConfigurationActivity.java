package com.ethvotingverifier.fragments.settings.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.election.ScanQrCodeActivity;
import com.ethvotingverifier.models.Election;

import org.web3j.crypto.WalletUtils;

public class ContractConfigurationActivity extends AppCompatActivity {

    EditText contractAddressEditText;

    private String contractAddress;
    public static final int REQUEST_CODE_QR_CODE = 1024;
    private final Election heliosElection = Election.instance;

    private boolean firstDeploy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_contract_configuration);

        try {
            Bundle bundle = getIntent().getExtras();
            firstDeploy = bundle.getBoolean("firstDeploy");
        } catch (NullPointerException exception) {
            firstDeploy = false;
        }

        contractAddressEditText = findViewById(R.id.contract_address_input);
        Button setAddress = findViewById(R.id.set_contract_address);
        Button scanQRCode = findViewById(R.id.scan_qr_code);

        if(Election.instance.isElectionDeployed) {
            contractAddressEditText.setText(Election.instance.contractAddress);
        }
        setAddress.setOnClickListener(v -> {
            contractAddress = contractAddressEditText.getText().toString();
            if (!contractAddress.isEmpty() && WalletUtils.isValidAddress(contractAddress)) {
                heliosElection.deployContractAtAddress(contractAddress);

                if(firstDeploy) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            } else {
                Toast.makeText(this, "You should insert a correct contract address", Toast.LENGTH_LONG).show();
            }
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
                    contractAddress = data.getStringExtra("result");
                    contractAddressEditText.setText(contractAddress);
                }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
