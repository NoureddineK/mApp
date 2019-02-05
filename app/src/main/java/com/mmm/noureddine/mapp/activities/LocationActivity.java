package com.mmm.noureddine.mapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.location.LocationRequest;
import com.mmm.noureddine.mapp.R;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmm.noureddine.mapp.components.GpsTracker;
import com.mmm.noureddine.mapp.components.MapResult;
import com.mmm.noureddine.mapp.db.DBHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_location);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleM) {
        if (googleM == null) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else {
            googleMap = googleM;
            // and move the map's camera to the same location.


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Toast.makeText(
                    getBaseContext(), "Alt: " + locationGPS.getAltitude() + " / Long: " + locationGPS.getLongitude(), Toast.LENGTH_SHORT).show();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude()), 16));

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(
                        getBaseContext(), "ACCESS LOCATION DENIED", Toast.LENGTH_SHORT).show();

                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            createMarker(48.117266, -1.6777926, "Rennes", "Bretagne");
            // createMarker(-33.852, 151.211, "Sydney", "Australie");
            createMarker(48.8534, 2.3488, "Paris", "Ici c'est Paris!");
            locationDB();
        }
    }

    private void createMarker(double latitude, double longitude, String title, String snippet) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
    }

    private void locationDB() {
        DBHandler db = new DBHandler(this);
        GpsTracker gpsTracker = new GpsTracker(this);

        db.addResult(gpsTracker.getLatitude(), gpsTracker.getLongitude(),
                "Player A", "Team B", 250);
        List<MapResult> listMap = db.getAllResult();
        if (listMap.size() > 0) {
            for (MapResult p : listMap) {
                Log.d("locationDB:", p.toString());
                double lat = p.getLatitude();
                double lon = p.getLongitude();
                createMarker(lat, lon, p.getTeamName(), String.valueOf(p.getScore()));
            }
        } else {
            Toast.makeText(
                    getBaseContext(), "No Scores to Show!", Toast.LENGTH_SHORT).show();
        }


    }


}


