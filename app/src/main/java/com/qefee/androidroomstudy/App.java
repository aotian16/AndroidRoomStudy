package com.qefee.androidroomstudy;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

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

        // 升级Room
//        Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name")
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
    }

//    // 定义升级，从版本1升级到2
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))"); // 新建一个table
//        }
//    };
//
//    // 定义升级，从版本2升级到3
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Book "
//                    + " ADD COLUMN pub_year INTEGER"); // 插入字段
//        }
//    };

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
