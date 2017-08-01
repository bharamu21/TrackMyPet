package com.benayah.app.trackmypet;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.benayah.app.trackmypet.Adapters.PlaceArrayAdapter;
import com.benayah.app.trackmypet.Services.TrackGPS;
import com.benayah.app.trackmypet.Utils.CommonAppMember;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * Created by Benayah on 22/5/2017.
 */

public class MapActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,AdapterView.OnItemClickListener {

    TrackGPS gps;
    private static final String LOG_TAG = "MainActivity";

    FloatingActionButton mDoneBtn;

    MapView mMapView;
    Bundle extra;

    GoogleMap mGoogleMap;
    double latitude,longitude;
    double petLatitude,petLongitude;
    Marker marker ;
    String petAddress;
    LinearLayout mGoBackToHome;
    AutoCompleteTextView googleAutoCompleteTextview;
    GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static LatLngBounds BOUNDS_MOUNTAIN_VIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select location");
        gps = new TrackGPS(this);

        mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this,GOOGLE_API_CLIENT_ID,this)
                .addConnectionCallbacks(this)
                .build();


        mDoneBtn = (FloatingActionButton) findViewById(R.id.fab);
        googleAutoCompleteTextview = (AutoCompleteTextView) findViewById(R.id.google_auto_complete);
        googleAutoCompleteTextview.setThreshold(1);
        googleAutoCompleteTextview.setOnItemClickListener(this);

        mDoneBtn.setVisibility(View.GONE);
        mDoneBtn.setOnClickListener(this);

        extra = getIntent().getExtras();

        //mGoBackToHome = (LinearLayout) findViewById(R.id.go_back_home);
        //mGoBackToHome.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.map);


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

                if(gps.canGetLocation())
                {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Log.d("Latitude = "+latitude,"Longitude = "+longitude);
                    LatLng location = new LatLng(latitude,longitude);
                    //mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker

                    BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                            new LatLng(latitude, longitude), new LatLng(latitude, longitude));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(14).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                else
                {
                    gps.showSettingsAlert();
                }


                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        mDoneBtn.setVisibility(View.VISIBLE);
                        petLatitude = latLng.latitude;
                        petLongitude = latLng.longitude;

                        //D/Latitude = 13.0678851: Longitude = 77.6432687
                        //Log.d("Latitude1 = "+latitude1,"Longitude1 = "+longitude1);
                        if(petLatitude != 0.0 && petLongitude != 0.0)
                        {
                            petAddress = getAddressFromLatLang(latLng);

                            //Log.d("mPetFoundOrLostAddress",petAddress);
                        }

                        if(marker != null)
                        {
                            marker.remove();
                        }
                        // Log.d("mPetFoundOrLostAddress",petAddress);
                        marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));


                    }
                });

            }
        });

        mPlaceArrayAdapter = new PlaceArrayAdapter(getApplicationContext(),R.layout.google_prediction_text_layout,
                BOUNDS_MOUNTAIN_VIEW,null);
        googleAutoCompleteTextview.setAdapter(mPlaceArrayAdapter);
    }



    public String getAddressFromLatLang(LatLng latLng)
    {
        try {

            Geocoder addressGeoCoder = new Geocoder(this, Locale.getDefault());
            if(addressGeoCoder != null)
            {
                Log.d("Latitude2 = "+latLng.latitude,"Longitude2 = "+latLng.longitude);
                List<Address> mPetFoundOrLostAddress = addressGeoCoder.getFromLocation(latLng.latitude,latLng.longitude,3);
                StringBuilder address = new StringBuilder("");
                if(mPetFoundOrLostAddress != null && mPetFoundOrLostAddress.size() != 0)
                {
                    Address mPetAddress = mPetFoundOrLostAddress.get(0);
                    Log.d("mPetFoundOrLostAddress1",mPetAddress.toString());
                    for(int i=0;i<mPetAddress.getMaxAddressLineIndex();i++)
                    {
                        address.append(mPetAddress.getAddressLine(i)).append("\n");
                    }
                }
                //Log.d("mPetFoundOrLostAddress",address.toString());
                return address.toString();
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {


            case R.id.fab:
                Log.d("Latitude2 = "+petLatitude,"Longitude2 = "+petLongitude);
                Log.d("mPetFoundOrLostAddress",petAddress);
                if(extra != null) {
                    String mForwardActivity = extra.getString("screen_name");
                    Log.d("MapActivity", "Fab clicked , " + mForwardActivity);
                   switch (mForwardActivity)
                   {
                       case "lost_pet":
                           Log.d("MapActivity", "" + mForwardActivity);
                           Intent lostPetDescriptionIntent = new Intent(this,LostPetDetailsActivity.class);
                           Bundle lostPetDescriptionBundle = new Bundle();
                           com.benayah.app.trackmypet.POJO.Place place =
                                   new com.benayah.app.trackmypet.POJO.Place("",petAddress,"",petLatitude,petLongitude);
                           lostPetDescriptionBundle.putSerializable("place_details",place);
                           lostPetDescriptionIntent.putExtras(lostPetDescriptionBundle);
                           startActivity(lostPetDescriptionIntent);
                           break;

                       case "found_pet":
                           Log.d("MapActivity", "" + mForwardActivity);
                           Intent foundPetDescriptionIntent = new Intent(this,FoundPetDetailsActivity.class);
                           startActivity(foundPetDescriptionIntent);
                           break;
                   }
                   /*  else if(mForwardActivity == "found_pet")
                    {
                        Log.d("MapActivity", "" + mForwardActivity);
                        Intent foundPetDescriptionIntent = new Intent(this,FoundPetDetailsActivity.class);
                        startActivity(foundPetDescriptionIntent);
                    }*/
                }

                /*Intent lostPetDescriptionIntent = new Intent(this,LostPetDetailsActivity.class);
                startActivity(lostPetDescriptionIntent);*/
                break;

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlaceArrayAdapter.PlaceAutoComplete item = mPlaceArrayAdapter.getItem(position);
        String placeId = String.valueOf(item.placeId);
        Log.i(LOG_TAG, "Selected: " + item.description);
        CommonAppMember.hideSoftKeyboard(MapActivity.this,googleAutoCompleteTextview);
        PendingResult<PlaceBuffer> placeBufferPendingResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);

        placeBufferPendingResult.setResultCallback(mUpdateResultCallBack);
    }

    private ResultCallback<PlaceBuffer> mUpdateResultCallBack = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess())
            {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +places.getStatus().toString());
                return;
            }

            Place place = places.get(0);

            LatLng searchedPlaceLatlng = place.getLatLng();
            petLatitude = searchedPlaceLatlng.latitude;
            petLongitude = searchedPlaceLatlng.longitude;
            if(petLatitude != 0.0 && petLongitude != 0.0)
            {
                petAddress = getAddressFromLatLang(searchedPlaceLatlng);


            }
            MarkerOptions markerOptions = new MarkerOptions().position(searchedPlaceLatlng)
                    .icon(BitmapDescriptorFactory.defaultMarker()).title((place.getAddress()).toString());
            mGoogleMap.addMarker(markerOptions);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(searchedPlaceLatlng).zoom(14).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            places.release();

            mDoneBtn.setVisibility(View.VISIBLE);
        }
    };




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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
