package com.example.sandilya.myfirstapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;


import com.example.sandilya.myfirstapp.db.TaskContract;
import com.example.sandilya.myfirstapp.db.TaskDbHelper;
import com.example.sandilya.myfirstapp.touch_util.SimpleItemTouchHelperCallback;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.tabhost;
import static com.example.sandilya.myfirstapp.R.id.icon;
import static com.example.sandilya.myfirstapp.R.id.split_action_bar;
import static com.example.sandilya.myfirstapp.R.id.tab_layout;
import static com.example.sandilya.myfirstapp.R.id.toolbar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    SwipeRefreshLayout swipeLayout;
    TextView cityField, detailsField,  humidity_field, weatherIcon, updatedOn;
    TextView currentTemperatureField1, currentTemperatureField2, currentTemperatureField3, currentTemperatureField, currentTemperatureField4;
    TextView updatedOn1, updatedOn2, updatedOn3, updatedOn4;
    TextView weatherIcon1, weatherIcon2, weatherIcon3, weatherIcon4;
    Typeface weatherFont;

    ViewPager viewPager;
    private TaskDbHelper mHelper;
    private ArrayAdapter<String> mAdapter;

    public TabFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragment2.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        SetWeather(v);
        SetWeatherForecast(v);

        mHelper = new TaskDbHelper(getContext());
        updateUI(v);

        //FUTURE CHANGES
        //window.setStatusBarColor(Color.BLUE);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        //TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        //tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //Window window = getActivity().getWindow();


        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.orange);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
               // final View v = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
                //Toast.makeText(getContext(), "Refreshing", Toast.LENGTH_SHORT).show();
                SetWeather(getView());
                updateUI(v);
                SetWeatherForecast(getView());
                if(swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Refresh Completed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    void SetWeather(final View v) {

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView)v.findViewById(R.id.city_field);
        detailsField = (TextView)v.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)v.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) v.findViewById(R.id.humidity);
        weatherIcon = (TextView)v.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        final TextView update_time = (TextView) v.findViewById(R.id.time_field);

        Function_weather.placeIdTask asyncTask =new Function_weather.placeIdTask(new Function_weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                detailsField.setText(weather_description);
                humidity_field.setText("Humidity "+weather_humidity);
                currentTemperatureField.setText(weather_temperature);
                weatherIcon.setText(Html.fromHtml(weather_iconText,0));
                Date date_rev = Calendar.getInstance().getTime();
                update_time.setText(DateFormat.getDateTimeInstance().format(date_rev));

                SetWeatherBackground (weather_iconText, v);

            }
        });
        asyncTask.execute( "45.52", "-122.99");
    }

    void SetWeatherForecast(View v) {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        currentTemperatureField1 = (TextView) v.findViewById(R.id.forecast_temperature_field1);
        weatherIcon1 = (TextView) v.findViewById(R.id.weather_forecast_icon1);
        updatedOn1 = (TextView) v.findViewById(R.id.forecast_time1);

        currentTemperatureField2 = (TextView) v.findViewById(R.id.forecast_temperature_field2);
        weatherIcon2 = (TextView) v.findViewById(R.id.weather_forecast_icon2);
        updatedOn2 = (TextView) v.findViewById(R.id.forecast_time2);

        currentTemperatureField3 = (TextView) v.findViewById(R.id.forecast_temperature_field3);
        weatherIcon3 = (TextView) v.findViewById(R.id.weather_forecast_icon3);
        updatedOn3 = (TextView) v.findViewById(R.id.forecast_time3);

        currentTemperatureField4 = (TextView) v.findViewById(R.id.forecast_temperature_field4);
        weatherIcon4 = (TextView) v.findViewById(R.id.weather_forecast_icon4);
        updatedOn4 = (TextView) v.findViewById(R.id.forecast_time4);

        weatherIcon1.setTypeface(weatherFont);
        weatherIcon2.setTypeface(weatherFont);
        weatherIcon3.setTypeface(weatherFont);
        weatherIcon4.setTypeface(weatherFont);

        Function_weather_forecast.placeIdTask asyncTask1 =new Function_weather_forecast.placeIdTask(new Function_weather_forecast.AsyncResponse() {

            public void processFinish (String[] updateOn, String[]  temperature,int[] id, int[] day_night, String[] weather_iconText) {

                Log.d("S",Arrays.toString(day_night) );
                Log.d("S",Arrays.toString(weather_iconText) );
                Log.d("S",weather_iconText[0]);

                currentTemperatureField1.setText(temperature[0]);
                weatherIcon1.setText(Html.fromHtml(weather_iconText[0],0));
                updatedOn1.setText(updateOn[0]);

                currentTemperatureField2.setText(temperature[1]);
                weatherIcon2.setText(Html.fromHtml(weather_iconText[1],0));
                updatedOn2.setText(updateOn[1]);

                currentTemperatureField3.setText(temperature[2]);
                weatherIcon3.setText(Html.fromHtml(weather_iconText[2],0));
                updatedOn3.setText(updateOn[2]);

                currentTemperatureField4.setText(temperature[3]);
                weatherIcon4.setText(Html.fromHtml(weather_iconText[3],0));
                updatedOn4.setText(updateOn[3]);
            }
        });

        asyncTask1.execute("45.52", "-122.99");

    }
    void SetWeatherBackground (String weather_iconText, View v) {
        switch (weather_iconText) {
            //Clear day; like its gonna ever happen in hillsboro!
            case "&#xf00d;":
                v.setBackgroundColor(Color.parseColor("#039be5"));
                break;
            //Clear night
            case "&#xf02e;":
                v.setBackgroundColor(Color.parseColor("#3F51B5"));
                break;
            //Thunderstorm
            case "&#xf01e;":
                v.setBackgroundColor(Color.parseColor("#212121"));
                break;
            //Light drizzle. gonna be seeing this a lot
            case "&#xf01c;":
                v.setBackgroundColor(Color.parseColor("#2d7baf"));
                break;
            //windy/foggy
            case "&#xf014;":
                v.setBackgroundColor(Color.parseColor("#bcaaa4"));
                break;
            //Cloudy
            case "&#xf013;":
                v.setBackgroundColor(Color.parseColor("#90a4ae"));
                break;
            //Snow
            case "&#xf01b;":
                v.setBackgroundColor(Color.parseColor("#c5cae9"));
                break;
            //The all time fav! RAIN bitch!
            case "&#xf019;":
                v.setBackgroundColor(Color.parseColor("#5c6bc0"));
                break;
            case "&#xf002;":
                v.setBackgroundColor(Color.parseColor("90CAF9"));
                break;
            case "&#xf086;":
                v.setBackgroundColor(Color.parseColor("#448AFF"));
                break;
        }
        //v.setBackgroundColor(Color.parseColor("#448AFF"));
    }

    public void updateUI(View v) {
        TextView t1 = (TextView) v.findViewById(R.id.tasks1);
        TextView t2 = (TextView) v.findViewById(R.id.tasks2);
        TextView t3 = (TextView) v.findViewById(R.id.tasks3);
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
