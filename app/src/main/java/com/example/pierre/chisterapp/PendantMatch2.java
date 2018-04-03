package com.example.pierre.chisterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PendantMatch2 extends AppCompatActivity {

    private ChronoFragment chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendant_match2);
        chrono = ChronoFragment.newInstance("first fragment", "you");
        getSupportFragmentManager().beginTransaction().add(R.id.chrono,
                chrono).commit();
    }
}
