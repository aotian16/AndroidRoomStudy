package com.qefee.androidroomstudy.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.qefee.androidroomstudy.App;
import com.qefee.androidroomstudy.R;
import com.qefee.androidroomstudy.adapter.StudentAdapter;
import com.qefee.androidroomstudy.db.entity.Student;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class StudentListActivity extends AppCompatActivity {
    /**
     * log tag for StudentListActivity
     */
    private static final String TAG = "StudentListAct";

    private List<Student> studentList;
    private StudentAdapter adapter;
    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_list = findViewById(R.id.fab_list);
        fab_list.setOnClickListener(this::onClickList);

        rv_list = findViewById(R.id.rv_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager agentLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        rv_list.setLayoutManager(agentLayoutManager);

        studentList = new ArrayList<>(10);

        adapter = new StudentAdapter(studentList, (v, position) -> Log.i(TAG, String.format("onClickItem: position = %d", position)));
        rv_list.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                final Student s = studentList.get(position);
                Action action = () -> App.getDb().studentDao().delete(s);

                Completable.fromAction(action)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, String.format("onComplete: delete student success id = %d", s.getUid()));
                                Snackbar.make(rv_list, "delete student success", Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: delete student fail", e);
                                Snackbar.make(rv_list, "delete student fail", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        itemTouchHelper.attachToRecyclerView(rv_list);
    }

    public void onClickList(View v) {
        App.getDb().studentDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Student>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Student> students) {
                        Log.i(TAG, String.format("onSuccess: students.size = %d", students.size()));
                        studentList.addAll(students);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: get all student error", e);
                        Snackbar.make(rv_list, "select student fail", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
