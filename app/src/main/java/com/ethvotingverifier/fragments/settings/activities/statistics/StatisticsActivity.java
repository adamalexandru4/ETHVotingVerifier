package com.ethvotingverifier.fragments.settings.activities.statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ethvotingverifier.R;
import com.ethvotingverifier.database.AppDatabase;
import com.ethvotingverifier.database.Vote;
import com.ethvotingverifier.election.check_vote.CheckVoteHistoryActivity;
import com.ethvotingverifier.models.Election;
import com.ethvotingverifier.models.Wallet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatisticsActivity extends AppCompatActivity implements Election.CheckVoteHistoryListener {

    ChartView chartViewInit, chartViewFromLayout;
    public static Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);
        chartViewFromLayout = findViewById(R.id.canvas_view);

        new GetVotesFromDB(this).execute();
    }

    @Override
    public void getVotesFromDB(ArrayList<Vote> votes) {
        chartViewFromLayout.addVotes(votes);
        chartViewFromLayout.invalidate();
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
                Date endDate = Calendar.getInstance().getTime();
                Calendar startDateCalendar = Calendar.getInstance();
                startDateCalendar.setTime(endDate);
                startDateCalendar.add(Calendar.DATE, -6);

                votes = appDatabase.votesDao().getUserVotesBetweenDates(Wallet.instance.getAddress(), startDateCalendar.getTime(), endDate);

                StatisticsActivity.startDate = startDateCalendar.getTime();
                StatisticsActivity.endDate = endDate;
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
