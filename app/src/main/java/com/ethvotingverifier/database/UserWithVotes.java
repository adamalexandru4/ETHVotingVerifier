package com.ethvotingverifier.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithVotes {
    @Embedded
    public User user;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<User> votes;
}
