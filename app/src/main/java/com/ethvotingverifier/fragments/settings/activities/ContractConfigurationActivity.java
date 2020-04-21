package com.ethvotingverifier.fragments.settings.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.election.ScanQrCodeActivity;
import com.ethvotingverifier.models.Election;

import org.web3j.crypto.WalletUtils;

public class ContractConfigurationActivity extends AppCompatActivity
    implements Election.DeployContractListener{

    EditText contractAddressEditText, electionNameEditText;

    private String contractAddress;
    private String electionName;

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
        electionNameEditText = findViewById(R.id.election_name);

        Button setAddress = findViewById(R.id.set_contract_address);
        Button scanQRCode = findViewById(R.id.scan_qr_code);

        if(Election.instance.isElectionDeployed) {
            contractAddressEditText.setText(Election.instance.contractAddress);
            electionNameEditText.setText(Election.instance.electionName);
        }
        setAddress.setOnClickListener(v -> {
            contractAddress = contractAddressEditText.getText().toString();
            electionName = electionNameEditText.getText().toString();

            if(!electionName.isEmpty()) {
                if (!contractAddress.isEmpty() && WalletUtils.isValidAddress(contractAddress)) {
                    // Async task
                    heliosElection.deployContractAtAddress(electionName, contractAddress, this);
                } else {
                    Toast.makeText(this, "You should insert a correct contract address", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "You should insert the election name", Toast.LENGTH_LONG).show();
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
                    electionNameEditText.setText("");
                    electionNameEditText.requestFocus();

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

    @Override
    public void deployContractResult(String message) {
        if(message.equalsIgnoreCase("Succesfull")) {

            SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("contractAddress", contractAddress);
            editor.putString("electionName", electionName);
            editor.commit();

            if (firstDeploy) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
