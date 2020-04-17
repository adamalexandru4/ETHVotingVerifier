package com.ethvotingverifier.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.MainActivity;
import com.ethvotingverifier.R;
import com.ethvotingverifier.fragments.settings.adapters.AdapterListSettingsItems;
import com.ethvotingverifier.models.Wallet;

public class SettingsFragment extends Fragment {

    View settingsView;

    private RecyclerView recyclerView;
    private String itemTitles[];
    private int icons[] = {
            R.drawable.settings_statistics,
            R.drawable.settings_contract,
            R.drawable.settings_contact,
            R.drawable.settings_logout };

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemTitles = getResources().getStringArray(R.array.settings_items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingsView =  inflater.inflate(R.layout.fragment_settings, container, false);

        TextView ethAddressTextView = settingsView.findViewById(R.id.eth_address);
        ethAddressTextView.setText(Wallet.instance.getAddress());

        TextView ethBalanceTextView = settingsView.findViewById(R.id.balance_eth);
        ethBalanceTextView.setText(Wallet.instance.getBalance() + " ETH");

        recyclerView = settingsView.findViewById(R.id.recycler_view);
        AdapterListSettingsItems myAdapter = new AdapterListSettingsItems(getActivity(), itemTitles, icons, (MainActivity)getActivity());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return settingsView;
    }
}
