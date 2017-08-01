package com.benayah.app.trackmypet.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 24-07-2017.
 */

public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {
    protected OnItemClickListener listener;

    private GestureDetector gestureDetector;

    private View childView;

    private int childViewPosition;


    public RecyclerViewClickListener(Context context, OnItemClickListener listener)
    {
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.listener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {

        childView = view.findChildViewUnder(event.getX(), event.getY());
        childViewPosition = view.getChildPosition(childView);

        return childView != null && gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnItemClickListener {


        public void onItemClick(View childView, int position);



        public void onItemLongPress(View childView, int position);

    }


    public static abstract class SimpleOnItemClickListener implements OnItemClickListener {



        public void onItemClick(View childView, int position) {

        }



        public void onItemLongPress(View childView, int position) {

        }

    }

    protected class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (childView != null) {
                listener.onItemClick(childView, childViewPosition);
            }

            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (childView != null) {
                listener.onItemLongPress(childView, childViewPosition);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {

            return true;
        }

    }
}
