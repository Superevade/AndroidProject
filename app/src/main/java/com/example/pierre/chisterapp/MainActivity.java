package com.example.pierre.chisterapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MatchsDataSource datasource;
    int REQUEST_IMAGE_CAPTURE = 1;
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

    public void startCameraActivity()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

            //String fileName = "photo_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpg";
            //File photo = new File(PICTURE_DIR, fileName);

            //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
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

        if (id == R.id.nav_camera)
        {
            startCameraActivity();
        }
        if (id == R.id.ajoutmatch)
        {

            Intent intent = new Intent(this, AjouterMatch.class);

            startActivity(intent);
            // Handle the camera action
        }
        if (id == R.id.nav_bddext)
        {
            Intent intent = new Intent(this, AffBDDExt.class);

            startActivity(intent);
        }
        if (id == R.id.nav_gallery)
        {
            Intent intent = new Intent(this, GalleryActivity.class);

             startActivity(intent);
               return true;


        }
        if (id == R.id.nav_infos)
        {

            Intent intent = new Intent(this, Infos.class);

            startActivity(intent);
            return true;


        } else if (id == R.id.nav_manage)
        {

            Intent intent = new Intent(this, ModifierMatch.class);

            startActivity(intent);
            // Handle the camera action
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            Toast toast = Toast.makeText(this, "Aucun match enregistré, aller dans 'Ajouter un match'pour enregistrer un match", Toast.LENGTH_LONG);
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
