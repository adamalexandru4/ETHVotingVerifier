package com.ethvotingverifier.election;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.models.Election;

import org.web3j.tuples.generated.Tuple8;

public class InfoElectionActivity extends AppCompatActivity implements Election.GetInformationListener {

    ConstraintLayout loadingLayout, infoLayout;
    TextView titleText, typeText, createdAtText, openDateText, closeDateText, ownerAddressText, serverNodeAddressText, frozenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info_election);

        loadingLayout = findViewById(R.id.loading_info);
        infoLayout = findViewById(R.id.info_layout);

        loadingLayout.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.GONE);

        Election.instance.getInformation(this);

        titleText = findViewById(R.id.election_name);
        typeText = findViewById(R.id.type_of_election);
        createdAtText = findViewById(R.id.created_at);
        openDateText = findViewById(R.id.open_election_date);
        closeDateText = findViewById(R.id.closed_election_date);
        ownerAddressText = findViewById(R.id.owner_address);
        serverNodeAddressText = findViewById(R.id.server_node_address);
        frozenText = findViewById(R.id.election_freezed_text);
    }


    @Override
    public void receiveInformationFromBlockchain(Tuple8<String, Boolean, String, String, String, String, String, Boolean> information) {
        if(information != null) {
            titleText.setText(information.component1());
            if (information.component2())
                typeText.setText("PUBLIC ELECTION");
            else
                typeText.setText("PRIVATE ELECTION");
            createdAtText.setText(information.component3());
            openDateText.setText(information.component4());
            closeDateText.setText(information.component5());
            ownerAddressText.setText(information.component6());
            serverNodeAddressText.setText(information.component7());
            if (information.component8())
                frozenText.setText("ELECTION IS FROZEN");
            else
                frozenText.setText("ELECTION IS NOT FROZEN");

            loadingLayout.setVisibility(View.GONE);
            infoLayout.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "There was an error. Try again or check the contract address!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
