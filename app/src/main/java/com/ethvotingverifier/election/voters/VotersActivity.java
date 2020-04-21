package com.ethvotingverifier.election.voters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.election.voters.adapters.AdapterListVoters;
import com.ethvotingverifier.fragments.wallet.adapters.AdapterListTransactions;
import com.ethvotingverifier.models.Election;

import java.util.ArrayList;

public class VotersActivity extends AppCompatActivity implements Election.GetVotersListener {

    RecyclerView listViewVoters;
    ConstraintLayout progressLayout, votersLayout, noVotersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_voters);

        progressLayout = findViewById(R.id.loading_voters);
        votersLayout = findViewById(R.id.voters_layout);
        noVotersLayout = findViewById(R.id.no_voters_layout);

        progressLayout.setVisibility(View.VISIBLE);
        votersLayout.setVisibility(View.GONE);
        noVotersLayout.setVisibility(View.GONE);

        listViewVoters = findViewById(R.id.voters_recycler_view);
        Election.instance.getVoters(this);
    }

    @Override
    public void receiveVotersList(ArrayList<String> voters) {
        if(voters.size() > 0) {
            AdapterListVoters adapter = new AdapterListVoters(this, voters);
            listViewVoters.setLayoutManager(new LinearLayoutManager(this));
            listViewVoters.setAdapter(adapter);
            progressLayout.setVisibility(View.GONE);
            votersLayout.setVisibility(View.VISIBLE);
        } else {
            progressLayout.setVisibility(View.GONE);
            noVotersLayout.setVisibility(View.VISIBLE);
        }
    }
}
