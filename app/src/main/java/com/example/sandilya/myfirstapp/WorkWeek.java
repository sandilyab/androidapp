package com.example.sandilya.myfirstapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import static android.R.id.input;

public class WorkWeek extends AppCompatActivity {

    public static TextView SelectedDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_week);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = (getIntent().getStringExtra("Selected Date"));
        String[]strArray= s.split(",");
        int month = Integer.parseInt(strArray[0]) ;
        int date = Integer.parseInt(strArray[1]);
        int year = Integer.parseInt(strArray[2]);
        Log.w("myApp",strArray[0]);
        Log.w("myApp",strArray[1]);
        Log.w("myApp",strArray[2]);
        String format = "yyMMdd";
        //SimpleDateFormat df = new SimpleDateFormat(format);
        //Date date2 = df.parse(Arrays.toString(strArray));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.YEAR, year);
        Date date2;
        date2= cal.getTime();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int year2 = cal.get(Calendar.YEAR);
        Log.w("myApp", Integer.toString(week));
        SelectedDateView = (TextView) findViewById(R.id.selected_date);
        SelectedDateView.setText(week);
        Log.w("myApp", "no network");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
