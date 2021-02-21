package com.example.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {


    private EditText edittext, ctitle, ccontent;
    final Calendar myCalendar = Calendar.getInstance();
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        NotificationChannel notificationChannel = new NotificationChannel("duong", "duong", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("Descreption");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        ctitle = findViewById(R.id.create_title);
        ccontent = findViewById(R.id.create_content);
        edittext = findViewById(R.id.create_date);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        };
        TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
        };


        edittext.setOnClickListener(v -> {
            new DatePickerDialog(CreateActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            new TimePickerDialog(CreateActivity.this, time,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE), true).show();


        });

    }
   /* private void timePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
        };
        new TimePickerDialog(CreateActivity.this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
    }*/


    private void updateLabel() {
        String myFormat = "HH:mm, dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.done) {
            Work work = new Work();
            work.setTitle(ctitle.getText().toString());
            work.setDate(edittext.getText().toString());
            work.setContent(ccontent.getText().toString());
            database = new Database(CreateActivity.this);
            database.add(work);
            setResult(Activity.RESULT_OK, new Intent());
            ////
            Intent intent2 = new Intent(CreateActivity.this, AlertBrr.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateActivity.this, 0, intent2, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);


            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}