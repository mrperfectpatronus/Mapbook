package com.example.mapbook;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static Double lati, longi;

    public void location(Double lat, Double lon){
        lati = lat;
        longi = lon;
        Log.d("TAG", lat.toString() + lon.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("TAG1", lati.toString() + longi.toString());
        LatLng loc = new LatLng(lati,longi);
        Log.d("TAG2", lati.toString() + longi.toString());
        mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
