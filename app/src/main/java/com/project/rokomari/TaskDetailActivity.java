package com.project.rokomari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.rokomari.model.Task;
import com.project.rokomari.ui.PhoneEmailUrlSaveFragmentDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDetailActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;


    @BindView(R.id.created_date_text_view)
    TextView tvCreatedDate;
    @BindView(R.id.task_name_text_view)
    TextView tvTaskName;
    @BindView(R.id.status_text_view)
    TextView tvStatus;
    @BindView(R.id.deadline_text_view)
    TextView tvDeadline;
    @BindView(R.id.desc_text_view)
    TextView tvDesc;

    @BindView(R.id.email_layout)
    LinearLayoutCompat emailLayout;
    @BindView(R.id.phone_layout)
    LinearLayoutCompat phoneLayout;
    @BindView(R.id.url_layout)
    LinearLayoutCompat urlLayout;
    private Task savedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        savedTask = (Task) getIntent().getSerializableExtra("TASK");

        tvCreatedDate.setText(savedTask.getCreatedDate());
        tvDeadline.setText(savedTask.getDeadline());
        if (savedTask.getStatus() == 0) {
            tvStatus.setText("Open");
        } else if (savedTask.getStatus() == 1) {
            tvStatus.setText("In-progress");
        } else if (savedTask.getStatus() == 2) {
            tvStatus.setText("Test");
        } else if (savedTask.getStatus() == 3) {
            tvStatus.setText("Done");
        }

        tvDesc.setText(savedTask.getDescription());
        tvTaskName.setText(savedTask.getTaskName());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskDetailActivity.this, TaskAddEditActivity.class);
                i.putExtra("TASK", savedTask);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @OnClick({R.id.email_layout, R.id.url_layout, R.id.phone_layout})

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.email_layout:

                break;

            case R.id.url_layout:

                break;

            case R.id.phone_layout:

                break;


        }
    }
}