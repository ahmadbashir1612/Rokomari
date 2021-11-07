package com.project.rokomari;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rokomari.Database.DatabaseHelper;
import com.project.rokomari.adapter.StatusTypeArrayAdapter;
import com.project.rokomari.model.Status;
import com.project.rokomari.model.Task;
import com.project.rokomari.ui.PhoneEmailUrlSaveFragmentDialog;
import com.project.rokomari.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskAddEditActivity extends AppCompatActivity implements PhoneEmailUrlSaveFragmentDialog.OnSaveListener {

    @BindView(R.id.task_name_edit_text)
    EditText etTaskName;

    @BindView(R.id.task_desc_edit_text)
    EditText etTaskDesc;

    @BindView(R.id.spModStatusType)
    Spinner spModStatusType;

    @BindView(R.id.deadline_date_text_view)
    TextView tvDeadline;

    @BindView(R.id.deadline_cal_image_view)
    ImageView ivCalender;

    @BindView(R.id.email_layout)
    LinearLayoutCompat emailLayout;
    @BindView(R.id.phone_layout)
    LinearLayoutCompat phoneLayout;
    @BindView(R.id.url_layout)
    LinearLayoutCompat urlLayout;


    List<Status> statusList = new ArrayList<>();
    private Status selectedStatus;
    private Date selectedDate = new Date();
    SimpleDateFormat EEEddMMMyyyyFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT_1, Locale.US);
    String formattedDate;
    int LAUNCH_SECOND_ACTIVITY = 1;
    String urlText = "", phoneText = "", emailText = "";
    private DatabaseHelper db;
    private Task savedTask;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {

            selectedDate = (Date) data.getSerializableExtra("SelectDeadline");
            formattedDate = EEEddMMMyyyyFormat.format(selectedDate);
            tvDeadline.setText(formattedDate);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_edit);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        statusList.add(new Status("Open", 0));
        statusList.add(new Status("In-progress", 1));
        statusList.add(new Status("Test", 2));
        statusList.add(new Status("Done", 3));
        setFingerTypeSpinnerAdapter();

        formattedDate = EEEddMMMyyyyFormat.format(selectedDate);
        tvDeadline.setText(formattedDate);

        savedTask = (Task) getIntent().getSerializableExtra("TASK");
        if (savedTask != null) {
            etTaskName.setText(savedTask.getTaskName());
            etTaskDesc.setText(savedTask.getDescription());
            tvDeadline.setText(savedTask.getDeadline());
            try {
                selectedDate = EEEddMMMyyyyFormat.parse(savedTask.getDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spModStatusType.setSelection(savedTask.getStatus());
            emailText = savedTask.getEmail();
            phoneText = savedTask.getPhone();
            urlText = savedTask.getUrl();

            getSupportActionBar().setTitle("Edit Tasks");

        }
    }

    @OnClick({R.id.email_layout, R.id.url_layout, R.id.phone_layout, R.id.deadline_cal_image_view
            , R.id.btnSubmit})

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.email_layout:
                PhoneEmailUrlSaveFragmentDialog emailSaveFragmentDialog = PhoneEmailUrlSaveFragmentDialog
                        .newInstance("", emailText, "email");
                emailSaveFragmentDialog.show(getSupportFragmentManager(), "dialog_fragment_url_email_phone");
                break;

            case R.id.url_layout:
                PhoneEmailUrlSaveFragmentDialog urlSaveFragmentDialog = PhoneEmailUrlSaveFragmentDialog
                        .newInstance("", urlText, "url");

                urlSaveFragmentDialog.show(getSupportFragmentManager(), "dialog_fragment_url_email_phone");
                break;

            case R.id.phone_layout:
                PhoneEmailUrlSaveFragmentDialog phoneSaveFragmentDialog = PhoneEmailUrlSaveFragmentDialog
                        .newInstance("", phoneText, "phone");
                phoneSaveFragmentDialog.show(getSupportFragmentManager(), "dialog_fragment_url_email_phone");
                break;

            case R.id.deadline_cal_image_view:

                Intent i = new Intent(this, CalenderActivity.class).putExtra("SelectDeadline", selectedDate);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                break;


            case R.id.btnSubmit:
                validateFields();
                break;

        }
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


    private void setFingerTypeSpinnerAdapter() {

        StatusTypeArrayAdapter adapter = new StatusTypeArrayAdapter(this,
                R.layout.listitems_custom_spineer_layout, R.id.tvItemName, statusList);
        spModStatusType.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spModStatusType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {

                selectedStatus = statusList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void didSave(String option, String text) {
        if (option.equals("email")) {
            emailText = text;
        } else if (option.equals("phone")) {
            phoneText = text;
        } else {
            urlText = text;
        }
    }

    public void validateFields() {
        if (etTaskName.getText().toString().equals("")) {
            Toast.makeText(this, "Enter task name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etTaskDesc.getText().toString().equals("")) {
            Toast.makeText(this, "Enter task desc", Toast.LENGTH_SHORT).show();
            return;
        }

        if (savedTask == null) {
            Task task = new Task();
            task.setTaskName(etTaskName.getText().toString());
            task.setDescription(etTaskDesc.getText().toString());
            task.setCreatedDate(EEEddMMMyyyyFormat.format(new Date()));
            task.setDeadline(tvDeadline.getText().toString());
            task.setStatus(selectedStatus.statusTypeValue);
            task.setEmail(emailText);
            task.setPhone(phoneText);
            task.setUrl(urlText);

            db.insertTask(task);
        } else {
            savedTask.setTaskName(etTaskName.getText().toString());
            savedTask.setDescription(etTaskDesc.getText().toString());
            savedTask.setDeadline(tvDeadline.getText().toString());
            savedTask.setStatus(selectedStatus.statusTypeValue);
            savedTask.setEmail(emailText);
            savedTask.setPhone(phoneText);
            savedTask.setUrl(urlText);
            db.updateTask(savedTask);
        }
        showAlertDialog(R.layout.dialog_positive_layout);
    }

    private void showAlertDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialog);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.setCancelable(false);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
    }
}