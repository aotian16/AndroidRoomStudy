package com.qefee.androidroomstudy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.qefee.androidroomstudy.db.entity.Student;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * StudentDAO.
 * <ul>
 * <li>date: 2018/5/2</li>
 * </ul>
 *
 * @author tongjin
 */
@Dao
public interface StudentDAO {
    @Query("SELECT * FROM student")
    Single<List<Student>> getAll();

    @Query("SELECT * FROM student WHERE uid IN (:ids)")
    Single<List<Student>> loadAllByIds(int[] ids);

    @Query("SELECT * FROM student WHERE uid = :id")
    Single<Student> findById(int id);

    @Query("SELECT * FROM student WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Maybe<Student> findByName(String first, String last);

    @Insert
    List<Long> insertAll(Student... students);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);
}
