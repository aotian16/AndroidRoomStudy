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

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class UpdateStudentActivity extends AppCompatActivity {
    /**
     * log tag for UpdateStudentActivity
     */
    private static final String TAG = "UpdateStudentAct";

    private EditText et_first_name;
    private EditText et_last_name;
    private EditText et_age;
    private EditText et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_update = findViewById(R.id.fab_update);
        fab_update.setOnClickListener(this::onClickInsert);
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_age = findViewById(R.id.et_age);
        et_id = findViewById(R.id.et_id);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void onClickInsert(View v) {
        String firstName = et_first_name.getText().toString().trim();
        String lastName = et_last_name.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String id = et_id.getText().toString().trim();

        int ageInt;

        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Snackbar.make(et_age, "age is not a int", Snackbar.LENGTH_SHORT).show();
            return;
        }

        int idInt;

        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Snackbar.make(et_age, "id is not a int", Snackbar.LENGTH_SHORT).show();
            return;
        }

        requestStudent(firstName, lastName, age, ageInt, idInt);
    }

    private void requestStudent(String firstName, String lastName, String age, int ageInt, int idInt) {
        App.getDb().studentDao().findById(idInt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Student>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Student student) {
                        Log.i(TAG, String.format("onSuccess: find student by id = %d", idInt));

                        updateStudent(idInt, firstName, lastName, age, ageInt);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, String.format("onError: can not find student by id = %d", idInt));
                        Snackbar.make(et_id, "select student fail", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateStudent(int idInt, String firstName, String lastName, String age, int ageInt) {
        Action action = () -> {
            Student s = new Student();
            s.setUid(idInt);
            if (!TextUtils.isEmpty(firstName)) {
                s.setFirstName(firstName);
            }
            if (!TextUtils.isEmpty(lastName)) {
                s.setLastName(lastName);
            }
            if (!TextUtils.isEmpty(age)) {
                s.setAge(ageInt);
            }
            App.getDb().studentDao().update(s);
        };

        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: update student success");
                        Snackbar.make(et_id, "update student success", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: update student fail", e);
                        Snackbar.make(et_id, "update student fail", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}
