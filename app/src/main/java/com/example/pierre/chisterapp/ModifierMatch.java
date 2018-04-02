package com.example.pierre.chisterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        id1= findViewById(R.id.idmatch1);
        id2= findViewById(R.id.idmatch2);


        datasource = new MatchsDataSource(this);

        datasource.open();


    }

    public void modifier() {


        if (id1.getText().toString().trim().length() > 0) {


            long longid = Long.parseLong(id1.getText().toString());


            datasource.update(longid, score1.getText().toString(), faute1.getText().toString(), score2.getText().toString(), faute2.getText().toString());
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
