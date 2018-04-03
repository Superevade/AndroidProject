package com.example.pierre.chisterapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AjouterMatch extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private TextView Latmatch;
    private TextView Longmatch;
    private TextView adress;
    EditText Equip1;
    EditText Equip2;
    ListView mListView;

    double lat;
    double lng;


    private Marker clickedMarker;
    private MatchsDataSource datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_match);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        Latmatch = (TextView) findViewById(R.id.lat);
        Longmatch = (TextView) findViewById(R.id.longi);
        adress = (TextView) findViewById(R.id.adresse);
        Equip1 = findViewById(R.id.equip1);
        Equip2 = findViewById(R.id.equip2);



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

            Latmatch.setText("Latitude du match : " + String.valueOf(lat));
            Longmatch.setText("Latitude du match : " + String.valueOf(lng));


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String adressgeo = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                //String city = addresses.get(0).getLocality();
                //String state = addresses.get(0).getAdminArea();
                //String country = addresses.get(0).getCountryName();
                //String postalCode = addresses.get(0).getPostalCode();


                System.out.println(adressgeo);
                //System.out.println(city);
                //System.out.println(state);
                //System.out.println(country);
                //System.out.println(postalCode);

                adress.setText(adressgeo);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void ajoutMatch(View view) {
        //@SuppressWarnings("unchecked")
        //ArrayAdapter<Match> adapter = (ArrayAdapter<Match>) mListView.getAdapter();
        Match match = null;


        match = datasource.createMatch(Equip1.getText().toString(), Equip2.getText().toString(), adress.getText().toString(), "0", "0", "0", "0");
        // adapter.add(match);

        // adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, PendantMatch.class);
        intent.putExtra("equipe1", match.getTeam1());
        intent.putExtra("equipe2", match.getTeam2());
        intent.putExtra("idmatch", match.getId());
        startActivity(intent);
        finish();
    }

    public void myClickHandler(View view) throws ExecutionException, InterruptedException {

        if (view.getId() == R.id.ajoutmatch) {

            ajoutMatch(view);


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
