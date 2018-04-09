package com.example.pierre.chisterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AffBDDExt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aff_bddext);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ConnectionServer cs = new ConnectionServer("aff_match.php");
                    try {
                        String truc = cs.Aff_Match();

                        if(truc.equals("error")){
                            System.out.println("Error");
                        }else{
                            JSONArray jsonArray = new JSONArray(truc);
                            String toDisplay = "";
                            for(int i=0; i< jsonArray.length(); i++)
                            {
                                JSONObject object = new JSONObject(jsonArray.get(i).toString());
                                String equipe1 = object.getString("equipe1");
                                String equipe2 = object.getString("equipe2");
                                String score1 = object.getString("score1");
                                String fautes1 = object.getString("fautes1");
                                String score2 = object.getString("score2");
                                String fautes2 = object.getString("fautes2");


                                toDisplay += equipe1+" "+equipe2+" "+score1+" "+fautes1+" "+score2+" "+fautes2;
                            }
                            TextView tv = (TextView)findViewById(R.id.TextView);
                            tv.setText(toDisplay);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }

}
