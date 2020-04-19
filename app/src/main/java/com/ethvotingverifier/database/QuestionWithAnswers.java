package com.ethvotingverifier.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class QuestionWithAnswers {
    @Embedded
    public Question question;

    @Relation(parentColumn = "questionId", entityColumn = "questionId")
    public List<Answer> answers;
}
