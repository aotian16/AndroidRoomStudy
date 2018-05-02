package com.qefee.androidroomstudy;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.qefee.androidroomstudy.db.AppDatabase;

/**
 * App.
 * <ul>
 * <li>date: 2018/5/2</li>
 * </ul>
 *
 * @author tongjin
 */
public class App extends Application {

    private static App instance;
    private static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initDb();
    }

    private void initDb() {
        // 这里初始化room
        db = Room.databaseBuilder(this,
                AppDatabase.class, "database-name").build();
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDatabase getDb() {
        return db;
    }
}
