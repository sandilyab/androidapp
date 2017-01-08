package com.example.sandilya.myfirstapp;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment1 extends Fragment {


   // private OnFragmentInteractionListener mListener;
    public static TabFragment1 newInstance() {
        TabFragment1 fragment = new TabFragment1();
        return fragment;
    }
    public TabFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragment1.
     */
    // TODO: Rename and change types and number of parameters
    TextView txtView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

            txtView = (TextView) v.findViewById(R.id.txtView2);
            String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

            Calendar cal_orig = Calendar.getInstance();
            int week_orig = cal_orig.get(Calendar.WEEK_OF_YEAR);

            txtView.setText(currentDateTimeString + " WW " + week_orig);
            txtView.setGravity(Gravity.CENTER);

            //Handle WW to Date dialog
            Button dc = (Button) v.findViewById(R.id.d_button);
            dc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder (getActivity());

                    final NumberPicker picker = new NumberPicker(getActivity());
                    picker.setMinValue(0);
                    picker.setMaxValue(53);

                    final FrameLayout parent = new FrameLayout(getActivity());
                    parent.addView(picker, new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            Gravity.CENTER));
                    builder.setView(parent);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        //On click for positive button
                        public void onClick(DialogInterface dialog, int id) {
                            int pickedValue = picker.getValue();
                            Calendar cal = Calendar.getInstance();
                            int year = cal.get(Calendar.YEAR);
                            Calendar cal_rev = Calendar.getInstance();
                            cal_rev.clear();
                            cal_rev.set(Calendar.WEEK_OF_YEAR, picker.getValue());
                            cal_rev.set(Calendar.YEAR, year);

                            //Get first day of week
                            Date date_rev = cal_rev.getTime();
                            final String strDate = DateFormat.getDateInstance().format(date_rev);
                            //Display selected number in new dialog
                            new AlertDialog.Builder(getActivity())
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setTitle("Work Week "+pickedValue+" Starts")
                                    .setMessage(strDate)
                                    .create()
                                    .show();
                            //Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www..com"));
                            //Bundle b = new Bundle();
                            //b.putBoolean("new_window", true); //sets new window
                            //intent.putExtras(b);
                            //startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });

        //Handle Date picker to WW Dialog





        return v;
    }
}
