package com.example.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import algorithm.EventElement;
import algorithm.MoodElement;
import algorithm.Preference;

/**
 * Created by Steven on 24/07/14.
 */
public class AddEventFragment extends Fragment {
    private List<EventElement> mEvents;

    private TextView mAddEventTitle;
    private EditText mEventName;
    private TextView mColorTitle;
    private GridView mColourGridView;
    private SeekBar mSournessSeekBar;
    private SeekBar mSaltinessSeekBar;
    private SeekBar mSweetnessSeekBar;
    private SeekBar mBitternessSeekBar;
    private SeekBar mFattinessSeekBar;
    private Button mCreateEvent;

    private ColorSelectAdapter mColorSelectAdapter;

    public AddEventFragment() {
        mEvents = MainActivity.dbhandler.getAllEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_event_fragment, container, false);

        mAddEventTitle = (TextView) rootView.findViewById(R.id.add_event_title);
        mEventName = (EditText) rootView.findViewById(R.id.add_event_name);
        mColorTitle = (TextView) rootView.findViewById(R.id.add_event_colour_title);
        mColourGridView = (GridView) rootView.findViewById(R.id.add_event_colour_select);
        mCreateEvent = (Button) rootView.findViewById(R.id.add_event_submit_button);
        mSournessSeekBar = (SeekBar) rootView.findViewById(R.id.sourness_seekbar);
        mSaltinessSeekBar = (SeekBar) rootView.findViewById(R.id.saltiness_seekbar);
        mSweetnessSeekBar = (SeekBar) rootView.findViewById(R.id.sweetness_seekbar);
        mBitternessSeekBar = (SeekBar) rootView.findViewById(R.id.bitterness_seekbar);
        mFattinessSeekBar = (SeekBar) rootView.findViewById(R.id.fattiness_seekbar);

        mEventName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEventName.getWindowToken(), 0);
                }
            }
        });
        final String[] colors = {"#ff26a65b", "#fff64747", "#ffcf000f", "#ff1abc9c", "#fff7ca18", "#fff9690e", "#ff3a539b", "#ff3498db", "#ff8e44ad"};
        mColorSelectAdapter = new ColorSelectAdapter(getActivity(), colors);
        mColourGridView.setAdapter(mColorSelectAdapter);
        mColourGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mColorSelectAdapter.selectedIndex = i;
                mColorSelectAdapter.notifyDataSetChanged();
                mEventName.clearFocus();
            }
        });
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEventName.clearFocus();
                return false;
            }
        };
        mSournessSeekBar.setOnTouchListener(touchListener);
        mSaltinessSeekBar.setOnTouchListener(touchListener);
        mSweetnessSeekBar.setOnTouchListener(touchListener);
        mBitternessSeekBar.setOnTouchListener(touchListener);
        mFattinessSeekBar.setOnTouchListener(touchListener);

        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mColorSelectAdapter.selectedIndex != -1 && !mEventName.getText().toString().equals("")) {
                    EventElement eventToAdd = new EventElement(mEventName.getText().toString(),
                            new Preference(mSournessSeekBar.getProgress(),
                                    mSaltinessSeekBar.getProgress(),
                                    mSweetnessSeekBar.getProgress(),
                                    mBitternessSeekBar.getProgress(),
                                    mFattinessSeekBar.getProgress())
                            , colors[mColorSelectAdapter.selectedIndex], colors.length + 1);
                    eventToAdd.setID(MainActivity.dbhandler.addEvent(eventToAdd));
                    MainActivity.table.addEvent(eventToAdd);

                    EventSelectFragment fragmentToLaunch = new EventSelectFragment();
                    ((MainActivity) view.getContext()).switchToFragment(fragmentToLaunch);
                } else {
                    Toast.makeText(getActivity(), "Please fill out all of the information", Toast.LENGTH_LONG).show();
                }
            }
        });

        ((MainActivity) getActivity()).setActionBarTitle("Add Event");
        return rootView;
    }

    @Override
    public void onStop(){
        super.onStop();
        ((MainActivity) getActivity()).hideActionBarTitle();
    }
}
