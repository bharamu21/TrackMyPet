package com.benayah.app.trackmypet.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 26-07-2017.
 */

public class PlaceArrayAdapter extends ArrayAdapter<PlaceArrayAdapter.PlaceAutoComplete> implements Filterable {

    private static final String TAG = "PlaceArrayAdapters";
    LatLngBounds mBounds;
    AutocompleteFilter mFilter;
    GoogleApiClient googleApiClient;
    ArrayList<PlaceAutoComplete> placeList;


    public PlaceArrayAdapter(Context context, int resource, LatLngBounds mBounds, AutocompleteFilter mFilter) {
        super(context, resource);

        this.mBounds = mBounds;
        this.mFilter = mFilter;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient)
    {
        if(googleApiClient == null || !googleApiClient.isConnected())
        {
            this.googleApiClient = null;
        }
        else
        {
            this.googleApiClient = googleApiClient;
        }
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Nullable
    @Override
    public PlaceAutoComplete getItem(int position) {
        return placeList.get(position);
    }

    private ArrayList<PlaceAutoComplete> getPrediction(CharSequence constraint)
    {
        if(googleApiClient != null)
        {
            Log.i(TAG, "Executing autocomplete query for: " + constraint);

            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi.getAutocompletePredictions(googleApiClient,constraint.toString(),mBounds,mFilter);

            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            Status status = autocompletePredictions.getStatus();

            if(!status.isSuccess())
            {
                Toast.makeText(getContext(), "Error: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting place predictions: " + status
                        .toString());
                autocompletePredictions.release();

                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");

            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();

            ArrayList<PlaceAutoComplete> placeAutoCompleteList = new ArrayList<>(autocompletePredictions.getCount());

            while (iterator.hasNext())
            {
                AutocompletePrediction prediction = iterator.next();
                placeAutoCompleteList.add(new PlaceAutoComplete(prediction.getPlaceId(),prediction.getFullText(null)));
            }

            autocompletePredictions.release();

            return placeAutoCompleteList;
        }

        Log.e(TAG, "Google API client is not connected.");
        return null;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults  filterResults = new FilterResults();
                if(constraint != null)
                {
                    placeList = getPrediction(constraint);
                    if(placeList != null)
                    {
                        filterResults.values = placeList;
                        filterResults.count = placeList.size();
                    }
                }

                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    public class PlaceAutoComplete {

        public CharSequence placeId;
        public CharSequence description;

        PlaceAutoComplete(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }
}
