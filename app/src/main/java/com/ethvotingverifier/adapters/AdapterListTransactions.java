package com.ethvotingverifier.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ethvotingverifier.R;
import com.ethvotingverifier.models.Transaction;
import com.ethvotingverifier.models.Wallet;

import org.w3c.dom.Text;
import org.web3j.utils.Convert;

import java.util.ArrayList;

public class AdapterListTransactions extends ArrayAdapter<Transaction> {

    Context context;
    ArrayList<Transaction> transactions;
    Integer[] icons = {R.drawable.upload, R.drawable.down_arrow};

    public AdapterListTransactions (Context c, ArrayList<Transaction> transactions) {
        super(c, R.layout.transaction_item_row, R.id.tx_value, transactions);
        this.context = c;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row == null)
            row = LayoutInflater.from(context).inflate(R.layout.transaction_item_row, parent, false);

        TextView myTitle = row.findViewById(R.id.tx_hash);
        String txHash = transactions.get(position).getHash();
        String croppedTxHash = txHash.substring(0, 15) + "..." + txHash.substring(txHash.length() - 15);
        myTitle.setText(croppedTxHash);

        TextView txValue = row.findViewById(R.id.tx_value);
        txValue.setText(transactions.get(position).getValueAsETH());

        ImageView images = row.findViewById(R.id.tx_icon);
        if(transactions.get(position).isSentTransaction("0x92dd3a3F22e8713604fFF872248808C0a574E56D")) {
            images.setImageResource(icons[0]);
            txValue.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
        } else {
            images.setImageResource(icons[1]);
            txValue.setTextColor(ContextCompat.getColor(context, R.color.correctColor));
        }
        TextView hasErrorLabel = row.findViewById(R.id.tx_has_error);
        if(transactions.get(position).hasError())
            hasErrorLabel.setVisibility(View.VISIBLE);
        else
            hasErrorLabel.setVisibility(View.GONE);

        TextView typeTx = row.findViewById(R.id.tx_type);
        typeTx.setText(transactions.get(position).getType());

        return row;
    }
}