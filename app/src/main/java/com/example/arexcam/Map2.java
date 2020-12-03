package com.example.arexcam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Map2 extends AppCompatActivity implements View.OnClickListener, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback {

    private GoogleMap map2;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onMapReady(GoogleMap map2) {

        this.map2 = map2;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        map2.setMyLocationEnabled(true);
        map2.setOnMyLocationButtonClickListener(this);
        map2.setOnMyLocationButtonClickListener(this);


    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

        Toast.makeText(this, "Current location:\n"+ location, Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onMyLocationButtonClick() {


        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();



        return false;
    }


}