package com.ethvotingverifier.models;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.ethvotingverifier.contract.HeliosElection;
import com.ethvotingverifier.election.CheckVoteActivity;

import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Election {
    public static final Election instance = new Election();

    public boolean isElectionDeployed = false;

    private HeliosElection heliosElectionWrapper;
    public String contractAddress;
    private Web3 web3Instance;
    private Wallet walletInstance;

    private GetVoteListener getVoteListener;
    public interface GetVoteListener {
        public void receiveVoteFromBlockchain(Tuple4<String, String, String, String> vote);
    }

    private Election() {
        web3Instance = Web3.instance;
        walletInstance = Wallet.instance;
    }

    public void deployContractAtAddress(String contractAddress) {
        this.contractAddress = contractAddress;
        isElectionDeployed = true;

        heliosElectionWrapper = HeliosElection.load(
                contractAddress,
                Web3.instance.getWeb3(),
                Wallet.instance.getCredentials(), new DefaultGasProvider());
    }

    public void getVote(String UUID, GetVoteListener getVoteListener) {
        this.getVoteListener = getVoteListener;

        new GetVoteTask().execute(UUID);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static Date toDate(Instant instant) {
        BigInteger milis = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                    BigInteger.valueOf(1000));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                    BigInteger.valueOf(1_000_000)));
        }
        return new Date(milis.longValue());
    }

    private class GetVoteTask extends AsyncTask<String, Integer, Tuple4<String, String, String, String>> {

        @Override
        protected Tuple4<String, String, String, String> doInBackground(String... params) {
            String UUID = params[0];

            byte[] uuid_bytes = Election.hexStringToByteArray(UUID.substring(2));
            Tuple4<String, String, String, String> vote_format = null;
            try {
                Tuple4<byte[], BigInteger, BigInteger, Boolean> vote = heliosElectionWrapper.votes(uuid_bytes).send();

                long milis = vote.component2().longValue();
                Date castAt = new Date(milis);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                String castAtString = dateFormat.format(castAt);

                vote_format = new Tuple4<>(new String(vote.component1()), castAtString, vote.component3().toString(), vote.component4().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return vote_format;
        }

        @Override
        protected void onPostExecute(Tuple4<String, String, String, String> vote) {
            super.onPostExecute(vote);

            getVoteListener.receiveVoteFromBlockchain(vote);
        }
    }
}
