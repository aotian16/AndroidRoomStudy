package com.qefee.androidroomstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.qefee.androidroomstudy.R;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(this::onClickInsert);

        Button btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this::onClickUpdate);

        Button btn_list = findViewById(R.id.btn_list);
        btn_list.setOnClickListener(this::onClickList);

        Button btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(this::onClickSelect);
    }

    private void onClickInsert(View view) {
        Intent intent = new Intent(this, InsertStudentActivity.class);
        startActivity(intent);
    }

    private void onClickUpdate(View view) {
        Intent intent = new Intent(this, UpdateStudentActivity.class);
        startActivity(intent);
    }

    private void onClickList(View view) {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }

    private void onClickSelect(View view) {
        Intent intent = new Intent(this, FindStudentByNameActivity.class);
        startActivity(intent);
    }
}
