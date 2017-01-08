package com.example.sandilya.myfirstapp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    Typeface weatherFont;
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
        Integer result =SetWeather(v);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //final View v = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
                Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
                SetWeather(getView());
                if(swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Weather Updated", Toast.LENGTH_SHORT).show();
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



    int SetWeather(View v) {

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView)v.findViewById(R.id.city_field);
        detailsField = (TextView)v.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)v.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) v.findViewById(R.id.humidity);
        weatherIcon = (TextView)v.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                detailsField.setText(weather_description);
                humidity_field.setText("Humidity "+weather_humidity);
                currentTemperatureField.setText(weather_temperature);
                weatherIcon.setText(Html.fromHtml(weather_iconText,0));
            }
        });
        asyncTask.execute( "45.52", "-122.99");
        return 1;
    }

}
