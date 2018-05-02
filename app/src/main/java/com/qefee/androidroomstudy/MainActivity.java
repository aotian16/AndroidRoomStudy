package com.qefee.androidroomstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.qefee.androidroomstudy.activity.StudentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn_student = findViewById(R.id.btn_student);
        Button btn_course = findViewById(R.id.btn_course);

        btn_student.setOnClickListener(this::onClickStudent);
        btn_course.setOnClickListener(this::onClickCourset);
    }

    private void onClickStudent(View view) {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    private void onClickCourset(View view) {

    }
}
