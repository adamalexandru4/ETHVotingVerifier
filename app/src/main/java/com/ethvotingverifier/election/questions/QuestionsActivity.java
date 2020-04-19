package com.ethvotingverifier.election.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.election.questions.adapters.AdapterListQuestions;
import com.ethvotingverifier.fragments.settings.adapters.AdapterListSettingsItems;
import com.ethvotingverifier.models.Election;

import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements Election.GetQuestionsListener {
    ConstraintLayout loadingContainer;
    RecyclerView recyclerView;
    AdapterListQuestions adapterListQuestions;
    LinearLayoutManager linearLayoutManager;

    private final Election heliosElection = Election.instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        loadingContainer = findViewById(R.id.loading_questions);
        loadingContainer.setVisibility(View.VISIBLE);

        heliosElection.getQuestions(this);

    }

    @Override
    public void receiveQuestionsFromBlockchain(ArrayList<Tuple5<String, String, String, String, ArrayList<String>>> questions) {
        loadingContainer.setVisibility(View.GONE);

        if(questions.size() > 0) {
            recyclerView = findViewById(R.id.questions_recycler_view);
            recyclerView.setVisibility(View.VISIBLE);

            int questionsLength = questions.size();
            String[] titles = new String[questionsLength];
            String[] minValues = new String[questionsLength];
            String[] maxValues = new String[questionsLength];
            String[] resultTypes = new String[questionsLength];
            ArrayList<String>[] answers = new ArrayList[questionsLength];

            for (int i = 0; i < questionsLength; i++) {
                titles[i] = questions.get(i).component1();
                minValues[i] = questions.get(i).component2();
                maxValues[i] = questions.get(i).component3();
                resultTypes[i] = questions.get(i).component4();
                answers[i] = new ArrayList<String>();

                for (int j = 0; j < questions.get(i).component5().size(); j++) {
                    answers[i].add(questions.get(i).component5().get(j));
                }
            }

            adapterListQuestions = new AdapterListQuestions(this, titles, minValues, maxValues, resultTypes, answers);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterListQuestions);
        } else {
            Toast.makeText(this, "There was an error. Try again!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
