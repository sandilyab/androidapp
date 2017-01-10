package com.example.sandilya.myfirstapp.touch_util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Sandilya on 1/10/2017.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}