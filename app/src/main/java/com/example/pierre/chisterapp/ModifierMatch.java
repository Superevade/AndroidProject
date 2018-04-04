package com.example.pierre.chisterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ModifierMatch extends AppCompatActivity {


    EditText id1;
    EditText id2;
    EditText score1;
    EditText faute1;
    EditText score2;
    EditText faute2;

    ListView mListView;


    private MatchsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_match);

        score1 = findViewById(R.id.score1);
        faute1 = findViewById(R.id.fautes1);
        score2 = findViewById(R.id.score2);
        faute2 = findViewById(R.id.fautes2);
        id1 = findViewById(R.id.idmatch1);
        id2 = findViewById(R.id.idmatch2);


        datasource = new MatchsDataSource(this);

        datasource.open();


    }

    public void modifier() {


        if (id1.getText().toString().trim().length() > 0) {

            long longid = Long.parseLong(id1.getText().toString());
            datasource.update(longid, score1.getText().toString(), faute1.getText().toString(), score2.getText().toString(), faute2.getText().toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ConnectionServer cs = new ConnectionServer("update_match.php");
                    try {
                        int idmatch = Integer.valueOf(id1.getText().toString());
                        int Servscore1 = Integer.valueOf(score1.getText().toString());
                        int Servfaute1 = Integer.valueOf(faute1.getText().toString());
                        int Servscore2 = Integer.valueOf(score2.getText().toString());
                        int Servfaute2 = Integer.valueOf(faute2.getText().toString());
                        String truc = cs.Update_Match(idmatch, Servscore1, Servfaute1, Servscore2, Servfaute2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            finish();
        } else {
            Toast toast = Toast.makeText(this, "Veuillez donner l'id d'un match", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void supprimermatch() {

        if (id2.getText().toString().trim().length() > 0) {

            long longid = Long.parseLong(id2.getText().toString());


            datasource.deleteMatch(longid);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ConnectionServer cs = new ConnectionServer("delete_match.php");
                    try {
                        int idmatch = Integer.valueOf(id2.getText().toString());
                        String truc = cs.Delete_Match(idmatch);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            finish();

        } else {
            Toast toast = Toast.makeText(this, "Veuillez donner l'id d'un match", Toast.LENGTH_LONG);
            toast.show();

        }


    }

    public void myClickHandler(View view) throws ExecutionException, InterruptedException {

        if (view.getId() == R.id.modifiermatch) {

            modifier();
        }

        if (view.getId() == R.id.delete) {
            supprimermatch();
        }

    }

    @Override
    protected void onPause() {

        datasource.close();
        super.onPause();


    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }


}
