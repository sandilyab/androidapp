package com.example.sandilya.myfirstapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String hello;
    Integer duration = 300;
    private static TextView tv;
    static Dialog d ;

    public static TextView SelectedDateView;

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String s = ((month + 1) + "," + day + "," + year);
            Activity activity = getActivity();
            Intent myIntent = new Intent(activity,WorkWeek.class);
            myIntent.putExtra("Selected Date", s);
            startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settime();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onButtonTap(View v) {

            if (v.getId() == R.id.tap_buton) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Ouch!", Toast.LENGTH_LONG);
                myToast.show();
                settime();
            }

            else if ( v.getId() == R.id.perc_button) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Ouch!", Toast.LENGTH_LONG);
                myToast.show();
                Intent myIntent = new Intent(MainActivity.this,
                        Main2Activity.class);
                startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }

            else if (v.getId() == R.id.ww_button) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }

            else if ( v.getId() == R.id.d_button){

                show();

            }
        // Now we display formattedDate value in TextView
        //TextView txtView = new TextView(this);
        //setContentView(txtView);
    }


    public void show()
    {
       final Dialog d = new Dialog(MainActivity.this);

        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(53);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final TextView tv = (TextView) findViewById(R.id.txtView1);
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                Calendar cal_rev = Calendar.getInstance();
                cal_rev.clear();
                cal_rev.set(Calendar.WEEK_OF_YEAR, np.getValue());
                cal_rev.set(Calendar.YEAR, year);

                //Get first day of week
                Date date_rev = cal_rev.getTime();
                String strDate = DateFormat.getDateInstance().format(date_rev);
                tv.setText("WW "+String.valueOf(np.getValue()) +" is "+ strDate);
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        d.show();

    }

    public void settime (){

        final TextView txtView = (TextView) findViewById(R.id.txtView);
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

        Calendar cal_orig = Calendar.getInstance();
        int week_orig = cal_orig.get(Calendar.WEEK_OF_YEAR);

        txtView.setText(currentDateTimeString + " WW: " + week_orig);
        txtView.setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
