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
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620019c1380380620019c1833981810160405260c08110156200003757600080fd5b81019080805160405193929190846401000000008211156200005857600080fd5b9083019060208201858111156200006e57600080fd5b82516401000000008111828201881017156200008957600080fd5b82525081516020918201929091019080838360005b83811015620000b85781810151838201526020016200009e565b50505050905090810190601f168015620000e65780820380516001836020036101000a031916815260200191505b506040908152602082810151918301516060840151608085015160a090950151600380546001600160a01b03191633179055885194975091955093929091620001359160009189019062000185565b50600194909455600b805461ffff1916610100179055600480546001600160a01b039095166001600160a01b0319909516949094179093556000600755600c91909155600d55600e55506200022a565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620001c857805160ff1916838001178555620001f8565b82800160010185558215620001f8579182015b82811115620001f8578251825591602001919060010190620001db565b50620002069291506200020a565b5090565b6200022791905b8082111562000206576000815560010162000211565b90565b611787806200023a6000396000f3fe608060405234801561001057600080fd5b50600436106101da5760003560e01c80637cc3ae8c11610104578063ac2a5dfd116100a2578063cf09e0d011610071578063cf09e0d0146107bf578063db88176f146107c7578063dbc7c5f7146108b2578063eccc0387146108ba576101da565b8063ac2a5dfd14610706578063b2255c441461070e578063b417094214610716578063c7446565146107b7576101da565b80638da5cb5b116100de5780638da5cb5b146104fa5780638fb229eb14610502578063a0d4b56714610633578063a1f1d75d146106d7576101da565b80637cc3ae8c146104b15780637df17823146104b957806383b3749c146104dd576101da565b80632dd96f2b1161017c57806349626cb11161014b57806349626cb11461044957806351550869146104515780635a50f0751461048c5780637957ca73146104a9576101da565b80632dd96f2b1461035757806331b1b97814610373578063462d8efb1461042457806347ecd51814610441576101da565b80630f639822116101b85780630f6398221461026e57806310b01b85146102c65780631371cd1d146102f55780632b38cd9614610312576101da565b806305b31a6b146101df57806306fdde03146101e9578063092a5cce14610266575b600080fd5b6101e76108c2565b005b6101f16109d2565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561022b578181015183820152602001610213565b50505050905090810190601f1680156102585780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101e7610a60565b610276610a85565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102b257818101518382015260200161029a565b505050509050019250505060405180910390f35b6102e3600480360360208110156102dc57600080fd5b5035610ade565b60408051918252519081900360200190f35b6101e76004803603602081101561030b57600080fd5b5035610afc565b61032f6004803603602081101561032857600080fd5b5035610b76565b6040805194855260208501939093528383019190915215156060830152519081900360800190f35b61035f610ba0565b604080519115158252519081900360200190f35b6103906004803603602081101561038957600080fd5b5035610bae565b6040518086815260200180602001858152602001848152602001838152602001828103825286818151815260200191508051906020019080838360005b838110156103e55781810151838201526020016103cd565b50505050905090810190601f1680156104125780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61035f6004803603602081101561043a57600080fd5b5035610c7c565b61035f610c91565b6102e3610c9f565b61046e6004803603602081101561046757600080fd5b5035610ca5565b60408051938452602084019290925282820152519081900360600190f35b6101e7600480360360208110156104a257600080fd5b5035610cc4565b61035f610d8c565b6102e3610d9b565b6104c1610da1565b604080516001600160a01b039092168252519081900360200190f35b61035f600480360360208110156104f357600080fd5b5035610db0565b6104c1610dc5565b6101e7600480360360a081101561051857600080fd5b810190602081018135600160201b81111561053257600080fd5b82018360208201111561054457600080fd5b803590602001918460018302840111600160201b8311171561056557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156105b757600080fd5b8201836020820111156105c957600080fd5b803590602001918460208302840111600160201b831117156105ea57600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295505082359350505060208101359060400135610dd4565b61035f6004803603602081101561064957600080fd5b810190602081018135600160201b81111561066357600080fd5b82018360208201111561067557600080fd5b803590602001918460018302840111600160201b8311171561069657600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061102d945050505050565b6101e7600480360360808110156106ed57600080fd5b508035906020810135906040810135906060013561104d565b6102e36112db565b61035f6112e1565b6101e76004803603602081101561072c57600080fd5b810190602081018135600160201b81111561074657600080fd5b82018360208201111561075857600080fd5b803590602001918460208302840111600160201b8311171561077957600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295506112ea945050505050565b6102e36113cd565b6102e36113d3565b6107e4600480360360208110156107dd57600080fd5b50356113d9565b604051808060200186815260200185815260200184815260200180602001838103835288818151815260200191508051906020019080838360005b8381101561083757818101518382015260200161081f565b50505050905090810190601f1680156108645780820380516001836020036101000a031916815260200191505b508381038252845181528451602091820191808701910280838360005b83811015610899578181015183820152602001610881565b5050505090500197505050505050505060405180910390f35b61035f611536565b6102e361153f565b6003546001600160a01b031633146108d957600080fd5b600f5460ff161561091b5760405162461bcd60e51b81526004018080602001828103825260258152602001806116dd6025913960400191505060405180910390fd5b600f5462010000900460ff16156109635760405162461bcd60e51b815260040180806020018281038252602681526020018061172c6026913960400191505060405180910390fd5b600f54610100900460ff16156109aa5760405162461bcd60e51b815260040180806020018281038252602281526020018061166d6022913960400191505060405180910390fd5b600f805462ff00001961ff001960ff1990921660011791909116610100171662010000179055565b6000805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610a585780601f10610a2d57610100808354040283529160200191610a58565b820191906000526020600020905b815481529060010190602001808311610a3b57829003601f168201915b505050505081565b6003546001600160a01b03163314610a7757600080fd5b6003546001600160a01b0316ff5b6060600a805480602002602001604051908101604052809291908181526020018280548015610ad357602002820191906000526020600020905b815481526020019060010190808311610abf575b505050505090505b90565b600a8181548110610aeb57fe5b600091825260209091200154905081565b6003546001600160a01b03163314610b1357600080fd5b600f5462010000900460ff1615610b71576040805162461bcd60e51b815260206004820152601d60248201527f5075626c6963206b65792063616e6e6f74206265206d6f646966696564000000604482015290519081900360640190fd5b600255565b60096020526000908152604090208054600182015460028301546003909301549192909160ff1684565b600b54610100900460ff1681565b6006602052806000526040600020600091509050806001015490806002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c605780601f10610c3557610100808354040283529160200191610c60565b820191906000526020600020905b815481529060010190602001808311610c4357829003601f168201915b5050505050908060030154908060040154908060050154905085565b60009081526008602052604090205460ff1690565b600f54610100900460ff1681565b60015481565b6000908152600960205260409020805460018201546002909201549092565b6003546001600160a01b0316331480610ce757506004546001600160a01b031633145b610d225760405162461bcd60e51b81526004018080602001828103825260298152602001806116446029913960400191505060405180910390fd5b600b5460ff1615610d7a576040805162461bcd60e51b815260206004820152601c60248201527f456c656374696f6e20697320616c72656164792073746f707065642100000000604482015290519081900360640190fd5b600b805460ff19166001179055600e55565b600f5462010000900460ff1681565b600e5481565b6004546001600160a01b031681565b60086020526000908152604090205460ff1681565b6003546001600160a01b031681565b6003546001600160a01b03163314610deb57600080fd5b600f5460ff1615610e43576040805162461bcd60e51b815260206004820152601d60248201527f596f752063616e6e6f7420616464206d6f7265207175657374696f6e73000000604482015290519081900360640190fd5b6005856040518082805190602001908083835b60208310610e755780518252601f199092019160209182019101610e56565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff16159150610efa9050576040805162461bcd60e51b815260206004820152601b60248201527f5175657374696f6e20616c726561647920726567697374657265640000000000604482015290519081900360640190fd5b60075460009081526006602090815260409091208651610f2292600290920191880190611545565b506007805460009081526006602052604080822060030186905582548252808220600401859055825482528082206005018490558651925482528120600101919091555b8451811015610fab57848181518110610f7b57fe5b60209081029190910181015160075460009081526006835260408082208583529093529190912055600101610f66565b5060016005866040518082805190602001908083835b60208310610fe05780518252601f199092019160209182019101610fc1565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220805460ff19169315159390931790925550506007805460010190555050505050565b805160208183018101805160058252928201919093012091525460ff1681565b6003546001600160a01b031633148061107057506004546001600160a01b031633145b6110ab5760405162461bcd60e51b81526004018080602001828103825260278152602001806116b66027913960400191505060405180910390fd5b600b5460ff16156110fb576040805162461bcd60e51b8152602060048201526015602482015274456c656374696f6e2069732066696e69736865642160581b604482015290519081900360640190fd5b8082106111395760405162461bcd60e51b815260040180806020018281038252603f815260200180611605603f913960400191505060405180910390fd5b600d5482116111795760405162461bcd60e51b815260040180806020018281038252602a815260200180611702602a913960400191505060405180910390fd5b600e5482106111b95760405162461bcd60e51b815260040180806020018281038252602781526020018061168f6027913960400191505060405180910390fd5b600b54610100900460ff166112305760008481526008602052604090205460ff161515600114611230576040805162461bcd60e51b815260206004820152601d60248201527f566f746572206973206e6f7420656c696769626c6520746f20766f7465000000604482015290519081900360640190fd5b60008481526009602052604090206003015460ff1661127f57600a80546001810182556000919091527fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490555b6112876115c3565b92835260208084019283526040808501928352600160608601818152600097885260099093529520935184559151938301939093559151600282015590516003909101805460ff1916911515919091179055565b60025481565b600f5460ff1681565b6003546001600160a01b0316331461130157600080fd5b600f54610100900460ff161561135e576040805162461bcd60e51b815260206004820152601a60248201527f596f752063616e6e6f7420616464206d6f726520766f74657273000000000000604482015290519081900360640190fd5b600b54610100900460ff161561137a57600b805461ff00191690555b60005b81518110156113c95760016008600084848151811061139857fe5b6020908102919091018101518252810191909152604001600020805460ff191691151591909117905560010161137d565b5050565b600d5481565b600c5481565b6000818152600660205260408120600181015460609291829182918591829067ffffffffffffffff8111801561140e57600080fd5b50604051908082528060200260200182016040528015611438578160200160208202803683370190505b50905060005b826001015481101561147a57600081815260208490526040902054825183908390811061146757fe5b602090810291909101015260010161143e565b5060038201546004830154600584015460028086018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529195949392879291879183018282801561151a5780601f106114ef5761010080835404028352916020019161151a565b820191906000526020600020905b8154815290600101906020018083116114fd57829003601f168201915b5050505050945096509650965096509650505091939590929450565b600b5460ff1681565b60075481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061158657805160ff19168380011785556115b3565b828001600101855582156115b3579182015b828111156115b3578251825591602001919060010190611598565b506115bf9291506115ea565b5090565b60408051608081018252600080825260208201819052918101829052606081019190915290565b610adb91905b808211156115bf57600081556001016115f056fe566572696669636174696f6e2074696d65206973206e6f742061667465722063617374696e672074696d652e20596f752063616e6e6f74206861636b206974596f7520617265206e6f7420656c696769626c6520746f2073746f702074686520656c656374696f6e416c6c20766f746572732068617665206265656e20616c7265616479206164646564566f7465206973206e6f7420617661696c61626c6520616674657220656e64696e672074696d65596f7520617265206e6f7420656c696769626c6520746f207265676973746572206120766f7465416c6c207175657374696f6e732068617665206265656e20616c7265616479206164646564566f7465206973206e6f7420617661696c61626c65206265666f7265207374617274696e672074696d65546865207075626c6963206b65792068617665206265656e20616c7265616479206164646564a26469706673582212204ba4b17d8527d7018783ae69638d3ce68819c9b8c82e696550d96c8b661e537b64736f6c63430006060033";

    public static final String FUNC_ADDELIGIBLEVOTERS = "addEligibleVoters";

    public static final String FUNC_ADDQUESTION = "addQuestion";

    public static final String FUNC_CREATEDAT = "createdAt";

    public static final String FUNC_DESTROYCONTRACT = "destroyContract";

    public static final String FUNC_ELECTIONISFINISHED = "electionIsFinished";

    public static final String FUNC_ELIGIBLEVOTERS = "eligibleVoters";

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

    public static final String FUNC_STOPELECTION = "stopElection";

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

    public RemoteFunctionCall<TransactionReceipt> destroyContract() {
        final Function function = new Function(
                FUNC_DESTROYCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> electionIsFinished() {
        final Function function = new Function(FUNC_ELECTIONISFINISHED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> eligibleVoters(byte[] param0) {
        final Function function = new Function(FUNC_ELIGIBLEVOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<TransactionReceipt> stopElection(BigInteger _endAt) {
        final Function function = new Function(
                FUNC_STOPELECTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_endAt)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
