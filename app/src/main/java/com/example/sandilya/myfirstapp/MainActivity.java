package com.example.sandilya.myfirstapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandilya.myfirstapp.db.TaskContract;
import com.example.sandilya.myfirstapp.db.TaskDbHelper;
import com.example.sandilya.myfirstapp.touch_util.OnStartDragListener;
import com.example.sandilya.myfirstapp.touch_util.SimpleItemTouchHelperCallback;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String hello;
    Integer duration = 300;
    private static TextView tv;
    static Dialog d ;
    final Context context = this;
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;


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

        //mHelper = new TaskDbHelper(this);
        //mTaskListView = (ListView) findViewById(R.id.list_todo);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.hide();
        //SetTime();
       // SetWeather();
        mHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //context.deleteDatabase("com.sandilya.todolist.db");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Utilities"));
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Tasks").setTag("tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        int item = viewPager.getCurrentItem();
        Fragment frag = (Fragment) adapter.getItem(2);
        View container = frag.getView();


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                switch (position) {
                    case 0:
                       // fab.hide();
                        break;
                    case 2:
                        //fab.show();
                        break;
                    case 1:

                       // fab.hide();
                        break;
                    default:
                       // fab.hide();
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        String provider = lm.getBestProvider(criteria, true);

        //Location location = locationManager.getLastKnownLocation(provider);
        //LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);

        Location location = lm.getLastKnownLocation(provider);
        Log.d("S", "Entering");
        if(location != null) {
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            Log.d("S","shoudl");
            Log.d("c", lat.toString());
        }

    }

    public void onClickHandler(View v) {
        Log.d("test", "onclickk here" );
        //Intent intent = new Intent(RVAdapter.this,CustomerGetQueueActivity.class);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setCurrentItem(2);
    }

    public void onButtonTap(View v) {

            if ( v.getId() == R.id.perc_button) {
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

        // Now we display formattedDate value in TextView
        //TextView txtView = new TextView(this);
        //setContentView(txtView);
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
    void DispDialog (String s, String ww) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Work Week "+ww );

        // set dialog message
        alertDialogBuilder
                .setMessage(s)
                .setCancelable(false)
                .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void updateUI() {
        RecyclerView recList = (RecyclerView)findViewById(R.id.cardList);
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DATE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idy = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            String s = cursor.getString(idx)+"-_"+cursor.getString(idy);
            taskList.add(s);
        }
        cursor.close();
        db.close();
        RecyclerView rview = (RecyclerView) findViewById(R.id.cardList);
        ContactAdapter ca = new ContactAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rview.setLayoutManager(llm);

        rview.setAdapter(ca);
        //ca.notifyDataSetChanged();
        TabFragment3 tf= (TabFragment3) getSupportFragmentManager().findFragmentByTag("tab3");
        if ( tf != null) {
            Log.d("S", "CALLINGG");
        }

        Log.d("s", "notifiyng"+taskList.size());

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(ca);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recList);
        //ca.notifyDataSetChanged();
        //ca.notifyItemChanged(taskList.size()-1) ;




    }
    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.txtName);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        RecyclerView rview = (RecyclerView) findViewById(R.id.cardList);
        ContactAdapter ca = new ContactAdapter();
        rview.setAdapter(ca);

        //rview.getChildAdapterPosition()
        //a.removeAt();


    }

    public void UpdateToday() {
        TextView t1 = (TextView) findViewById(R.id.tasks1);
        TextView t2 = (TextView) findViewById(R.id.tasks2);
        TextView t3 = (TextView) findViewById(R.id.tasks3);
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DATE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idy = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            String s = cursor.getString(idx)+"-_"+cursor.getString(idy);
            String[] parts = s.split("-_");
            taskList.add(parts[0]);
        }

        cursor.close();
        db.close();


        Integer size = taskList.size();
        if (size >3 ) { size =3;}
        switch (size) {
            case 3:
                t1.setText(taskList.get(0));
                t2.setText(taskList.get(1));
                t3.setText(taskList.get(2));
                return;
            case 2:
                t1.setText(taskList.get(0));
                t2.setText(taskList.get(1));
                return;
            case 1:
                t1.setText(taskList.get(0));
                return;
            default:
                return;
        }
    }


}

