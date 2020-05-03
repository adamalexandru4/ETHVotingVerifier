package com.ethvotingverifier.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.room.OnConflictStrategy.ABORT;
import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class VotesDao {
    @Query("SELECT * FROM User WHERE `Public key` = :publicKey")
    public abstract User getUser(String publicKey);

    @Insert(onConflict = ABORT)
    public abstract void insertUser(User user);

    @Insert(onConflict = IGNORE)
    abstract void insertVote(Vote vote);

    @Query("SELECT * FROM Vote WHERE userId = :userId")
    abstract List<Vote> getVotes(int userId);

    @Query("SELECT * FROM Vote WHERE userId = :userId AND `Checked at` BETWEEN :startDate and :endDate " +
            "ORDER BY `Checked at`")
    @TypeConverters({DateConverter.class})
    abstract List<Vote> getVotesBetweenDates(int userId, Date startDate, Date endDate);

    public void insertVote(Vote vote, User user) {
        final int userID = getUser(user.publicKey).userId;
        vote.userId = userID;
        insertVote(vote);
    }

    public ArrayList<Vote> getUserVotes(String publicKey) {
        ArrayList<Vote> votes;
        final int userID = getUser(publicKey).userId;
        votes = new ArrayList(getVotes(userID));
        return votes;
    }

    public ArrayList<Vote> getUserVotesBetweenDates(String publicKey, Date startDate, Date endDate) {
        ArrayList<Vote> votes;
        final int userID = getUser(publicKey).userId;
        votes = new ArrayList(getVotesBetweenDates(userID, startDate, endDate));
        return votes;
    }
}
