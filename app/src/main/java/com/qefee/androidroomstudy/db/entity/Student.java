package com.qefee.androidroomstudy.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Student.
 * <ul>
 * <li>date: 2018/5/2</li>
 * </ul>
 *
 * @author tongjin
 */
@Entity // 实体类注解，必须
public class Student {
    @PrimaryKey(autoGenerate = true) // 主键, autoGenerate表示自增长
    private long uid;

    @ColumnInfo(name = "first_name") // name指定的是表的字段名
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    private int age; // 可以不指定ColumnInfo，那么表的字段名和属性名一致

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
