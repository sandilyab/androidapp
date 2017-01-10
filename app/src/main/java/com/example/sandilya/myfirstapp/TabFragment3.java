package com.example.sandilya.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandilya.myfirstapp.db.TaskContract;
import com.example.sandilya.myfirstapp.db.TaskDbHelper;
import com.example.sandilya.myfirstapp.touch_util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private RecyclerView rview;
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

        mHelper = new TaskDbHelper(getContext());
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContactAdapter ca  = updateUI(v);

        ////RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.cardList);
        //recyclerView.setAdapter(ca);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(ca);
        //mItemTouchHelper = new ItemTouchHelper(callback);
       // mItemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        View v = getView();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        ContactAdapter ca  = updateUI(v);
        recyclerView.setAdapter(ca);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(ca);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private ContactAdapter updateUI(View v) {

        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
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

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ContactAdapter ca = new ContactAdapter(taskList);
        recList.setAdapter(ca);

        return ca;
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

}

