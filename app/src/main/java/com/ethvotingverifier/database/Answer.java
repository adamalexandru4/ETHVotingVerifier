package com.ethvotingverifier.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Answer")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    public int answerId;

    @ColumnInfo(name = "Name")
    private String name;

    private long questionId;

    public Answer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
