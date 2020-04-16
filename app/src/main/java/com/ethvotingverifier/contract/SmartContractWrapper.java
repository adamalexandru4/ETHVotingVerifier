package com.ethvotingverifier.contract;

public class SmartContractWrapper {

    public static final SmartContractWrapper instance = new SmartContractWrapper();

    private String contractAddress;
    private String contractABI;

    public SmartContractWrapper() {}

    public void setContractAddress(String address) {
        this.contractAddress = address;
    }
    public String getContractAddress() {
        return this.contractAddress;
    }


}
