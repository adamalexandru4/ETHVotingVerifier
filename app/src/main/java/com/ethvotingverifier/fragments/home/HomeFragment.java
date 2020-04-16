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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View inflatedView = null;
    private HomeFragmentListener mainActivityListener;

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_home, container, false);

        CardView checkVoteCard = inflatedView.findViewById(R.id.check_vote_card);
        checkVoteCard.setOnClickListener(this);

        TextView ethAddressTextView = inflatedView.findViewById(R.id.eth_address);
        ethAddressTextView.setText(Wallet.instance.getAddress());

        TextView ethBalanceTextView = inflatedView.findViewById(R.id.balance_eth);
        ethBalanceTextView.setText(Wallet.instance.getBalance() + " ETH");

        return inflatedView;
    }


    @Override
    public void onClick(View v) {
//        switch(v.getId()) { }
        mainActivityListener.clickOnCheckVote();
    }
}
