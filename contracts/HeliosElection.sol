pragma solidity 0.6.6;
contract HeliosElection {

    struct Vote {
        bytes32 hash;
        uint castAt;
        uint verifiedAt;
        bool alreadyVoted;
    }

    struct Question {
        string question;
        int min;
        int max;
        bytes32[] answers;
        bytes32 resultType;
    }

    string public name;
    bytes32 public short_name; // for URL
    bytes32 public pubKey;

    address owner;
    address serverNodeAddr;

    Question[] public questions;
    mapping(string => bool) public questionsRegistered;
    
    mapping(bytes32 => bool) eligibleVoters;
    mapping(bytes32 => Vote) public votes;
    bytes32[] public votersWhoVoted;

    bool public isElectionPublic;
    uint public createdAt;
    uint public startAt;
    uint public endAt;

    bool questionsAdded;
    bool eligibleVotersAdded;
    bool publicKeyAdded;

    constructor(string memory _name, bytes32 _short_name,
                uint _createdAt, uint _startAt, uint _endAt,
                address _serverNodeAddr) public {
        owner = msg.sender;
        name = _name;
        short_name = _short_name;

        isElectionPublic = true;
        serverNodeAddr = _serverNodeAddr;

        createdAt = _createdAt;
        startAt = _startAt;
        endAt = _endAt;
    }

    modifier onlyOwner () {
      require(msg.sender == owner);
      _;
    }

    modifier onlyServerNode() {
        require(msg.sender == serverNodeAddr);
        _;
    }

    function addEligibleVoters(bytes32[] memory _uuids) public onlyOwner {
        require(!eligibleVotersAdded, "You cannot add more voters");

        if(isElectionPublic)
            isElectionPublic = false;

        for (uint i = 0; i < _uuids.length; ++i) {
            eligibleVoters[_uuids[i]] = true;
        }
    }

    function isEligibleVoter(bytes32 _uuid) public view returns(bool) {
        return eligibleVoters[_uuid];
    }

    function getNoQuestions() public view returns(uint) {
        return questions.length;
    }

    function addQuestion(string memory _name, bytes32[] memory _answers, int _min, int _max, bytes32 _type) public onlyOwner {
        require(!questionsAdded, "You cannot add more questions");
        require(questionsRegistered[_name] == false, "Question already registered");

        Question memory newQuestion;
        newQuestion.question = _name;
        newQuestion.min = _min;
        newQuestion.max = _max;
        newQuestion.resultType = _type;
        newQuestion.answers = _answers;
        questions.push(newQuestion);
        
        questionsRegistered[_name] = true;

    }

    function freezeTheElection() public onlyOwner {
        require(!questionsAdded, "All questions have been already added");
        require(!publicKeyAdded, "The public key have been already added");
        require(!eligibleVotersAdded, "All voters have been already added");

        questionsAdded = true;
        eligibleVotersAdded = true;
        publicKeyAdded = true;
    }

    function setPublicKey(bytes32 _pubKey) public onlyOwner {
        require(!publicKeyAdded, "Public key cannot be modified");

        pubKey = _pubKey;
    }

    function vote(bytes32 _uuid, bytes32 _hash, uint _castAt, uint _verifiedAt) public {
        require(msg.sender == owner || msg.sender == serverNodeAddr, "You are not eligible to register a vote");

        if(!isElectionPublic)
            require(eligibleVoters[_uuid] == true, "Voter is not eligible to vote");

        require(_castAt < _verifiedAt, "Verification time is not after casting time. You cannot hack it");

        if(!votes[_uuid].alreadyVoted)
            votersWhoVoted.push(_uuid);

        Vote memory newVote;
        newVote.hash = _hash;
        newVote.castAt = _castAt;
        newVote.verifiedAt = _verifiedAt;
        newVote.alreadyVoted = true;

        votes[_uuid] = newVote;
    }

    function getVotersUUID() public view returns(bytes32[] memory) {
        return votersWhoVoted;
    }

    function getVote(bytes32 _uuid) public view returns (bytes32 _hash, uint _castAt, uint _verifiedAt)  {
        return (votes[_uuid].hash, votes[_uuid].castAt, votes[_uuid].verifiedAt);
    }
}