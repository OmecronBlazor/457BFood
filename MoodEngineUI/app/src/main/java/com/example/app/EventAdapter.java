package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import algorithm.EventElement;
import algorithm.Food;

/**
 * Created by Steven on 2015-02-10.
 */
public class EventAdapter extends BaseAdapter {
    private Context mContext;
    private List<EventElement> mEvents;

    public EventAdapter(Context c) {
        mContext = c;
        mEvents = MainActivity.dbhandler.getAllEvents();
    }

    public int getCount() {
        return mEvents.size();
    }

    public EventElement getItem(int pos) {
        return mEvents.get(pos);
    }

    public long getItemId(int pos) {
        return mEvents.get(pos).id();
    }

    public View getView(final int pos, View convertView, ViewGroup parent) {
        final Button button;
        if (convertView == null) {
            button = new Button(mContext);
            button.setLayoutParams(new GridView.LayoutParams((((GridView)parent).getColumnWidth()), ((GridView)parent).getColumnWidth()));
        } else {
            button = (Button) convertView;
        }

        EventElement event = mEvents.get(pos);

        Display dd = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dd.getMetrics(dm);
        float m_ScaledDensity = dm.scaledDensity;
        button.setText(event.event_name());
        button.setSingleLine();
        button.setTextSize((((GridView) parent).getColumnWidth() / 7) / m_ScaledDensity);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor(event.event_colour()));
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setAlpha(0.8f);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        button.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ArrayList<Food> list = MainActivity.dbhandler.getRecommendation(mEvents.get(pos).event_name());

                    if (!list.isEmpty()) {
                        try {
                            Fragment fragment = new FoodRecommendationFragment(list, mEvents.get(pos));
                            ((MainActivity) view.getContext()).switchToFragment(fragment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        });

        return button;
    }
}
