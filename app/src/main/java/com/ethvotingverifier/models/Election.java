package com.ethvotingverifier.models;

import android.content.Context;
import android.os.AsyncTask;

import com.ethvotingverifier.contract.HeliosElection;
import com.ethvotingverifier.database.AppDatabase;
import com.ethvotingverifier.database.User;
import com.ethvotingverifier.database.Vote;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthSyncing;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Async;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Election {
    public static final Election instance = new Election();

    public boolean isElectionDeployed = false;

    public String contractAddress;
    public String electionName;

    private HeliosElection heliosElectionWrapper;
    private Web3 web3Instance;
    private Wallet walletInstance;

    private GetVoteListener getVoteListener;
    public interface GetVoteListener {
        public void receiveVoteFromBlockchain(Tuple4<String, String, String, String> vote);
    }

    private DeployContractListener deployContractListener;
    public interface DeployContractListener {
        public void deployContractResult(String message);
    }

    private GetQuestionsListener getQuestionsListener;
    public interface GetQuestionsListener {
        public void receiveQuestionsFromBlockchain(ArrayList<Tuple5<String, String, String, String, ArrayList<String>>> questions);
    }

    private GetVotersListener getVotersListener;
    public interface GetVotersListener {
        public void receiveVotersList(ArrayList<String> voters);
    }

    private GetInformationListener getInformationListener;
    public interface GetInformationListener {
        public void receiveInformationFromBlockchain(Tuple8<String, Boolean, String, String, String, String, String, Boolean> information);
    }

    public interface CheckVoteHistoryListener {
        void getVotesFromDB(ArrayList<Vote> votes);
    }

    private Election() {
        web3Instance = Web3.instance;
        walletInstance = Wallet.instance;
    }

    public void removeInstance() {
        this.isElectionDeployed = false;
        this.contractAddress = new String();
        this.electionName = new String();
    }

    public void deployContractAtAddress(String electionName, String contractAddress, DeployContractListener deployContractListener) {
        this.deployContractListener = deployContractListener;

        if(deployContractListener == null)
            deployContractSync(electionName, contractAddress);
        else
            new DeployContractTask(deployContractListener).execute(electionName, contractAddress);
    }

    public void getVote(String UUID, GetVoteListener getVoteListener) {
        this.getVoteListener = getVoteListener;

        new GetVoteTask((Context) getVoteListener).execute(UUID);
    }

    public void getQuestions(GetQuestionsListener getQuestionsListener) {
        this.getQuestionsListener = getQuestionsListener;

        new GetQuestionsTask(getQuestionsListener).execute();
    }

    public void getVoters(GetVotersListener getVotersListener) {
        this.getVotersListener = getVotersListener;

        new GetVotersTask(getVotersListener).execute();
    }

    public void getInformation(GetInformationListener getInformationListener) {
        this.getInformationListener = getInformationListener;

        new GetInformationTask(getInformationListener).execute();
    }

    private boolean deployContractSync(String electionName, String contractAddress) {
        try {
            heliosElectionWrapper = HeliosElection.load(
                contractAddress,
                Web3.instance.getWeb3(),
                Wallet.instance.getCredentials(), new DefaultGasProvider());

            if (heliosElectionWrapper.name().send().length() > 0) {

                Election.instance.electionName = electionName;
                Election.instance.contractAddress = contractAddress;
                Election.instance.isElectionDeployed = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
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

    public static String longToDateTime(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return formatter.format(date);
    }

    private class DeployContractTask extends AsyncTask<String, Integer, String> {

        DeployContractListener deployContractListener;

        public DeployContractTask(DeployContractListener deployContractListener) {
            this.deployContractListener = deployContractListener;
        }

        @Override
        protected String doInBackground(String... params) {

            electionName = params[0];
            contractAddress = params[1];

            if(deployContractSync(electionName, contractAddress))
                return "Succesfull";
            else
                return "Failed to connect to the deployed contract. Check the address";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(deployContractListener != null) {
                deployContractListener.deployContractResult(s);
            }
        }

    }

    private class GetVoteTask extends AsyncTask<String, Integer, Tuple4<String, String, String, String>> {

        private Context context;

        public GetVoteTask(Context context) {
            this.context = context;
        }

        @Override
        protected Tuple4<String, String, String, String> doInBackground(String... params) {
            String UUID = params[0];
            Tuple4<String, String, String, String> vote_format = null;

            try {
                byte[] uuid_bytes = Election.hexStringToByteArray(UUID.substring(2));
                Tuple4<byte[], BigInteger, BigInteger, Boolean> vote = heliosElectionWrapper.votes(uuid_bytes).send();

                long milis = vote.component2().longValue();
                String castAtString = Election.longToDateTime(milis * 1000);

                milis = vote.component3().longValue();
                String verifiedAtString = Election.longToDateTime(milis * 1000);

                vote_format = new Tuple4<>("0x" + Hex.toHexString(vote.component1()), castAtString, verifiedAtString, vote.component4().toString());

                AppDatabase appDatabase = AppDatabase.getInstance(context);
                User userDB = null;
                    if((userDB = appDatabase.votesDao().getUser(Wallet.instance.getAddress())) != null) {
                        Date checkedDate = Calendar.getInstance().getTime();
                        Vote voteDB = new Vote(vote_format.component1(), vote_format.component2(), vote_format.component3(), vote.component4(), checkedDate);

                        appDatabase.votesDao().insertVote(voteDB, userDB);
                    }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return vote_format;
        }

        @Override
        protected void onPostExecute(Tuple4<String, String, String, String> vote) {
            super.onPostExecute(vote);

            getVoteListener.receiveVoteFromBlockchain(vote);
        }
    }

    private class GetQuestionsTask extends AsyncTask<Void, Integer, ArrayList<Tuple5<String, String, String, String, ArrayList<String>>>> {

        GetQuestionsListener getQuestionsListener;

        public GetQuestionsTask(GetQuestionsListener getQuestionsListener) {
            this.getQuestionsListener = getQuestionsListener;
        }

        @Override
        protected ArrayList<Tuple5<String, String, String, String, ArrayList<String>>> doInBackground(Void... voids) {
            ArrayList<Tuple5<String, String, String, String, ArrayList<String>>> questions = new ArrayList<>();

            Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>> question = null;

            int noQuestions;
            try {
                noQuestions = heliosElectionWrapper.noQuestions().send().intValue();
                if(noQuestions > 0) {
                    for(int i = 0; i < noQuestions; i ++) {
                        question = heliosElectionWrapper.getQuestion(BigInteger.valueOf(i)).send();

                        String title = question.component1();
                        String minValue = question.component2().toString();
                        String maxValue = question.component3().toString();
                        String resultType = new String(question.component4()).replaceAll("\0", "");
                        ArrayList<String> answers = new ArrayList<>();
                        for(int j = 0; j < question.component5().size(); j ++) {
                            answers.add(new String(question.component5().get(j)).replaceAll("\0", ""));
                        }

                        Tuple5<String, String, String, String, ArrayList<String>> correctQuestionFormat = new Tuple5<>(title, minValue, maxValue, resultType, answers);
                        questions.add(correctQuestionFormat);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return questions;
        }

        @Override
        protected void onPostExecute(ArrayList<Tuple5<String, String, String, String, ArrayList<String>>> questions) {
            getQuestionsListener.receiveQuestionsFromBlockchain(questions);
        }
    }

    private class GetVotersTask extends AsyncTask<String, Integer, ArrayList<String>> {

        GetVotersListener getVotersListener;

        public GetVotersTask(GetVotersListener getVotersListener) {
            this.getVotersListener = getVotersListener;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
             ArrayList<byte[]> votersBytes = new ArrayList<>();
             ArrayList<String> voters = new ArrayList<>();

            try {
                votersBytes = new ArrayList(heliosElectionWrapper.getVotersUUID().send());
                for(int i = 0; i < votersBytes.size(); i ++) {
                    voters.add("0x" + Hex.toHexString(votersBytes.get(i)));
                }
                Collections.sort(voters);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return voters;
        }

        @Override
        protected void onPostExecute(ArrayList<String> voters) {
            getVotersListener.receiveVotersList(voters);
        }
    }

    private class GetInformationTask extends AsyncTask<Void, Integer, Tuple8<String, Boolean, String, String, String, String, String, Boolean>> {

        GetInformationListener getInformationListener;

        public GetInformationTask(GetInformationListener getInformationListener) {
            this.getInformationListener = getInformationListener;
        }

        @Override
        protected Tuple8<String, Boolean, String, String, String, String, String, Boolean> doInBackground(Void... voids) {

            Tuple8<String, Boolean, String, String, String, String, String, Boolean> information = null;

            try {
                String electionName = heliosElectionWrapper.name().send();
                Boolean electionIsPublic = heliosElectionWrapper.isElectionPublic().send();
                String createdAt = Election.longToDateTime(heliosElectionWrapper.createdAt().send().longValue());
                String startAt = Election.longToDateTime(heliosElectionWrapper.startAt().send().longValue());
                String endAt = Election.longToDateTime(heliosElectionWrapper.endAt().send().longValue());
                String ownerAddress = heliosElectionWrapper.owner().send();
                String nodeAddress = heliosElectionWrapper.serverNodeAddr().send();
                Boolean isElectionPublic = heliosElectionWrapper.isElectionPublic().send();

                information = new Tuple8<>(electionName, electionIsPublic, createdAt, startAt, endAt, ownerAddress, nodeAddress, isElectionPublic);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return information;
        }

        @Override
        protected void onPostExecute(Tuple8<String, Boolean, String, String, String, String, String, Boolean> information) {
            super.onPostExecute(information);

            getInformationListener.receiveInformationFromBlockchain(information);
        }


    }
}
