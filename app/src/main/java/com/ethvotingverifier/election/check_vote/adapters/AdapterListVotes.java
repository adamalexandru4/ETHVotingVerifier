package com.ethvotingverifier.election.check_vote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.R;
import com.ethvotingverifier.database.Vote;
import com.ethvotingverifier.election.voters.adapters.AdapterListVoters;

import org.web3j.tuples.generated.Tuple4;

import java.util.ArrayList;

public class AdapterListVotes extends RecyclerView.Adapter<AdapterListVotes.ViewHolder> {

    private Context context;
    private ArrayList<Vote> votes;

    public AdapterListVotes(Context context, ArrayList<Vote> votes) {
        this.context = context;
        this.votes = votes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_check_vote_history_row, parent, false);
        return new AdapterListVotes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.voteValue.setText(votes.get(position).hexValue.substring(0, 18) + "..." + votes.get(position).hexValue.substring(votes.get(position).hexValue.length() - 18));
        holder.castAt.setText("CAST AT " + votes.get(position).castAt);

        if(votes.get(position).verified) {
            holder.verifiedAt.setText("VERIFIED AT " + votes.get(position).verifiedAt);
        } else {
            holder.castAt.setVisibility(View.GONE);
            holder.verifiedAt.setVisibility(View.GONE);
            holder.notVerifiedAt.setVisibility(View.VISIBLE);

            holder.verifiedImage.setVisibility(View.GONE);
            holder.notVerifiedImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView voteValue, castAt, verifiedAt, notVerifiedAt;
        ImageView verifiedImage, notVerifiedImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            voteValue = itemView.findViewById(R.id.vote_value_text);
            castAt = itemView.findViewById(R.id.cast_at_vote_text);
            verifiedAt = itemView.findViewById(R.id.verified_at_vote_text);
            notVerifiedAt = itemView.findViewById(R.id.not_verified_at_vote_text);
            verifiedImage = itemView.findViewById(R.id.vote_verified_image);
            notVerifiedImage = itemView.findViewById(R.id.vote_not_verified_image);
        }
    }
}
