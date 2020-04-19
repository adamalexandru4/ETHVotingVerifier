package com.ethvotingverifier.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class HeliosElection extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001f0238038062001f02833981810160405260c08110156200003757600080fd5b81019080805160405193929190846401000000008211156200005857600080fd5b838201915060208201858111156200006f57600080fd5b82518660018202830111640100000000821117156200008d57600080fd5b8083526020830192505050908051906020019080838360005b83811015620000c3578082015181840152602081019050620000a6565b50505050905090810190601f168015620000f15780820380516001836020036101000a031916815260200191505b50604052602001805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919050505033600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555085600090805190602001906200018692919062000213565b50846001819055506001600b60006101000a81548160ff02191690831515021790555080600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060078190555083600c8190555082600d8190555081600e81905550505050505050620002c2565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200025657805160ff191683800117855562000287565b8280016001018555821562000287579182015b828111156200028657825182559160200191906001019062000269565b5b5090506200029691906200029a565b5090565b620002bf91905b80821115620002bb576000816000905550600101620002a1565b5090565b90565b611c3080620002d26000396000f3fe608060405234801561001057600080fd5b506004361061018e5760003560e01c80637cc3ae8c116100de578063ac2a5dfd11610097578063c744656511610071578063c74465651461095d578063cf09e0d01461097b578063db88176f14610999578063eccc038714610a9d5761018e565b8063ac2a5dfd14610865578063b2255c4414610883578063b4170942146108a55761018e565b80637cc3ae8c146105275780637df17823146105455780638da5cb5b1461058f5780638fb229eb146105d9578063a0d4b56714610746578063a1f1d75d146108195761018e565b80632dd96f2b1161014b57806347ecd5181161012557806347ecd5181461047557806349626cb11461049757806351550869146104b55780637957ca73146105055761018e565b80632dd96f2b1461034a57806331b1b9781461036c578063462d8efb1461042f5761018e565b806305b31a6b1461019357806306fdde031461019d5780630f6398221461022057806310b01b851461027f5780631371cd1d146102c15780632b38cd96146102ef575b600080fd5b61019b610abb565b005b6101a5610c9a565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101e55780820151818401526020810190506101ca565b50505050905090810190601f1680156102125780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610228610d38565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561026b578082015181840152602081019050610250565b505050509050019250505060405180910390f35b6102ab6004803603602081101561029557600080fd5b8101908080359060200190929190505050610d90565b6040518082815260200191505060405180910390f35b6102ed600480360360208110156102d757600080fd5b8101908080359060200190929190505050610db1565b005b61031b6004803603602081101561030557600080fd5b8101908080359060200190929190505050610e98565b604051808581526020018481526020018381526020018215151515815260200194505050505060405180910390f35b610352610ed5565b604051808215151515815260200191505060405180910390f35b6103986004803603602081101561038257600080fd5b8101908080359060200190929190505050610ee8565b6040518086815260200180602001858152602001848152602001838152602001828103825286818151815260200191508051906020019080838360005b838110156103f05780820151818401526020810190506103d5565b50505050905090810190601f16801561041d5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61045b6004803603602081101561044557600080fd5b8101908080359060200190929190505050610fb6565b604051808215151515815260200191505060405180910390f35b61047d610fe0565b604051808215151515815260200191505060405180910390f35b61049f610ff3565b6040518082815260200191505060405180910390f35b6104e1600480360360208110156104cb57600080fd5b8101908080359060200190929190505050610ff9565b60405180848152602001838152602001828152602001935050505060405180910390f35b61050d611050565b604051808215151515815260200191505060405180910390f35b61052f611063565b6040518082815260200191505060405180910390f35b61054d611069565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61059761108f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610744600480360360a08110156105ef57600080fd5b810190808035906020019064010000000081111561060c57600080fd5b82018360208201111561061e57600080fd5b8035906020019184600183028401116401000000008311171561064057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156106a357600080fd5b8201836020820111156106b557600080fd5b803590602001918460208302840111640100000000831117156106d757600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919080359060200190929190803590602001909291905050506110b5565b005b6107ff6004803603602081101561075c57600080fd5b810190808035906020019064010000000081111561077957600080fd5b82018360208201111561078b57600080fd5b803590602001918460018302840111640100000000831117156107ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061141d565b604051808215151515815260200191505060405180910390f35b6108636004803603608081101561082f57600080fd5b8101908080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611453565b005b61086d61173d565b6040518082815260200191505060405180910390f35b61088b611743565b604051808215151515815260200191505060405180910390f35b61095b600480360360208110156108bb57600080fd5b81019080803590602001906401000000008111156108d857600080fd5b8201836020820111156108ea57600080fd5b8035906020019184602083028401116401000000008311171561090c57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290505050611756565b005b6109656118c1565b6040518082815260200191505060405180910390f35b6109836118c7565b6040518082815260200191505060405180910390f35b6109c5600480360360208110156109af57600080fd5b81019080803590602001909291905050506118cd565b604051808060200186815260200185815260200184815260200180602001838103835288818151815260200191508051906020019080838360005b83811015610a1b578082015181840152602081019050610a00565b50505050905090810190601f168015610a485780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b83811015610a84578082015181840152602081019050610a69565b5050505090500197505050505050505060405180910390f35b610aa5611a4f565b6040518082815260200191505060405180910390f35b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610b1557600080fd5b600f60009054906101000a900460ff1615610b7b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526025815260200180611bb06025913960400191505060405180910390fd5b600f60029054906101000a900460ff1615610be1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526026815260200180611bd56026913960400191505060405180910390fd5b600f60019054906101000a900460ff1615610c47576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526022815260200180611b676022913960400191505060405180910390fd5b6001600f60006101000a81548160ff0219169083151502179055506001600f60016101000a81548160ff0219169083151502179055506001600f60026101000a81548160ff021916908315150217905550565b60008054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d305780601f10610d0557610100808354040283529160200191610d30565b820191906000526020600020905b815481529060010190602001808311610d1357829003601f168201915b505050505081565b6060600a805480602002602001604051908101604052809291908181526020018280548015610d8657602002820191906000526020600020905b815481526020019060010190808311610d72575b5050505050905090565b600a8181548110610d9d57fe5b906000526020600020016000915090505481565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610e0b57600080fd5b600f60029054906101000a900460ff1615610e8e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f5075626c6963206b65792063616e6e6f74206265206d6f64696669656400000081525060200191505060405180910390fd5b8060028190555050565b60096020528060005260406000206000915090508060000154908060010154908060020154908060030160009054906101000a900460ff16905084565b600b60009054906101000a900460ff1681565b6006602052806000526040600020600091509050806001015490806002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f9a5780601f10610f6f57610100808354040283529160200191610f9a565b820191906000526020600020905b815481529060010190602001808311610f7d57829003601f168201915b5050505050908060030154908060040154908060050154905085565b60006008600083815260200190815260200160002060009054906101000a900460ff169050919050565b600f60019054906101000a900460ff1681565b60015481565b60008060006009600085815260200190815260200160002060000154600960008681526020019081526020016000206001015460096000878152602001908152602001600020600201549250925092509193909250565b600f60029054906101000a900460ff1681565b600e5481565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461110f57600080fd5b600f60009054906101000a900460ff1615611192576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f596f752063616e6e6f7420616464206d6f7265207175657374696f6e7300000081525060200191505060405180910390fd5b600015156005866040518082805190602001908083835b602083106111cc57805182526020820191506020810190506020830392506111a9565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900460ff16151514611282576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f5175657374696f6e20616c72656164792072656769737465726564000000000081525060200191505060405180910390fd5b8460066000600754815260200190815260200160002060020190805190602001906112ae929190611a55565b5082600660006007548152602001908152602001600020600301819055508160066000600754815260200190815260200160002060040181905550806006600060075481526020019081526020016000206005018190555083516006600060075481526020019081526020016000206001018190555060008090505b84518110156113815784818151811061133f57fe5b6020026020010151600660006007548152602001908152602001600020600001600083815260200190815260200160002081905550808060010191505061132a565b5060016005866040518082805190602001908083835b602083106113ba5780518252602082019150602081019050602083039250611397565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff0219169083151502179055506007600081548092919060010191905055505050505050565b6005818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806114fc5750600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b611551576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526027815260200180611b896027913960400191505060405180910390fd5b600b60009054906101000a900460ff1661160057600115156008600086815260200190815260200160002060009054906101000a900460ff161515146115ff576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f566f746572206973206e6f7420656c696769626c6520746f20766f746500000081525060200191505060405180910390fd5b5b808210611658576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603f815260200180611b28603f913960400191505060405180910390fd5b6009600085815260200190815260200160002060030160009054906101000a900460ff166116aa57600a8490806001815401808255809150506001900390600052602060002001600090919091909150555b6116b2611ad5565b8381600001818152505082816020018181525050818160400181815250506001816060019015159081151581525050806009600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083151502179055509050505050505050565b60025481565b600f60009054906101000a900460ff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146117b057600080fd5b600f60019054906101000a900460ff1615611833576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f596f752063616e6e6f7420616464206d6f726520766f7465727300000000000081525060200191505060405180910390fd5b600b60009054906101000a900460ff1615611864576000600b60006101000a81548160ff0219169083151502179055505b60008090505b81518110156118bd5760016008600084848151811061188557fe5b6020026020010151815260200190815260200160002060006101000a81548160ff02191690831515021790555080600101905061186a565b5050565b600d5481565b600c5481565b60606000806000606060006006600088815260200190815260200160002090506060816001015467ffffffffffffffff8111801561190a57600080fd5b506040519080825280602002602001820160405280156119395781602001602082028036833780820191505090505b50905060008090505b8260010154811015611989578260000160008281526020019081526020016000205482828151811061197057fe5b6020026020010181815250508080600101915050611942565b508160020182600301548360040154846005015484848054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611a335780601f10611a0857610100808354040283529160200191611a33565b820191906000526020600020905b815481529060010190602001808311611a1657829003601f168201915b5050505050945096509650965096509650505091939590929450565b60075481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611a9657805160ff1916838001178555611ac4565b82800160010185558215611ac4579182015b82811115611ac3578251825591602001919060010190611aa8565b5b509050611ad19190611b02565b5090565b60405180608001604052806000801916815260200160008152602001600081526020016000151581525090565b611b2491905b80821115611b20576000816000905550600101611b08565b5090565b9056fe566572696669636174696f6e2074696d65206973206e6f742061667465722063617374696e672074696d652e20596f752063616e6e6f74206861636b206974416c6c20766f746572732068617665206265656e20616c7265616479206164646564596f7520617265206e6f7420656c696769626c6520746f207265676973746572206120766f7465416c6c207175657374696f6e732068617665206265656e20616c7265616479206164646564546865207075626c6963206b65792068617665206265656e20616c7265616479206164646564a2646970667358221220385960c1d864e5fa803f0b7ab3c2c3d611cf96f89ddbf3e2a19243d881956e8b64736f6c63430006060033";

    public static final String FUNC_ADDELIGIBLEVOTERS = "addEligibleVoters";

    public static final String FUNC_ADDQUESTION = "addQuestion";

    public static final String FUNC_CREATEDAT = "createdAt";

    public static final String FUNC_ELIGIBLEVOTERSADDED = "eligibleVotersAdded";

    public static final String FUNC_ENDAT = "endAt";

    public static final String FUNC_FREEZETHEELECTION = "freezeTheElection";

    public static final String FUNC_GETQUESTION = "getQuestion";

    public static final String FUNC_GETVOTE = "getVote";

    public static final String FUNC_GETVOTERSUUID = "getVotersUUID";

    public static final String FUNC_ISELECTIONPUBLIC = "isElectionPublic";

    public static final String FUNC_ISELIGIBLEVOTER = "isEligibleVoter";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_NOQUESTIONS = "noQuestions";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PUBKEY = "pubKey";

    public static final String FUNC_PUBLICKEYADDED = "publicKeyAdded";

    public static final String FUNC_QUESTIONS = "questions";

    public static final String FUNC_QUESTIONSADDED = "questionsAdded";

    public static final String FUNC_QUESTIONSREGISTERED = "questionsRegistered";

    public static final String FUNC_SERVERNODEADDR = "serverNodeAddr";

    public static final String FUNC_SETPUBLICKEY = "setPublicKey";

    public static final String FUNC_SHORT_NAME = "short_name";

    public static final String FUNC_STARTAT = "startAt";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_VOTERSWHOVOTED = "votersWhoVoted";

    public static final String FUNC_VOTES = "votes";

    @Deprecated
    protected HeliosElection(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HeliosElection(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HeliosElection(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HeliosElection(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addEligibleVoters(List<byte[]> _uuids) {
        final Function function = new Function(
                FUNC_ADDELIGIBLEVOTERS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_uuids, org.web3j.abi.datatypes.generated.Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addQuestion(String _name, List<byte[]> _answers, BigInteger _min, BigInteger _max, byte[] _type) {
        final Function function = new Function(
                FUNC_ADDQUESTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                                org.web3j.abi.datatypes.generated.Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_answers, org.web3j.abi.datatypes.generated.Bytes32.class)),
                        new org.web3j.abi.datatypes.generated.Int256(_min),
                        new org.web3j.abi.datatypes.generated.Int256(_max),
                        new org.web3j.abi.datatypes.generated.Bytes32(_type)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> createdAt() {
        final Function function = new Function(FUNC_CREATEDAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> eligibleVotersAdded() {
        final Function function = new Function(FUNC_ELIGIBLEVOTERSADDED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> endAt() {
        final Function function = new Function(FUNC_ENDAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> freezeTheElection() {
        final Function function = new Function(
                FUNC_FREEZETHEELECTION,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>>> getQuestion(BigInteger _questionId) {
        final Function function = new Function(FUNC_GETQUESTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_questionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>>>(function,
                new Callable<Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, byte[], List<byte[]>>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (byte[]) results.get(3).getValue(),
                                convertToNative((List<Bytes32>) results.get(4).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<byte[], BigInteger, BigInteger>> getVote(byte[] _uuid) {
        final Function function = new Function(FUNC_GETVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_uuid)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<byte[], BigInteger, BigInteger>>(function,
                new Callable<Tuple3<byte[], BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<byte[], BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], BigInteger, BigInteger>(
                                (byte[]) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getVotersUUID() {
        final Function function = new Function(FUNC_GETVOTERSUUID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isElectionPublic() {
        final Function function = new Function(FUNC_ISELECTIONPUBLIC,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isEligibleVoter(byte[] _uuid) {
        final Function function = new Function(FUNC_ISELIGIBLEVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_uuid)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> noQuestions() {
        final Function function = new Function(FUNC_NOQUESTIONS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> pubKey() {
        final Function function = new Function(FUNC_PUBKEY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> publicKeyAdded() {
        final Function function = new Function(FUNC_PUBLICKEYADDED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, BigInteger, byte[]>> questions(BigInteger param0) {
        final Function function = new Function(FUNC_QUESTIONS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, BigInteger, BigInteger, byte[]>>(function,
                new Callable<Tuple5<BigInteger, String, BigInteger, BigInteger, byte[]>>() {
                    @Override
                    public Tuple5<BigInteger, String, BigInteger, BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, BigInteger, BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (byte[]) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> questionsAdded() {
        final Function function = new Function(FUNC_QUESTIONSADDED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> questionsRegistered(String param0) {
        final Function function = new Function(FUNC_QUESTIONSREGISTERED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> serverNodeAddr() {
        final Function function = new Function(FUNC_SERVERNODEADDR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setPublicKey(byte[] _pubKey) {
        final Function function = new Function(
                FUNC_SETPUBLICKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_pubKey)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> short_name() {
        final Function function = new Function(FUNC_SHORT_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> startAt() {
        final Function function = new Function(FUNC_STARTAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(byte[] _uuid, byte[] _hash, BigInteger _castAt, BigInteger _verifiedAt) {
        final Function function = new Function(
                FUNC_VOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_uuid),
                        new org.web3j.abi.datatypes.generated.Bytes32(_hash),
                        new org.web3j.abi.datatypes.generated.Uint256(_castAt),
                        new org.web3j.abi.datatypes.generated.Uint256(_verifiedAt)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> votersWhoVoted(BigInteger param0) {
        final Function function = new Function(FUNC_VOTERSWHOVOTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Tuple4<byte[], BigInteger, BigInteger, Boolean>> votes(byte[] param0) {
        final Function function = new Function(FUNC_VOTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<byte[], BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple4<byte[], BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple4<byte[], BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<byte[], BigInteger, BigInteger, Boolean>(
                                (byte[]) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static HeliosElection load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HeliosElection(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HeliosElection load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HeliosElection(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HeliosElection load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HeliosElection(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HeliosElection load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HeliosElection(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HeliosElection> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _name, byte[] _short_name, BigInteger _createdAt, BigInteger _startAt, BigInteger _endAt, String _serverNodeAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                new org.web3j.abi.datatypes.generated.Bytes32(_short_name),
                new org.web3j.abi.datatypes.generated.Uint256(_createdAt),
                new org.web3j.abi.datatypes.generated.Uint256(_startAt),
                new org.web3j.abi.datatypes.generated.Uint256(_endAt),
                new org.web3j.abi.datatypes.Address(160, _serverNodeAddr)));
        return deployRemoteCall(HeliosElection.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HeliosElection> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _name, byte[] _short_name, BigInteger _createdAt, BigInteger _startAt, BigInteger _endAt, String _serverNodeAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                new org.web3j.abi.datatypes.generated.Bytes32(_short_name),
                new org.web3j.abi.datatypes.generated.Uint256(_createdAt),
                new org.web3j.abi.datatypes.generated.Uint256(_startAt),
                new org.web3j.abi.datatypes.generated.Uint256(_endAt),
                new org.web3j.abi.datatypes.Address(160, _serverNodeAddr)));
        return deployRemoteCall(HeliosElection.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HeliosElection> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _name, byte[] _short_name, BigInteger _createdAt, BigInteger _startAt, BigInteger _endAt, String _serverNodeAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                new org.web3j.abi.datatypes.generated.Bytes32(_short_name),
                new org.web3j.abi.datatypes.generated.Uint256(_createdAt),
                new org.web3j.abi.datatypes.generated.Uint256(_startAt),
                new org.web3j.abi.datatypes.generated.Uint256(_endAt),
                new org.web3j.abi.datatypes.Address(160, _serverNodeAddr)));
        return deployRemoteCall(HeliosElection.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HeliosElection> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _name, byte[] _short_name, BigInteger _createdAt, BigInteger _startAt, BigInteger _endAt, String _serverNodeAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                new org.web3j.abi.datatypes.generated.Bytes32(_short_name),
                new org.web3j.abi.datatypes.generated.Uint256(_createdAt),
                new org.web3j.abi.datatypes.generated.Uint256(_startAt),
                new org.web3j.abi.datatypes.generated.Uint256(_endAt),
                new org.web3j.abi.datatypes.Address(160, _serverNodeAddr)));
        return deployRemoteCall(HeliosElection.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
