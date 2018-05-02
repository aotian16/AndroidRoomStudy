package com.qefee.androidroomstudy.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.qefee.androidroomstudy.App;
import com.qefee.androidroomstudy.R;
import com.qefee.androidroomstudy.db.entity.Student;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InsertStudentActivity extends AppCompatActivity {
    /**
     * log tag for InsertStudentActivity
     */
    private static final String TAG = "InsertStudentAct";

    private EditText et_first_name;
    private EditText et_last_name;
    private EditText et_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_insert = findViewById(R.id.fab_insert);
        fab_insert.setOnClickListener(this::onClickInsert);
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_age = findViewById(R.id.et_age);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void onClickInsert(View v) {
        String firstName = et_first_name.getText().toString().trim();
        String lastName = et_last_name.getText().toString().trim();
        String age = et_age.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            Snackbar.make(et_age, "firstName is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Snackbar.make(et_age, "lastName is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(age)) {
            Snackbar.make(et_age, "age is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        int ageInt;

        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Snackbar.make(et_age, "age is not a int", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setAge(ageInt);

        Callable<List<Long>> callable = () -> App.getDb().studentDao().insertAll(s);
        Single.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Long>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Long> longs) {
                        Log.i(TAG, String.format("onSuccess: insert success ids = %s", Arrays.toString(longs.toArray())));
                        Snackbar.make(et_first_name, "add student success", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: insert error", e);
                        Snackbar.make(et_first_name, "add student fail", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}
