package com.ethvotingverifier.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {


//    public String timestamp;
    @SerializedName("hash")
    public String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
//    public String type;
//    public String fromAddr, toAddr;
//    public String contractAddress;
//    public Integer gas, gasUsed;
//    public Integer isError, txreceipt_status;

}
