package com.pace.cs639spring.hw4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kachi
 */

public class CircleAnimationAdapter extends BaseAdapter {

    private Context mContext;
    private List<Circle> mCircles; //our data is going to be a list of circles

    private int mSelectedPosition = Adapter.NO_SELECTION;

    public CircleAnimationAdapter(Context context) {
        mContext = context;
        mCircles = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mCircles.size();
    }

    @Override
    public Object getItem(int position) {
        return mCircles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item, null);
            ViewHolder viewHolder = new ViewHolder((CircleAnimationView)convertView.findViewById(R.id.circle_animation_view));
            convertView.setTag(viewHolder);
        }

        Circle circle = (Circle) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mCircleAnimationView.setCircle(circle);
        //once we've binded our circle to the CircleAnimationView, start animating the circle
        viewHolder.mCircleAnimationView.start();
        return convertView;
    }

    public void addCircle(Circle circle) {
        //adds circle to our list and tells the list to refresh itself
        mCircles.add(circle);
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition =  position ==  mSelectedPosition ? Adapter.NO_SELECTION : position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public boolean isPositionSelected() {
        return mSelectedPosition != Adapter.NO_SELECTION;
    }

    static class ViewHolder {

        CircleAnimationView mCircleAnimationView;

        ViewHolder(CircleAnimationView trackerView) {
            mCircleAnimationView = trackerView;
        }
    }
}
