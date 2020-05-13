package com.ethvotingverifier.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.fragments.wallet.adapters.AdapterListTransactions;
import com.ethvotingverifier.models.Wallet;

public class WalletFragment extends Fragment {

    private View inflatedView = null;

    private String[] filterOption = {"SENT", "RECEIVED", "ALL"};
    private int filterSelected = 2;

    Button allButton, sentButton, receivedButton;

    private ListView listViewTransactions;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_wallet, container, false);

        TextView ethAddressTextView = inflatedView.findViewById(R.id.eth_address);
        ethAddressTextView.setText(Wallet.instance.getAddress());

        TextView ethBalanceTextView = inflatedView.findViewById(R.id.balance_eth);
        ethBalanceTextView.setText(Wallet.instance.getBalance() + " ETH");

        AdapterListTransactions adapterListTransactions = new AdapterListTransactions(getActivity(), MainActivity.walletTransactions.getTransactions());
        listViewTransactions = inflatedView.findViewById(R.id.list_transactions);
        listViewTransactions.setAdapter(adapterListTransactions);

        allButton = inflatedView.findViewById(R.id.all_button);
        sentButton = inflatedView.findViewById(R.id.sent_button);
        receivedButton = inflatedView.findViewById(R.id.received_button);

        allButton.setOnClickListener(v -> {
            changeButtonsBackgroundInactive();
            allButton.setBackground(getResources().getDrawable(R.drawable.custom_button_outlined));
            AdapterListTransactions allTransactionsAdapter = new AdapterListTransactions(getActivity(), MainActivity.walletTransactions.getTransactions());
            listViewTransactions.setAdapter(allTransactionsAdapter);
        });

        sentButton.setOnClickListener(v -> {
            changeButtonsBackgroundInactive();
            sentButton.setBackground(getResources().getDrawable(R.drawable.custom_button_outlined));
            AdapterListTransactions sentTransactionsAdapter = new AdapterListTransactions(getActivity(), MainActivity.walletTransactions.getSentTransactions());
            listViewTransactions.setAdapter(sentTransactionsAdapter);
        });

        receivedButton.setOnClickListener(v -> {
            changeButtonsBackgroundInactive();
            receivedButton.setBackground(getResources().getDrawable(R.drawable.custom_button_outlined));
            AdapterListTransactions receivedTransactionsAdapter = new AdapterListTransactions(getActivity(), MainActivity.walletTransactions.getReceivedTransactions());
            listViewTransactions.setAdapter(receivedTransactionsAdapter);
        });


        return inflatedView;
    }

    private void changeButtonsBackgroundInactive() {
        allButton.setBackground(getResources().getDrawable(R.drawable.custom_button_yellow));
        sentButton.setBackground(getResources().getDrawable(R.drawable.custom_button_yellow));
        receivedButton.setBackground(getResources().getDrawable(R.drawable.custom_button_yellow));
    }
}
