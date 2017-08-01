package com.benayah.app.trackmypet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 06-07-2017.
 */

public class PetDetailsActivity extends AppCompatActivity {

    MapView mMapView;

    GoogleMap mGoogleMap;
    double latitude,longitude;
    Marker marker ;

    TextView mLostPetAddress;
    String lostPetAddress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lostpet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select location");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
            lostPetAddress = extras.getString("address");
        }

        mMapView = (MapView) findViewById(R.id.google_map);
        mLostPetAddress = (TextView) findViewById(R.id.location_details);
        mLostPetAddress.setText(lostPetAddress);


        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {

                mGoogleMap = mMap;

                // For showing a move to my location button

                // mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


                // For dropping a marker at a point on the Map
                /*LatLng sydney = new LatLng(-34, 151);*/

                /*if(gps.canGetLocation())
                {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Log.d("Latitude = "+latitude,"Longitude = "+longitude);*/
                LatLng location = new LatLng(latitude,longitude);
                //mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(17).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                marker = mGoogleMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker()));
               /* }
                else
                {
                    gps.showSettingsAlert();
                }*/


                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                      /*  mDoneBtn.setVisibility(View.VISIBLE);
                        double latitude1 = latLng.latitude;
                        double longitude1 = latLng.longitude;

                        //D/Latitude = 13.0678851: Longitude = 77.6432687
                        Log.d("Latitude1 = "+latitude1,"Longitude1 = "+longitude1);
                        if(latLng.latitude != 0.0 && latLng.longitude != 0.0)
                        {
                            petAddress = getAddressFromLatLang(latLng);

                            Log.d("mPetFoundOrLostAddress",petAddress);
                        }

                        if(marker != null)
                        {
                            marker.remove();
                        }*/
                        // Log.d("mPetFoundOrLostAddress",petAddress);
                        marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));


                    }
                });

            }
        });

    }
}
