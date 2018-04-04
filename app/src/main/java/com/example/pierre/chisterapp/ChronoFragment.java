package com.example.pierre.chisterapp;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;


public class ChronoFragment  extends Fragment implements View.OnClickListener {
    private static Bundle args;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button toggleButton;
    Button resetButton;
    Chronometer chronometer;
    long timeWhenStopped = 0;
    boolean currentlyTiming = false;



    public static ChronoFragment newInstance(String param1, String param2) {
       ChronoFragment fragment = new ChronoFragment();
        args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chrono, container, false);

        toggleButton = (Button) v.findViewById(R.id.buttonToggleChronometer);
        resetButton = (Button) v.findViewById(R.id.buttonReset);
        chronometer = (Chronometer) v.findViewById(R.id.mainChronometer);




        toggleButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);


        if (savedInstanceState != null) {
            chronometer.setBase(savedInstanceState.getLong("time"));
            if (savedInstanceState.getBoolean("isTiming")) {
                chronometer.start();
                currentlyTiming = savedInstanceState.getBoolean("isTiming");
            }
        }

        return v;
    }

    public void toggleChronometer(){
        if(!currentlyTiming){
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
            toggleButton.setText("Stop");
            currentlyTiming = true;
        } else {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            toggleButton.setText("Start");
            currentlyTiming = false;
        }
    }
    public void resetChronometer(){
        timeWhenStopped = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonToggleChronometer:
                toggleChronometer();
                break;
            case R.id.buttonReset:
                resetChronometer();
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save myVar's value in saveInstanceState bundle
        outState.putLong("time", chronometer.getBase());
        outState.putBoolean("isTiming", currentlyTiming);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // savedInstanceState is the bundle in which we stored myVar in onSaveInstanceState() method above
        // savedInstanceState == null means that activity is being created a first time
        if (savedInstanceState != null) {
            chronometer.setBase(savedInstanceState.getLong("time"));
            if (savedInstanceState.getBoolean("isTiming")) {
                chronometer.start();
                currentlyTiming = savedInstanceState.getBoolean("isTiming");
            }
        }
    }



}