package com.example.pierre.chisterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.SurfaceHolder;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MatchsDataSource datasource;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mListView = (ListView) findViewById(R.id.listView);

        datasource = new MatchsDataSource(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, CameraActivity.class);

            startActivity(intent);
            return true;
        }
        if (id == R.id.ajoutmatch) {

            Intent intent = new Intent( this, AjouterMatch.class);

            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, GalleryActivity.class);

            startActivity(intent);
            return true;

        } else if (id == R.id.nav_manage) {}




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void supprimermatch() {

        ArrayAdapter<Match> adapter = (ArrayAdapter<Match>) mListView.getAdapter();
        Match match = null;

        if (mListView.getAdapter().getCount() > 0) {
            match = (Match) mListView.getAdapter().getItem(mListView.getAdapter().getCount()-1);
            datasource.deleteMatch(match);
            adapter.remove(match);
        }

        else
        {
            Toast toast = Toast.makeText(this, "Aucun match à supprimer",  Toast.LENGTH_LONG);
            toast.show();

        }



    }

    public void myClickHandler(View view) throws ExecutionException, InterruptedException {

        if (view.getId()==R.id.delete) {

            supprimermatch();




        }

    }


    @Override
    protected void onResume() {

        datasource.open();

        List<Match> values = datasource.getAllMatchs();

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<Match> adapter = new ArrayAdapter<Match>(this,
                android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(adapter);

        if (mListView.getAdapter().getCount() == 0) {

            Toast toast = Toast.makeText(this, "Aucun match enregistré, aller dans 'Ajouter un match'pour enregistrer un match",  Toast.LENGTH_LONG);
            toast.show();

        }

        super.onResume();


    }

    @Override
    protected void onPause() {

        datasource.close();
        super.onPause();



    }




}
