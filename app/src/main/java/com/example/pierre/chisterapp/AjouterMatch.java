package com.example.pierre.chisterapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AjouterMatch extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private TextView Latmatch;
    private TextView Longmatch;
    EditText Equip1;
    EditText Equip2;

    double lat;
    double lng;

    private Marker clickedMarker;
    private Marker currentMarker;
    private LocationManager locationManager;

    private String provider;



    String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private int USER_LOCATION_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_match);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);




        Latmatch = (TextView) findViewById(R.id.lat);
        Longmatch = (TextView) findViewById(R.id.longi);
        Equip1 = findViewById(R.id.equip1);
        Equip2 = findViewById(R.id.equip2);




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

            Latmatch.setText("Latitude du match "+ String.valueOf(lat));
            Longmatch.setText("Longitude du match "+ String.valueOf(lng));


        }
    }

    @Override
    protected void onPause() {
        super.onPause();



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
