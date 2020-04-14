package com.ethvotingverifier.models;

import com.google.gson.annotations.SerializedName;

import org.web3j.abi.datatypes.Bool;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class Transaction {
    @SerializedName("hash")
    public String hash;
    @SerializedName("timestamp")
    public String timestamp;
    @SerializedName("from")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("value")
    public String value;
    @SerializedName("gas")
    public String gas;
    @SerializedName("isError")
    public Integer isError;
    @SerializedName("txreceipt_status")
    public Integer txreceipt_status;
    @SerializedName("contractAddress")
    public String contractAddress;
    @SerializedName("input")
    public String input;        // TAKE CARE, THIS IS HEX VALUE!

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getValueAsETH() {
        return Convert.fromWei(this.value, Convert.Unit.ETHER).toString() + " ETH";
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        if(to.isEmpty() && !contractAddress.isEmpty()) {
            return "CONTRACT CREATION";
        } else if(contractAddress.length() == 0 && gas.equalsIgnoreCase("21000")) {
            return "TRANSACTION";
        } else {
            return "CONTRACT INTERACTION";
        }
    }

    public Boolean isSentTransaction(String ownerAddress) {
        return (from.equalsIgnoreCase(ownerAddress)) ? true : false;
    }

    public Boolean hasError() {
        return (isError == 1 && txreceipt_status == 0) ? true : false;
    }

}
