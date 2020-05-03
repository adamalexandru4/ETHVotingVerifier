package com.ethvotingverifier.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Vote")
public class Vote {
    @PrimaryKey(autoGenerate = true)
    public int voteId;

    @ColumnInfo(name = "HEX Value")
    public String hexValue;

    @ColumnInfo(name = "Cast at")
    public String castAt;

    @ColumnInfo(name = "Verified at")
    public String verifiedAt;

    @ColumnInfo(name = "Verified")
    public Boolean verified;

    @ColumnInfo(name = "Checked at")
    @TypeConverters({DateConverter.class})
    public Date checkedDate;

    public int userId;

    public Vote(String hexValue, String castAt, String verifiedAt, Boolean verified, Date checkedDate) {
        this.hexValue = hexValue;
        this.castAt = castAt;
        this.verifiedAt = verifiedAt;
        this.verified = verified;
        this.checkedDate = checkedDate;
    }

}
