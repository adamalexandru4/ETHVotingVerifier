package com.ethvotingverifier.election.questions.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.R;

import java.util.ArrayList;

public class AdapterListQuestionsAnswers extends RecyclerView.Adapter<AdapterListQuestionsAnswers.ViewHolder> {

    ArrayList<String> answers;

    public AdapterListQuestionsAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_questions_row_answers, parent, false);
        return new AdapterListQuestionsAnswers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.answerText.setText(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView answerText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answerText = itemView.findViewById(R.id.answer);
        }
    }
}
