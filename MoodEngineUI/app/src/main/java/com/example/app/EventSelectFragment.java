package com.example.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Steven on 24/07/14.
 */
public class EventSelectFragment extends Fragment {

    public EventSelectFragment() {}

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mood_select, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.mood_select_gridview);
        gridView.setAdapter(new EventAdapter(getActivity()) );

        ((MainActivity)getActivity()).setActionBarTitle("Select Event");
        return rootView;
    }
}
