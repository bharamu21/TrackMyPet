package com.benayah.app.trackmypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.benayah.app.trackmypet.Adapters.StoreListAdapter;
import com.benayah.app.trackmypet.POJO.Place;
import com.benayah.app.trackmypet.Services.TrackGPS;
import com.benayah.app.trackmypet.Utils.DataParser;
import com.benayah.app.trackmypet.Utils.RecyclerViewClickListener;
import com.benayah.app.trackmypet.Utils.UrlConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by User on 18-07-2017.
 */

public class PetIndustryActivity extends AppCompatActivity {

    Button mGetListOfStoresBtn;
    MapView mStoresListMapView;
    RecyclerView mPetStoreListCardView;
    TrackGPS gps;
    double mLatitude;
    double mLongitude;

    GoogleMap mGoogleMap;

    ProgressDialog dialog;

    ArrayList<Place> storeList;
    StoreListAdapter storeListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_store_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gps = new TrackGPS(PetIndustryActivity.this);
        //mGetListOfStoresBtn = (Button) findViewById(R.id.store_list_btn);
        mStoresListMapView = (MapView) findViewById(R.id.pet_store_map_view);
        mPetStoreListCardView = (RecyclerView) findViewById(R.id.recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mPetStoreListCardView.setLayoutManager(mLayoutManager);

        mPetStoreListCardView.addOnItemTouchListener(new RecyclerViewClickListener(PetIndustryActivity.this,
                new PetIndustryActivity.OnContactItemClickListener()));



        mStoresListMapView.onCreate(savedInstanceState);
        mStoresListMapView.onResume();

        Bundle bundle = getIntent().getExtras();
        final String actionName = bundle.getString("activity_name");
        /*Log.d("actionName",actionName);*/
        if(actionName.equals("pet_store"))
        {
          setTitle("Pet Stores");
        }
        else if(actionName.equals("veterinary_care"))
        {
           setTitle("Veterinary Clinic");
        }

        storeList = new ArrayList<>();


        try {
            MapsInitializer.initialize(getApplicationContext());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mStoresListMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;

                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(PetIndustryActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PetIndustryActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                //mGoogleMap.setMyLocationEnabled(true);

                if(gps.canGetLocation())
                {
                    mLatitude = gps.getLatitude();
                    mLongitude = gps.getLongitude();

                    LatLng location = new LatLng(mLatitude,mLongitude);

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                if(actionName.equals("pet_store"))
                {
                    if(storeList.size() != 0)
                    {
                        storeList.clear();
                    }
                    getNearByPetStores(mLatitude,mLongitude,actionName);
                }
                else if(actionName.equals("veterinary_care"))
                {
                    if(storeList.size() != 0)
                    {
                        storeList.clear();
                    }
                    getNearByPetStores(mLatitude,mLongitude,actionName);
                }
            }
        });


        /*mGetListOfStoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Log.d("storeList length",storeList.size()+"");*//*
                Bundle bundle = new Bundle();
                Intent displayStoreListIntent = new Intent(PetIndustryActivity.this,PetIndustryListActivity.class);
                bundle.putSerializable("list",storeList);
                displayStoreListIntent.putExtras(bundle);
                startActivity(displayStoreListIntent);
            }
        });*/
    }



    private void getNearByPetStores(double latitude, double longitude,String searchString) {

        dialog = new ProgressDialog(PetIndustryActivity.this);
        dialog.setTitle("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 5000);
        googlePlacesUrl.append("&type=" + searchString);
        googlePlacesUrl.append("&sensor=true");
        //AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0
        //AIzaSyDmKAffdI_xuZP9D9_tP-YDnbLcdFVSVfc
        //AIzaSyBBNjrMYcJ797gxlKNXKe7FNwNNnWdaI-8
        //AIzaSyCm1gE1qsjAqES2465yko7WlT4p5WUsteI - mine
        googlePlacesUrl.append("&key=" + "AIzaSyA_ZOiYGuVouHsMUmIkqmlz2_Lo4SYWlA0");
        Log.d("URL", googlePlacesUrl.toString());

        new GetNearByPlaces().execute(googlePlacesUrl.toString());
    }

    public class GetNearByPlaces extends AsyncTask<String, String, String> {
        String googlePlacesData;

        @Override
        protected void onPreExecute() {
            Log.d("OnPreExecute", "Entered into on preexecute method");
            /*dialog = new ProgressDialog();
            dialog.setMessage("Please wiat we are getting the information...");
            dialog.setCancelable(false);
            dialog.show();*/

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.d("GetNearbyBanksData", "doInBackground entered");


                UrlConnection urlConnection = new UrlConnection();
                googlePlacesData = urlConnection.readUrl(params[0]);
                Log.d("GooglePlacesReadTask", googlePlacesData);
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("GooglePlacesReadTask", "onPostExecute Entered");

            //getPlaceDetails("ChIJoa0nOpQXrjsRdvu4nU3j_Gc");
            ArrayList<HashMap<String, String>> nearbyPlacesList = null;
            DataParser dataParser = new DataParser();
            nearbyPlacesList = dataParser.parse(result);
            showNearbyPlacesId(nearbyPlacesList);
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        }
    }

    public void showNearbyPlacesId(ArrayList<HashMap<String, String>> nearByPlacesIdList) {
        String storeName;
        double latitude;
        double longitude;
        String distance;
        String storeAddress;

        Log.d("showNearByPlaces", "inside nearByPlacesId");


       /* GetPlaceDetails getPlaceDetails = new GetPlaceDetails();
        getPlaceDetails.execute("ChIJoa0nOpQXrjsRdvu4nU3j_Gc");*/
        //getPlaceDetails("ChIJoa0nOpQXrjsRdvu4nU3j_Gc");
        for (int i = 0; i < nearByPlacesIdList.size(); i++) {
            HashMap<String, String> googlePlace = nearByPlacesIdList.get(i);
            storeName = googlePlace.get("name");
            if(storeName == null || storeName.isEmpty())
            {
                //Log.d("CommonMemberPlaceDetail", storeName+"1");
                storeName = googlePlace.get("place_name");
            }
            storeAddress = googlePlace.get("vicinity");
            latitude = Double.parseDouble(googlePlace.get("lat"));
            longitude = Double.parseDouble(googlePlace.get("lng"));
            distance = getDistance(latitude, longitude);
            //Log.d("CommonMemberPlaceDetail", storeName+","+distance+","+storeAddress);
            Place place = new Place(storeName, storeAddress, distance, latitude, longitude);
            storeList.add(place);

        }

        if (storeList.size() != 0) {
            Collections.sort(storeList, new Comparator<Place>() {
                @Override
                public int compare(Place placeDetails1, Place placeDetails2) {
                    return placeDetails1.getDistance().compareTo(placeDetails2.getDistance());
                }
            });
        }

        drawMarker(storeList);
        addToRecyclerView(storeList);
    }

    protected void drawMarker(ArrayList<Place> petStoreList) {

        MarkerOptions markerOptions = new MarkerOptions();
        Log.d("drawMarker",petStoreList.size()+"");
        for (int i = 0; i < petStoreList.size(); i++) {
            markerOptions.position(new LatLng(petStoreList.get(i).getLatitude(),
                    petStoreList.get(i).getLongitude()));
            markerOptions.title(petStoreList.get(i).getPlaceName());



            mGoogleMap.addMarker(markerOptions);
        }

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                int marker_id = Integer.parseInt(marker.getId().substring(1));

                Intent mapIntent = new Intent(PetIndustryActivity.this,PetIndustryMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("place_details",storeList.get(marker_id));
                mapIntent.putExtras(bundle);
                startActivity(mapIntent);
                /*Place place = storeList.get(marker_id);
                Log.d("place",place.getPlaceName()+" ,"+place.getPlaceAddress()+","+
                        place.getLatitude()+","+place.getLongitude());*/
            }
        });


    }

    public void addToRecyclerView(ArrayList<Place> stores)
    {
        storeListAdapter = new StoreListAdapter(PetIndustryActivity.this,stores);
        mPetStoreListCardView.setAdapter(storeListAdapter);
    }



    public String getDistance(double latitude, double longitude) {
        Location placeLocation = new Location("");
        placeLocation.setLatitude(latitude);
        placeLocation.setLongitude(longitude);

        Location userLocation = new Location("");
        userLocation.setLatitude(mLatitude);
        userLocation.setLongitude(mLongitude);

        double distance = userLocation.distanceTo(placeLocation) / 1000;

        return distance + "";
    }


    private class OnContactItemClickListener extends RecyclerViewClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, int position) {
            // Do something when an item is clicked, or override something else.ihjkmj

            //Toast.makeText(getActivity(),"onItemclick",Toast.LENGTH_SHORT).show();
            Place place = storeList.get(position);
            Intent showStoreOnMapIntent = new Intent(PetIndustryActivity.this,PetIndustryMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("place_details",place);
            showStoreOnMapIntent.putExtras(bundle);
            startActivity(showStoreOnMapIntent);

            //Log.d("place",place.getPlaceName()+" ,"+place.getPlaceAddress()+","+place.getLatitude()+","+place.getLongitude());
        }

        @Override
        public void onItemLongPress(View childView, int position) {
            super.onItemLongPress(childView, position);
            //showAlertDialogBox(position);
            //Toast.makeText(getActivity(),"onItemLongclick = "+position,Toast.LENGTH_SHORT).show();
        }
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
