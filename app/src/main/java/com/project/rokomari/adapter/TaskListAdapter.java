package com.project.rokomari.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.rokomari.Database.DatabaseHelper;
import com.project.rokomari.R;
import com.project.rokomari.TaskAddEditActivity;
import com.project.rokomari.TaskDetailActivity;
import com.project.rokomari.model.Task;
import com.project.rokomari.ui.Open.OpenViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {


    private final RefreshTaskListener refreshTaskListener;
    private final Context context;
    private DatabaseHelper db;
    private List<Task> taskList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTaskName, tvCreatedDate, tvDeadline;
        public ImageView editImageView, deleteImageView;

        public MyViewHolder(View view) {
            super(view);

            tvTaskName = view.findViewById(R.id.task_name_text_view);
            tvCreatedDate = view.findViewById(R.id.created_date_text_view);
            tvDeadline = view.findViewById(R.id.deadline_text_view);

            editImageView = view.findViewById(R.id.edit_task_image_view);
            deleteImageView = view.findViewById(R.id.delete_task_image_view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i=new Intent(context, TaskDetailActivity.class);
                    i.putExtra("TASK",taskList.get(getAdapterPosition()));
                    context.startActivity(i);

                }

            });

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, TaskAddEditActivity.class);
                    i.putExtra("TASK",taskList.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteTask(taskList.get(getAdapterPosition()));
                    refreshTaskListener.didRefreshTask();
                }
            });

        }

    }


    public TaskListAdapter(Context context, RefreshTaskListener refreshTaskListener) {
        this.context=context;
        db = new DatabaseHelper(context);
        this.refreshTaskListener=refreshTaskListener;

    }

    public void setItem(List<Task> tasks) {
        taskList.clear();
        taskList.addAll(tasks);
        notifyDataSetChanged();
    }

    @Override
    public TaskListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);

        return new TaskListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.MyViewHolder holder, final int position) {
        Task task = taskList.get(position);
//
        holder.tvTaskName.setText(task.getTaskName());
        holder.tvCreatedDate.setText(task.getCreatedDate());
        holder.tvDeadline.setText(task.getDeadline());

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface RefreshTaskListener {

        void didRefreshTask();

    }
}
