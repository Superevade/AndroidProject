package com.example.pierre.chisterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AffBDDExt extends AppCompatActivity {

    int matchid = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aff_bddext);



        for(int i = 0; i<30;i++)
        {
            matchid ++;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ConnectionServer cs = new ConnectionServer("aff_match.php");
                    try {
                        String truc = cs.Aff_Match(matchid);
                        JSONArray jsonArray = new JSONArray(truc);
                        JSONObject object = new JSONObject(jsonArray.get(0).toString());
                        int workoutID = object.getInt("id");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
