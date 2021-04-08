package com.open.test.jetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {


    private static UserDataBase INSTANCE = null;

    public static synchronized UserDataBase getInstance(Context context){
        if(null == INSTANCE){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),UserDataBase.class,"db_user").build();
        }
        return INSTANCE;
    }

    public static void closeDatabase() {
        if (INSTANCE != null && INSTANCE.isOpen()) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    public abstract UserDao userDao();
}
