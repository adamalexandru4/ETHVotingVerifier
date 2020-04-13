package com.ethvotingverifier.models;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ExecutionException;

public class Web3 {
    public static final Web3 instance = new Web3();

    private Web3j web3;
    private Web3jService web3jService;

    private Web3() {
        this.web3jService = new HttpService("https://rinkeby.infura.io/v3/1f68317652084f4b87cb97bd9a4f3d17");
        this.web3 = Web3j.build(this.web3jService);
    }

    public Web3j getWeb3() {
        return this.web3;
    }

    public String getWeb3ClientVersion() {
        Web3ClientVersion web3ClientVersion = null;
        String clientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
            clientVersion = web3ClientVersion.getWeb3ClientVersion();
            return clientVersion;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return clientVersion;
    }
}
