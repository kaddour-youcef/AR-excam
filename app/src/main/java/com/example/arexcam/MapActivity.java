package com.example.arexcam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    // map gestion

    //initialisation
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    boolean cheked = (boolean) true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //initialisation de fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //permission
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == getPackageManager().PERMISSION_GRANTED) {

            getCurrentLocation();

        }else{
            // permission refusé
            // demander permission
ActivityCompat.requestPermissions(MapActivity.this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);


        }



        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cheked = false;
                    getCurrentLocation();
                }else{
                    cheked = true;
                    getCurrentLocation();
                }
            }
        });
        View v = findViewById(R.id.floatingActionButton3);
        v.setOnClickListener(this);
    }

    private void getCurrentLocation() {
        // localisation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // si resussie
                if(location != null) {
                 // syncronisé la map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                           // initialisé lat lng
                            LatLng latlng = new LatLng(location.getLatitude()
                               , location.getLongitude());

                            //option pour le marqueur
                            MarkerOptions options = new MarkerOptions().position(latlng).title("vous etes ici");
                           // option zoom map
                            if(cheked != true){
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latlng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        .bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else {
                                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latlng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        .bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                                        .build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }

                            // ajouté un marcker a la map

                            googleMap.addMarker(options);


                        }
                    });

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if (requestCode ==44  ){
          if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // quand la permission est accordé
            //on appel la methode
            getCurrentLocation();
          }
      }

    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.floatingActionButton3){
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }

    }

}