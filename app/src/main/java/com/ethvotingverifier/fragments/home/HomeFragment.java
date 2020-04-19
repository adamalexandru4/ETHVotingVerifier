package com.ethvotingverifier.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ethvotingverifier.R;
import com.ethvotingverifier.models.Wallet;

public class HomeFragment extends Fragment implements View.OnClickListener {
    View inflatedView = null;
    private HomeFragmentListener mainActivityListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof HomeFragmentListener) {
            mainActivityListener = (HomeFragmentListener) context;
        } else
            throw new RuntimeException(context.toString()
                    + "Must implement HomeFragmentListener");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_home, container, false);

        CardView checkVoteCard = inflatedView.findViewById(R.id.check_vote_card);
        CardView questionsCard = inflatedView.findViewById(R.id.questions_card);
        CardView electionInfoCard = inflatedView.findViewById(R.id.election_info_card);
        checkVoteCard.setOnClickListener(this);
        questionsCard.setOnClickListener(this);
        electionInfoCard.setOnClickListener(this);

        TextView ethAddressTextView = inflatedView.findViewById(R.id.eth_address);
        ethAddressTextView.setText(Wallet.instance.getAddress());

        TextView ethBalanceTextView = inflatedView.findViewById(R.id.balance_eth);
        ethBalanceTextView.setText(Wallet.instance.getBalance() + " ETH");

        return inflatedView;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.check_vote_card:
                mainActivityListener.clickOnCheckVote();
                break;
            case R.id.questions_card:
                mainActivityListener.clickOnQuestions();
                break;
            case R.id.election_info_card:
                mainActivityListener.clickOnElectionInfo();
                break;
        }
    }
}
