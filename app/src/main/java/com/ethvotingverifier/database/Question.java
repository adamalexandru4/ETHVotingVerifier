package com.ethvotingverifier.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Question")
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int questionId;

    @ColumnInfo(name = "Title")
    public String title;

    @ColumnInfo(name = "min")
    public int min;

    @ColumnInfo(name = "max")
    public int max;

    @ColumnInfo(name = "Result type")
    public String resultType;

    public Question(String title, int min, int max, String resultType) {
        this.title = title;
        this.min = min;
        this.max = max;
        this.resultType = resultType;
    }


}
