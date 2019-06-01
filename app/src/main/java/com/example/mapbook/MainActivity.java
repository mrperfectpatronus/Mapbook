package com.example.mapbook;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity{

    private BottomSheetBehavior bottomSheetBehavior;
    private TextView bText;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list;
    private RAdapter adapter;

    private FusedLocationProviderClient client;
    private Button bLoc, mLoc;
    private LocationManager locationManager;
    private int REQUEST_LOCATION = 1, f1=0;
    private String latitude, longitude;

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLoc = findViewById(R.id.bloc);
        mLoc = findViewById(R.id.mloc);
        client = LocationServices.getFusedLocationProviderClient(this);
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View bsheet = findViewById(R.id.bsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bsheet);
        bText = findViewById(R.id.bText);
        recyclerView = findViewById(R.id.rview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list = Arrays.asList(getResources().getStringArray(R.array.Books));
        adapter = new RAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        bottomSheetBehavior.setPeekHeight(150);
        bottomSheetBehavior.setHideable(false);

        bLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                    buildAlertMessageNoOps();

                }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    getLocation();
                }
            }
        });

        mLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                    buildAlertMessageNoOps();

                }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    f1=1;
                    getLocation();
                }
            }
        });

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else{
            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        double latti =  location.getLatitude();
                        double longi =  location.getLongitude();
                        latitude = String.valueOf(latti);
                        longitude = String.valueOf(longi);
                        if(f1 == 0)
                            bText.setText("Your Current Location is" + "\n" + "Latitude = "+ latitude + "\n" + "Longitude = " + longitude);
                        else{
                            MapsActivity mapsActivity = new MapsActivity();
                            mapsActivity.location(latti, longi);
                            f1 = 0;
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Unable to Track Location", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void buildAlertMessageNoOps(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn On Your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
    }

}
