package com.example.sandilya.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private ArrayList contactList;
    //private final OnStartDragListener mDragStartListener;
    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();


    public ContactAdapter(ArrayList contactList) {
        this.contactList = contactList;

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d("S","FROM IS "+fromPosition);
        Log.d("S", "TO IS "+ toPosition);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(contactList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(contactList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
       contactList.remove(position);
        notifyItemRemoved(position);
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

        contactViewHolder.mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                Log.d("S","CLICKED");
                //final int adapterPosition = ContactViewHolder.this.getAdapterPosition();
                //FilmObject film = mFilms.get(adapterPosition);
                //mDragStartListener.onStartDrag(contactViewHolder);
                contactViewHolder.getAdapterPosition();
                //int itemPosition = recyclerView.getChildLayoutPosition(v);
                return false;
            }
        });

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
        public ViewGroup container;
        protected View mRootView;


        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vDate =  (TextView) v.findViewById(R.id.title);
            CardView vCard = (CardView) v.findViewById(R.id.card_view);
            mRootView = v;

        }


        @Override
        public void onItemSelected() {
           // v.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0);
        }
    }

}