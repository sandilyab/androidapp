package com.example.sandilya.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandilya.myfirstapp.db.TaskContract;
import com.example.sandilya.myfirstapp.db.TaskDbHelper;
import com.example.sandilya.myfirstapp.touch_util.SimpleItemTouchHelperCallback;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ItemTouchHelper mItemTouchHelper;
    ArrayList<String> taskList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private RecyclerView rview;
    public ContactAdapter ca;
    private ArrayAdapter<String> mAdapter;

    private OnFragmentInteractionListener mListener;

    public TabFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragment3.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);


        //ca.notifyDataSetChanged();

        //RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.cardList);
        //recyclerView.setAdapter(ca);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rview = (RecyclerView) v.findViewById(R.id.cardList);
        //rview.setHasFixedSize(true);
        ca = new ContactAdapter();
        rview.setAdapter(ca);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rview.setLayoutManager(llm);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(ca);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rview);


        updateUI(v);
        //ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(ca);
        //mItemTouchHelper = new ItemTouchHelper(callback);
       // mItemTouchHelper.attachToRecyclerView(recyclerView);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Snackbar.make(view, "To Do list coming soon", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                final EditText taskEditText = new EditText(getContext());

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                Log.d("S", "Task to add MAAA: " + task);
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                String dt = DateFormat.getDateTimeInstance().format(new Date());
                                values.put(TaskContract.TaskEntry.COL_TASK_DATE, dt);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                ca.insert(taskList.size(),task+"-_"+dt);
                                ca.notifyItemChanged(taskList.size()-1);
                                //TabFragment2 tab2 = new TabFragment2();
                                //UpdateToday();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }

        });

        return v;
    }

    public void updateUI(View v) {

        //rview = (RecyclerView) v.findViewById(R.id.cardList);
        mHelper = new TaskDbHelper(getContext());
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ArrayList<String> taskList = new ArrayList<>();
        //SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DATE},
                null, null, null, null, null);
        int i=0;
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idy = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            String s = cursor.getString(idx)+"-_"+cursor.getString(idy);
            ca.insert(i,s);
            taskList.add(s);
            i++;
        }

        cursor.close();
        db.close();
        ca.notifyItemChanged(taskList.size()-1);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        //itemAnimator.setAddDuration(1000);
        //itemAnimator.setRemoveDuration(1000);
        rview.setItemAnimator(itemAnimator);

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        updateUI(view);
    }

}

