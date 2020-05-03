package com.ethvotingverifier.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Vote.class}, exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "App_DB";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }

        return instance;
    }

    public abstract VotesDao votesDao();
}
