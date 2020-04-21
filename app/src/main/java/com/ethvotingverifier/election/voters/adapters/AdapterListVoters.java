package com.ethvotingverifier.election.voters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.R;

import java.util.ArrayList;

public class AdapterListVoters extends RecyclerView.Adapter<AdapterListVoters.ViewHolder> {
    private Context context;

    private ArrayList<String> uuids = new ArrayList<>();

    public AdapterListVoters(Context context, ArrayList<String> uuids) {
        this.context = context;
        this.uuids = uuids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_voters_row, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.voterUUID.setText(uuids.get(position));
    }

    @Override
    public int getItemCount() {
        return uuids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView voterUUID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            voterUUID = itemView.findViewById(R.id.voter_uuid);
        }
    }

}
