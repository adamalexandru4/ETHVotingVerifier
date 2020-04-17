package com.ethvotingverifier.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
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
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001aeb38038062001aeb833981810160405260c08110156200003757600080fd5b81019080805160405193929190846401000000008211156200005857600080fd5b838201915060208201858111156200006f57600080fd5b82518660018202830111640100000000821117156200008d57600080fd5b8083526020830192505050908051906020019080838360005b83811015620000c3578082015181840152602081019050620000a6565b50505050905090810190601f168015620000f15780820380516001836020036101000a031916815260200191505b50604052602001805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919050505033600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508560009080519060200190620001869291906200020b565b50846001819055506001600a60006101000a81548160ff02191690831515021790555080600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555083600b8190555082600c8190555081600d81905550505050505050620002ba565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200024e57805160ff19168380011785556200027f565b828001600101855582156200027f579182015b828111156200027e57825182559160200191906001019062000261565b5b5090506200028e919062000292565b5090565b620002b791905b80821115620002b357600081600090555060010162000299565b5090565b90565b61182180620002ca6000396000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c806351550869116100ad578063ac2a5dfd11610071578063ac2a5dfd14610724578063b417094214610742578063c7446565146107fa578063cf09e0d014610818578063d3660736146108365761012c565b8063515508691461042a5780637cc3ae8c1461047a5780638fb229eb14610498578063a0d4b56714610605578063a1f1d75d146106d85761012c565b80632b38cd96116100f45780632b38cd961461028d5780632dd96f2b146102e857806331b1b9781461030a578063462d8efb146103c657806349626cb11461040c5761012c565b806305b31a6b1461013157806306fdde031461013b5780630f639822146101be57806310b01b851461021d5780631371cd1d1461025f575b600080fd5b610139610854565b005b610143610a33565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610183578082015181840152602081019050610168565b50505050905090810190601f1680156101b05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101c6610ad1565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156102095780820151818401526020810190506101ee565b505050509050019250505060405180910390f35b6102496004803603602081101561023357600080fd5b8101908080359060200190929190505050610b29565b6040518082815260200191505060405180910390f35b61028b6004803603602081101561027557600080fd5b8101908080359060200190929190505050610b4a565b005b6102b9600480360360208110156102a357600080fd5b8101908080359060200190929190505050610c31565b604051808581526020018481526020018381526020018215151515815260200194505050505060405180910390f35b6102f0610c6e565b604051808215151515815260200191505060405180910390f35b6103366004803603602081101561032057600080fd5b8101908080359060200190929190505050610c81565b6040518080602001858152602001848152602001838152602001828103825286818151815260200191508051906020019080838360005b8381101561038857808201518184015260208101905061036d565b50505050905090810190601f1680156103b55780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6103f2600480360360208110156103dc57600080fd5b8101908080359060200190929190505050610d56565b604051808215151515815260200191505060405180910390f35b610414610d80565b6040518082815260200191505060405180910390f35b6104566004803603602081101561044057600080fd5b8101908080359060200190929190505050610d86565b60405180848152602001838152602001828152602001935050505060405180910390f35b610482610ddd565b6040518082815260200191505060405180910390f35b610603600480360360a08110156104ae57600080fd5b81019080803590602001906401000000008111156104cb57600080fd5b8201836020820111156104dd57600080fd5b803590602001918460018302840111640100000000831117156104ff57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561056257600080fd5b82018360208201111561057457600080fd5b8035906020019184602083028401116401000000008311171561059657600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019092919080359060200190929190505050610de3565b005b6106be6004803603602081101561061b57600080fd5b810190808035906020019064010000000081111561063857600080fd5b82018360208201111561064a57600080fd5b8035906020019184600183028401116401000000008311171561066c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506110f8565b604051808215151515815260200191505060405180910390f35b610722600480360360808110156106ee57600080fd5b810190808035906020019092919080359060200190929190803590602001909291908035906020019092919050505061112e565b005b61072c611418565b6040518082815260200191505060405180910390f35b6107f86004803603602081101561075857600080fd5b810190808035906020019064010000000081111561077557600080fd5b82018360208201111561078757600080fd5b803590602001918460208302840111640100000000831117156107a957600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f82011690508083019250505050505050919291929050505061141e565b005b610802611589565b6040518082815260200191505060405180910390f35b61082061158f565b6040518082815260200191505060405180910390f35b61083e611595565b6040518082815260200191505060405180910390f35b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146108ae57600080fd5b600e60009054906101000a900460ff1615610914576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806117a16025913960400191505060405180910390fd5b600e60029054906101000a900460ff161561097a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001806117c66026913960400191505060405180910390fd5b600e60019054906101000a900460ff16156109e0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001806117586022913960400191505060405180910390fd5b6001600e60006101000a81548160ff0219169083151502179055506001600e60016101000a81548160ff0219169083151502179055506001600e60026101000a81548160ff021916908315150217905550565b60008054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ac95780601f10610a9e57610100808354040283529160200191610ac9565b820191906000526020600020905b815481529060010190602001808311610aac57829003601f168201915b505050505081565b60606009805480602002602001604051908101604052809291908181526020018280548015610b1f57602002820191906000526020600020905b815481526020019060010190808311610b0b575b5050505050905090565b60098181548110610b3657fe5b906000526020600020016000915090505481565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610ba457600080fd5b600e60029054906101000a900460ff1615610c27576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f5075626c6963206b65792063616e6e6f74206265206d6f64696669656400000081525060200191505060405180910390fd5b8060028190555050565b60086020528060005260406000206000915090508060000154908060010154908060020154908060030160009054906101000a900460ff16905084565b600a60009054906101000a900460ff1681565b60058181548110610c8e57fe5b9060005260206000209060050201600091509050806000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d3a5780601f10610d0f57610100808354040283529160200191610d3a565b820191906000526020600020905b815481529060010190602001808311610d1d57829003601f168201915b5050505050908060010154908060020154908060040154905084565b60006007600083815260200190815260200160002060009054906101000a900460ff169050919050565b60015481565b60008060006008600085815260200190815260200160002060000154600860008681526020019081526020016000206001015460086000878152602001908152602001600020600201549250925092509193909250565b600d5481565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610e3d57600080fd5b600e60009054906101000a900460ff1615610ec0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f596f752063616e6e6f7420616464206d6f7265207175657374696f6e7300000081525060200191505060405180910390fd5b600015156006866040518082805190602001908083835b60208310610efa5780518252602082019150602081019050602083039250610ed7565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900460ff16151514610fb0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f5175657374696f6e20616c72656164792072656769737465726564000000000081525060200191505060405180910390fd5b610fb86115a2565b858160000181905250838160200181815250508281604001818152505081816080018181525050848160600181905250600581908060018154018082558091505060019003906000526020600020906005020160009091909190915060008201518160000190805190602001906110309291906115d4565b5060208201518160010155604082015181600201556060820151816003019080519060200190611061929190611654565b5060808201518160040155505060016006876040518082805190602001908083835b602083106110a65780518252602082019150602081019050602083039250611083565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548160ff021916908315150217905550505050505050565b6006818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806111d75750600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b61122c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602781526020018061177a6027913960400191505060405180910390fd5b600a60009054906101000a900460ff166112db57600115156007600086815260200190815260200160002060009054906101000a900460ff161515146112da576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f566f746572206973206e6f7420656c696769626c6520746f20766f746500000081525060200191505060405180910390fd5b5b808210611333576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603f815260200180611719603f913960400191505060405180910390fd5b6008600085815260200190815260200160002060030160009054906101000a900460ff166113855760098490806001815401808255809150506001900390600052602060002001600090919091909150555b61138d6116a1565b8381600001818152505082816020018181525050818160400181815250506001816060019015159081151581525050806008600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083151502179055509050505050505050565b60025481565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461147857600080fd5b600e60019054906101000a900460ff16156114fb576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f596f752063616e6e6f7420616464206d6f726520766f7465727300000000000081525060200191505060405180910390fd5b600a60009054906101000a900460ff161561152c576000600a60006101000a81548160ff0219169083151502179055505b60008090505b81518110156115855760016007600084848151811061154d57fe5b6020026020010151815260200190815260200160002060006101000a81548160ff021916908315150217905550806001019050611532565b5050565b600c5481565b600b5481565b6000600580549050905090565b6040518060a0016040528060608152602001600081526020016000815260200160608152602001600080191681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061161557805160ff1916838001178555611643565b82800160010185558215611643579182015b82811115611642578251825591602001919060010190611627565b5b50905061165091906116ce565b5090565b828054828255906000526020600020908101928215611690579160200282015b8281111561168f578251825591602001919060010190611674565b5b50905061169d91906116f3565b5090565b60405180608001604052806000801916815260200160008152602001600081526020016000151581525090565b6116f091905b808211156116ec5760008160009055506001016116d4565b5090565b90565b61171591905b808211156117115760008160009055506001016116f9565b5090565b9056fe566572696669636174696f6e2074696d65206973206e6f742061667465722063617374696e672074696d652e20596f752063616e6e6f74206861636b206974416c6c20766f746572732068617665206265656e20616c7265616479206164646564596f7520617265206e6f7420656c696769626c6520746f207265676973746572206120766f7465416c6c207175657374696f6e732068617665206265656e20616c7265616479206164646564546865207075626c6963206b65792068617665206265656e20616c7265616479206164646564a264697066735822122025b0bd49d666d9d90b0743a097d3dba44896385b8b23da8ce57e8375f804597b64736f6c63430006060033";

    public static final String FUNC_ADDELIGIBLEVOTERS = "addEligibleVoters";

    public static final String FUNC_ADDQUESTION = "addQuestion";

    public static final String FUNC_CREATEDAT = "createdAt";

    public static final String FUNC_ENDAT = "endAt";

    public static final String FUNC_FREEZETHEELECTION = "freezeTheElection";

    public static final String FUNC_GETNOQUESTIONS = "getNoQuestions";

    public static final String FUNC_GETVOTE = "getVote";

    public static final String FUNC_GETVOTERSUUID = "getVotersUUID";

    public static final String FUNC_ISELECTIONPUBLIC = "isElectionPublic";

    public static final String FUNC_ISELIGIBLEVOTER = "isEligibleVoter";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_PUBKEY = "pubKey";

    public static final String FUNC_QUESTIONS = "questions";

    public static final String FUNC_QUESTIONSREGISTERED = "questionsRegistered";

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

    public RemoteFunctionCall<BigInteger> getNoQuestions() {
        final Function function = new Function(FUNC_GETNOQUESTIONS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<byte[]> pubKey() {
        final Function function = new Function(FUNC_PUBKEY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, byte[]>> questions(BigInteger param0) {
        final Function function = new Function(FUNC_QUESTIONS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, byte[]>>(function,
                new Callable<Tuple4<String, BigInteger, BigInteger, byte[]>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, BigInteger, byte[]>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (byte[]) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> questionsRegistered(String param0) {
        final Function function = new Function(FUNC_QUESTIONSREGISTERED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
