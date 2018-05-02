package com.qefee.androidroomstudy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.qefee.androidroomstudy.db.dao.StudentDAO;
import com.qefee.androidroomstudy.db.entity.Student;

/**
 * AppDatabase.
 * <ul>
 * <li>date: 2018/5/2</li>
 * </ul>
 *
 * @author tongjin
 */
@Database(entities = {Student.class}, version = 1) // 数据库注解，必须。entities指定实体类，version指定数据库版本
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDAO studentDao();
}
