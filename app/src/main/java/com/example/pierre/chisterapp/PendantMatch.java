package com.example.pierre.chisterapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class PendantMatch extends AppCompatActivity implements View.OnClickListener {

    TextView equipe1;
    TextView equipe2;
    TextView score1;
    int faute1;
    TextView score2;
    int faute2;
    long idmatch;
    String team1;
    String team2;

    Button bfaute1;
    Button bfaute2;
    Button bpause;
    Button bservice;
    Button bannulfaute;
    Button bfinmatch;
    RadioButton baff1;
    RadioButton baff2;

    int ajoutscore1 = 0;
    int ajoutscore2 = 0;
    int ajoutfaute1 = 0;
    int ajoutfaute2 = 0;
    int fauteservice = 0;

    Boolean service = false;
    Boolean pause = true;
    Boolean dernierefaute;

    Chronometer simpleChronometer;


    private MatchsDataSource datasource;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendant_match);

        score1 = findViewById(R.id.affscore1);
        score2 = findViewById(R.id.affscore2);
        equipe1 = findViewById(R.id.affequipe1);
        equipe2 = findViewById(R.id.affequipe2);
        bfaute1 = findViewById(R.id.faute1);
        bfaute2 = findViewById(R.id.faute2);
        bpause = findViewById(R.id.pause);
        bservice = findViewById(R.id.affservice);
        bannulfaute = findViewById(R.id.annulfaute);
        bfinmatch = findViewById(R.id.finmatch);
        baff1 = findViewById(R.id.aff1);
        baff2 = findViewById(R.id.aff2);

        bfaute1.setOnClickListener(this);
        bfaute2.setOnClickListener(this);
        bpause.setOnClickListener(this);
        bservice.setOnClickListener(this);
        bannulfaute.setOnClickListener(this);
        bfinmatch.setOnClickListener(this);
        baff1.setOnClickListener(this);
        baff2.setOnClickListener(this);

        baff1.setChecked(true);

        idmatch = getIntent().getExtras().getLong("idmatch");
        team1 = getIntent().getExtras().getString("equipe1");
        team2 = getIntent().getExtras().getString("equipe2");

        equipe1.setText(team1);
        equipe2.setText(team2);

        datasource = new MatchsDataSource(this);

        datasource.open();

        simpleChronometer = findViewById(R.id.timer); // initiate a chronometer
        simpleChronometer.setFormat("Temps écoulé - %s"); // set the format for a chronometer

        Thread t=new Thread(){


            @Override
            public void run(){

                while(!isInterrupted()){

                    try {
                        Thread.sleep(1000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                findViewById(R.id.timer);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();

    }

    public void setFaute1() {

        ajoutfaute1 = ajoutfaute1 + 1;
        dernierefaute = true;
        faute1 = ajoutfaute1;
        service = true;
        baff1.setChecked(false);
        baff2.setChecked(true);
    }

    public void setFaute2() {
        ajoutfaute2 = ajoutfaute2 + 1;
        dernierefaute = false;
        faute2 = ajoutfaute2;
        service = false;
        baff1.setChecked(true);
        baff2.setChecked(false);
    }

    public void setScore1() {
        ajoutscore1 = ajoutscore1 + 1;
        score1.setText(String.valueOf(ajoutscore1));
    }

    public void setScore2() {
        ajoutscore2 = ajoutscore2 + 1;
        score2.setText(String.valueOf(ajoutscore2));
    }

    public void annulerfaute1() {

        ajoutscore2 = ajoutscore2 - 1;
        ajoutfaute1 = ajoutfaute1 - 1;
        score2.setText(String.valueOf(ajoutscore2));
        //return ajoutscore2;
    }

    public void annulerfaute2() {

        ajoutscore1 = ajoutscore1 - 1;
        ajoutfaute2 = ajoutfaute2 - 1;
        score1.setText(String.valueOf(ajoutscore1));
        //return ajoutscore1;
    }

    public void changerservice1()
    {
            baff1.setChecked(true);
            baff2.setChecked(false);
            service = false;
    }
    public void changerservice2()
    {
        baff1.setChecked(false);
        baff2.setChecked(true);
        service = true;
    }

    public void terminermatch() {

        datasource.update(idmatch, score1.getText().toString(), String.valueOf(faute1), score2.getText().toString(), String.valueOf(faute2));
        finish();

    }

    public void myClickHandler(View view) throws ExecutionException, InterruptedException {
        System.out.println("listener");

        if (view.getId() == R.id.faute1) {
            System.out.println("faute1");
            setFaute1();
            setScore2();
        }

        if (view.getId() == R.id.faute2) {
            System.out.println("faute2");
            setFaute2();
            setScore1();
        }

        if (view.getId() == R.id.pause) {

            if (pause) {
                simpleChronometer.start();
                pause = false;
            }
            if (!pause) {
                simpleChronometer.stop();
                pause = true;
            }

        }
        if (view.getId() == R.id.affservice) {

                fauteservice += 1;

            if (fauteservice == 2)
            {

                if (baff1.isChecked() && !service)
                {
                    setFaute1();
                    setScore2();
                    fauteservice = 0;
                }else if (baff2.isChecked() && service)
                {
                    setFaute2();
                    setScore1();
                    fauteservice = 0;
                }

            }

        }

        if (view.getId() == R.id.annulfaute) {

            if (dernierefaute) {
                annulerfaute1();

            }
            if (!dernierefaute) {
                annulerfaute2();
            }

        }
        if (view.getId() == R.id.aff1) {

            changerservice1();
        }
        if (view.getId() == R.id.aff2) {

            changerservice2();
        }

        if (view.getId() == R.id.finmatch) {

            terminermatch();
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

    @Override
    public void onClick(View view) {
        try {
            myClickHandler(view);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
