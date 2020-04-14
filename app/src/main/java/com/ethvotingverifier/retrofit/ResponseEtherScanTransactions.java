package com.ethvotingverifier.retrofit;

import androidx.annotation.NonNull;

import com.ethvotingverifier.models.Transaction;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseEtherScanTransactions {

    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<Transaction> transactions;

    public ResponseEtherScanTransactions(Integer status, String message, ArrayList<Transaction> transactions) {
        this.status = status;
        this.message = message;
        this.transactions = transactions;
    }

    public List<String> getTxHashes() {
        List<String> txHashes = new ArrayList<String>();
        for(int i = 0; i < transactions.size(); i ++) {
            txHashes.add(transactions.get(i).getHash());
        }

        return txHashes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
