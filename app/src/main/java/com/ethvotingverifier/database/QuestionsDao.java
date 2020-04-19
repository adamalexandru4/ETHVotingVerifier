package com.ethvotingverifier.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class QuestionsDao {
    @Transaction
    @Query("SELECT * FROM Question")
    public abstract List<QuestionWithAnswers> getQuestionsWithAnswers();

    @Insert(onConflict = REPLACE)
    abstract long insertQuestion(Question question);

    @Insert(onConflict = REPLACE)
    abstract void insertAnswers(List<Answer> answers);

    public void insertQuestionWithAnswers(Question question, List<Answer> answers) {
        final long questionId = insertQuestion(question);

        for(Answer answer : answers) {
            answer.setQuestionId(questionId);
        }
        insertAnswers(answers);
    }
}
