package com.ethvotingverifier.models;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.security.interfaces.ECKey;
import java.util.concurrent.ExecutionException;

public class Wallet {
    public static final Wallet instance = new Wallet();

    private Credentials credentials;
    private String walletPath;
    private ECKeyPair ecKeyPair;
    private String address;

    private BigDecimal balance;

    private Wallet() {}

    public void setCredentials(Credentials credentials, String walletPath) {
        this.credentials = credentials;
        this.walletPath = walletPath;
        this.ecKeyPair = credentials.getEcKeyPair();
        this.address = Numeric.prependHexPrefix(Keys.getAddress(this.ecKeyPair));
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public String getWalletPath() {
        return this.walletPath;
    }

    public ECKeyPair getEcKeyPair() {
        return this.ecKeyPair;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = Web3.instance.getWeb3().ethGetBalance(this.address, DefaultBlockParameterName.LATEST).sendAsync().get();
            this.balance = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        return balance.toString();
    }
}
