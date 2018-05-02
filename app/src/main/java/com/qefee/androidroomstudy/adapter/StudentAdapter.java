package com.qefee.androidroomstudy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qefee.androidroomstudy.db.entity.Student;

import java.util.List;
import java.util.Locale;

/**
 * StudentAdapter.
 * <ul>
 * <li>date: 2018/5/2</li>
 * </ul>
 *
 * @author tongjin
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> studentList;
    private ActionCallback callback;

    public StudentAdapter(List<Student> studentList, ActionCallback callback) {
        this.studentList = studentList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int ap = holder.getAdapterPosition();
        Student s = studentList.get(ap);
        holder.text.setText(String.format(Locale.CHINA, "[%d, %s, %s, %d]", s.getUid(), s.getFirstName(), s.getLastName(), s.getAge()));
        holder.text.setOnClickListener(v -> callback.onClickItem(holder.itemView, ap));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface ActionCallback {
        void onClickItem(View v, int position);
    }
}
