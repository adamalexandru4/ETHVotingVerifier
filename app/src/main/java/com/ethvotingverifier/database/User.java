package com.ethvotingverifier.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "User", indices = {@Index(value = {"Public key"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    @ColumnInfo(name = "Public key")
    public String publicKey;

    public User(String publicKey) {
        this.publicKey = publicKey;
    }

}
