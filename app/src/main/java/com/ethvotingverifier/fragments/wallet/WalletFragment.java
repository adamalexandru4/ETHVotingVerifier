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
import com.ethvotingverifier.adapters.AdapterListTransactions;
import com.ethvotingverifier.models.Wallet;

public class WalletFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View inflatedView = null;
    private WalletFragmentListener mainActivityListener;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WalletFragmentListener) {
            mainActivityListener = (WalletFragmentListener) context;
        } else
            throw new RuntimeException(context.toString()
                + "Must implement MainActivityWalletInterface");
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
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_wallet, container, false);

        TextView ethAddressTextView = inflatedView.findViewById(R.id.eth_address);
        ethAddressTextView.setText(Wallet.instance.getAddress());

        TextView ethBalanceTextView = inflatedView.findViewById(R.id.balance_eth);
        ethBalanceTextView.setText(Wallet.instance.getBalance() + " ETH");

        AdapterListTransactions adapterListTransactions = new AdapterListTransactions(getActivity(), MainActivity.walletTransactions.getTransactions());
        ListView listViewTransactions = inflatedView.findViewById(R.id.list_transactions);
        listViewTransactions.setAdapter(adapterListTransactions);

        listViewTransactions.setOnItemClickListener((parent, view, position, id) -> {
              mainActivityListener.onWalletTransactionClick(position);
        });

        return inflatedView;
    }
}
