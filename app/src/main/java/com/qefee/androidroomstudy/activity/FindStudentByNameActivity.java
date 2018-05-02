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

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FindStudentByNameActivity extends AppCompatActivity {
    /**
     * log tag for FindStudentByNameActivity
     */
    private static final String TAG = "FindStudentByNameAct";

    private EditText et_first_name;
    private EditText et_last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_student_by_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_find = findViewById(R.id.fab_find);
        fab_find.setOnClickListener(this::onClickInsert);
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void onClickInsert(View v) {
        String firstName = et_first_name.getText().toString().trim();
        String lastName = et_last_name.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            Snackbar.make(v, "firstName is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Snackbar.make(v, "lastName is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        selectStudentByName(firstName, lastName);
    }

    private void selectStudentByName(String firstName, String lastName) {
        App.getDb().studentDao().findByName(firstName, lastName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Student>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Student student) {
                        Log.i(TAG, String.format("onSuccess: find student id = %d", student.getUid()));
                        Snackbar.make(et_first_name, "find student success", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: find student error", e);
                        Snackbar.make(et_first_name, "find student fail", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
