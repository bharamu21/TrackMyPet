package com.benayah.app.trackmypet;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.benayah.app.trackmypet.POJO.Place;
import com.benayah.app.trackmypet.Services.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 24-07-2017.
 */

public class PetIndustryMapActivity extends AppCompatActivity {

    MapView mStorelocation;
    GoogleMap mStoreMap;
    Place storeDetails;
    TrackGPS gps;
    double mLatitude;
    double mLongitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.store_on_map_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gps = new TrackGPS(PetIndustryMapActivity.this);

        Bundle bundle = getIntent().getExtras();

        storeDetails = (Place) bundle.getSerializable("place_details");

        Log.d("place",storeDetails.getPlaceName()+" ,"+storeDetails.getPlaceAddress()+","+
         storeDetails.getLatitude()+","+storeDetails.getLongitude());

        mStorelocation = (MapView) findViewById(R.id.store_locatore);

        mStorelocation.onCreate(savedInstanceState);
        mStorelocation.onResume();

        try {
            MapsInitializer.initialize(PetIndustryMapActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mStorelocation.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mStoreMap = googleMap;

                if (ActivityCompat.checkSelfPermission(PetIndustryMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PetIndustryMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mStoreMap.setMyLocationEnabled(true);
                mStoreMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                if(gps.canGetLocation())
                {
                    mLatitude = gps.getLatitude();
                    mLongitude = gps.getLongitude();

                    CameraPosition position = new CameraPosition.Builder().target(new LatLng(mLatitude,mLongitude)).zoom(12).build();
                    mStoreMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                }

                MarkerOptions options = new MarkerOptions().position(new LatLng(storeDetails.getLatitude(),storeDetails.getLongitude()))
                        .title(storeDetails.getPlaceName()).icon(BitmapDescriptorFactory.defaultMarker());

                mStoreMap.addMarker(options);


            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;

            /*case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
