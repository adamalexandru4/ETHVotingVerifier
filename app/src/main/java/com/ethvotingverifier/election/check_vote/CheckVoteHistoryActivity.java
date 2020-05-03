package com.ethvotingverifier.election.check_vote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.ethvotingverifier.R;
import com.ethvotingverifier.database.AppDatabase;
import com.ethvotingverifier.database.User;
import com.ethvotingverifier.database.Vote;
import com.ethvotingverifier.election.check_vote.adapters.AdapterListVotes;
import com.ethvotingverifier.election.questions.adapters.AdapterListQuestions;
import com.ethvotingverifier.election.voters.adapters.AdapterListVoters;
import com.ethvotingverifier.models.Election;
import com.ethvotingverifier.models.Wallet;

import java.util.ArrayList;

public class CheckVoteHistoryActivity extends AppCompatActivity implements Election.CheckVoteHistoryListener {

    LinearLayout noVotesLayout;
    RecyclerView recyclerViewVotes;
    AdapterListVotes adapterListVotes;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_vote_history);

        noVotesLayout = findViewById(R.id.no_votes_in_history_layout);
        recyclerViewVotes = findViewById(R.id.votes_recycler_view);

        new GetVotesFromDB(this).execute();
    }

    public void getVotesFromDB(ArrayList<Vote> votes) {
        if(votes.size() == 0) {
            noVotesLayout.setVisibility(View.VISIBLE);
            recyclerViewVotes.setVisibility(View.GONE);
        } else {
            noVotesLayout.setVisibility(View.GONE);
            adapterListVotes = new AdapterListVotes(this, votes);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerViewVotes.setLayoutManager(linearLayoutManager);
            recyclerViewVotes.setAdapter(adapterListVotes);
        }
    }

    class GetVotesFromDB extends AsyncTask<Void, Void, ArrayList<Vote>> {

        Election.CheckVoteHistoryListener checkVoteHistoryListener;

        public GetVotesFromDB(Election.CheckVoteHistoryListener checkVoteHistoryListener) {
            this.checkVoteHistoryListener = checkVoteHistoryListener;
        }

        @Override
        protected ArrayList<Vote> doInBackground(Void... voids) {
            ArrayList<Vote> votes = null;
            try {
                AppDatabase appDatabase = AppDatabase.getInstance((Context) checkVoteHistoryListener);
                votes = appDatabase.votesDao().getUserVotes(Wallet.instance.getAddress());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return votes;
        }

        @Override
        protected void onPostExecute(ArrayList<Vote> votes) {
            super.onPostExecute(votes);
            checkVoteHistoryListener.getVotesFromDB(votes);
        }
    }
}