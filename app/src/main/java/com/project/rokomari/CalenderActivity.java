package com.project.rokomari;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalenderActivity extends AppCompatActivity implements CalendarPickerView.OnDateSelectedListener {

    @BindView(R.id.calView)
    CalendarView calendarView;
    CalendarPickerView calendar;
    Calendar nextYear;
    Date today;
    private Date selectedDate;

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedDate = (Date) getIntent().getSerializableExtra("SelectDeadline");
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        today = new Date();

        calendar.init(today, nextYear.getTime())
                .withSelectedDate(selectedDate);

        calendar.setOnDateSelectedListener(this);
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

    @Override
    public void onDateSelected(final Date date) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("SelectDeadline", date);
        setResult(1, returnIntent);
        finish();

    }

    @Override
    public void onDateUnselected(final Date date) {
        //   Toast.makeText(getApplicationContext(), "Date:" + date, Toast.LENGTH_LONG).show();
    }

}
