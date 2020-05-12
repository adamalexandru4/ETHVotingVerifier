package com.ethvotingverifier.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ListView listViewTransactions = inflatedView.findViewById(R.id.list_transactions);
        listViewTransactions.setAdapter(adapterListTransactions);

        return inflatedView;
    }
}
