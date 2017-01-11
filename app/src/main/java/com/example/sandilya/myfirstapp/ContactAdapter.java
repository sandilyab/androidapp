package com.example.sandilya.myfirstapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sandilya.myfirstapp.db.TaskContract;
import com.example.sandilya.myfirstapp.db.TaskDbHelper;
import com.example.sandilya.myfirstapp.touch_util.ItemTouchHelperAdapter;
import com.example.sandilya.myfirstapp.touch_util.ItemTouchHelperViewHolder;
import com.example.sandilya.myfirstapp.touch_util.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sandilya on 1/8/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements ItemTouchHelperAdapter {

    ArrayList<String> contactList = new ArrayList<>();
    //private final OnStartDragListener mDragStartListener;
    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    //mDragStartListener = dragLlistener;
    public ContactAdapter() {

    }
    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, String data) {
        contactList.add(position, data);
        notifyItemInserted(position);
    }
    public void removeAt(int position) {
        contactList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contactList.size());
    }
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d("S","FROM IS "+fromPosition);
        Log.d("S", "TO IS "+ toPosition);
        Log.d("S",contactList.size()+"conatct sizes");

        Collections.swap(contactList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
       //contactList.remove(position);
        //notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        String ci = contactList.get(i).toString();
        String[] parts = ci.split("-_");
        contactViewHolder.vName.setText(parts[0]);
        contactViewHolder.vDate.setText(parts[1]);
        final Button button = contactViewHolder.dButton;
        button.setText("DONE");
        final int position = i;
        final String task = parts[0];

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removeAt(position);
                Log.d("YOLO", "HEREEEEEEEE");
                TaskDbHelper mHelper = new TaskDbHelper(button.getContext());
                SQLiteDatabase db = mHelper.getWritableDatabase();
                db.delete(TaskContract.TaskEntry.TABLE,
                        TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                        new String[]{task});
                db.close();
                removeAt(position);
            }
        });

        //contactViewHolder.mRootView.setOnTouchListener(new View.OnTouchListener() {

        //});

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.task_list, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        protected TextView vName;
        protected TextView vDate;
        public Button dButton;
        public ViewGroup container;
        protected View mRootView;


        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vDate =  (TextView) v.findViewById(R.id.title);
            CardView vCard = (CardView) v.findViewById(R.id.card_view);
            dButton = (Button) v.findViewById(R.id.task_delete);
            mRootView = v;

        }


        @Override
        public void onItemSelected() {
           // v.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0);
            //int currentPosition = getLayoutPosition() + 1;
        }
    }



}