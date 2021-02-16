package com.example.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

        ctitle = findViewById(R.id.create_title);
        ccontent = findViewById(R.id.create_content);
        edittext = findViewById(R.id.create_date);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        };

        edittext.setOnClickListener(v -> {
            new DatePickerDialog(CreateActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            timePicker();


        });

    }

    private void timePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
        };
        new TimePickerDialog(CreateActivity.this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
    }

    private void updateLabel() {
        String myFormat = "HH:mm, dd/MM/yy"; //In which you need put here
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}