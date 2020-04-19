package com.ethvotingverifier.election.questions.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.R;

import java.util.ArrayList;

public class AdapterListQuestions extends RecyclerView.Adapter<AdapterListQuestions.MyViewHolder> {

    private String titles[];
    private String minValues[];
    private String maxValues[];
    private String resultTypes[];
    private ArrayList<String> answers[];

    Context context;

    public AdapterListQuestions(Context context, String titles[], String minValues[], String maxValues[],
                                String resultTypes[], ArrayList<String> answers[]) {
        this.context = context;

        this.titles = titles;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.resultTypes = resultTypes;
        this.answers = answers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_questions_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText("#" + String.valueOf(position + 1) + ". " + titles[position]);

        String resultTypeString = "Result type is <b>" + resultTypes[position] + "</b>";
        holder.resultType.setText(Html.fromHtml(resultTypeString));

        String minAndMaxValuesString = "You can choose between <b>" + minValues[position] + "</b> and <b>" + maxValues[position] + "</b> answers";
        holder.minAndMaxAnswers.setText(Html.fromHtml(minAndMaxValuesString));

        ArrayList<String> answersForQuestion = answers[position];
        AdapterListQuestionsAnswers adapterListQuestionsAnswers = new AdapterListQuestionsAnswers(answersForQuestion);
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(context);
        holder.answers.setLayoutManager(layoutManagerMember);
        holder.answers.setAdapter(adapterListQuestionsAnswers);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView minAndMaxAnswers;
        TextView resultType;
        RecyclerView answers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.questions_row_title);
            minAndMaxAnswers = itemView.findViewById(R.id.questions_row_min_and_max_answers);
            resultType = itemView.findViewById(R.id.questions_row_result_type);
            answers = itemView.findViewById(R.id.questions_row_answers_recycle_view);
        }
    }
}
