package com.example.pierre.chisterapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AjouterMatch extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private TextView Latmatch;
    private TextView Longmatch;
    EditText Equip1;
    EditText Equip2;
    ListView mListView;

    double lat;
    double lng;


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    Button btnNewMatch;

    private Marker clickedMarker;
    private MatchsDataSource datasource;


    // url to create new product
    private static String url_create_match = "localhost/android_connect/create_match.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_match);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        btnNewMatch = (Button) findViewById(R.id.ajoutmatch);

        mapFragment.getMapAsync(this);


        Latmatch = (TextView) findViewById(R.id.lat);
        Longmatch = (TextView) findViewById(R.id.longi);
        Equip1 = findViewById(R.id.equip1);
        Equip2 = findViewById(R.id.equip2);

        // button click event
        btnNewMatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewMatch().execute();
            }
        });

        datasource = new MatchsDataSource(this);

        datasource.open();




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapClickListener(this);

    }


    @Override
    public void onMapClick(LatLng latLng) {
        if (latLng != null) {

            if (clickedMarker != null) {
                clickedMarker.remove();
            }

            clickedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marqueur cliqué").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            lat = (double) (latLng.latitude);
            lng = (double) (latLng.longitude);

            Latmatch.setText("Latitude du match " + String.valueOf(lat));
            Longmatch.setText("Longitude du match " + String.valueOf(lng));
            Latmatch.setText("Latitude du match : "+ String.valueOf(lat));
            Longmatch.setText("Longitude du match : "+ String.valueOf(lng));


        }
    }

    public void ajoutMatch(View view) {
        //@SuppressWarnings("unchecked")
        //ArrayAdapter<Match> adapter = (ArrayAdapter<Match>) mListView.getAdapter();
        Match match = null;


                match = datasource.createMatch(Equip1.getText().toString(),Equip2.getText().toString(), Longmatch.getText().toString(), Latmatch.getText().toString());
               // adapter.add(match);
    }

    /**
     * Background Async Task to Create new match
     * */
    class CreateNewMatch extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            System.out.println("1");
            super.onPreExecute();
            pDialog = new ProgressDialog(AjouterMatch.this);
            pDialog.setMessage("Creating Match..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating Match
         * */
        protected String doInBackground(String... args) {
            System.out.println("2");
            String equip1 = Equip1.getText().toString();
            String equip2 = Equip2.getText().toString();
            String lat = Latmatch.getText().toString();
            String longi = Longmatch.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("equipe1", equip1));
            params.add(new BasicNameValuePair("equipe2", equip2));
            params.add(new BasicNameValuePair("latitude ", lat));
            params.add(new BasicNameValuePair("longitude ", longi));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_match,"POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    //startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

       // adapter.notifyDataSetChanged();
        //finish();
    }

    public void myClickHandler(View view) throws ExecutionException, InterruptedException {

        if (view.getId() == R.id.ajoutmatch) {

            ajoutMatch(view);
        }
    }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String url_create_match)
        {
            System.out.println("3");
            // dismiss the dialog once done
            pDialog.dismiss();
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




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
