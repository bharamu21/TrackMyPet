package com.benayah.app.trackmypet.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Benayah on 19/6/2017.
 */

public class Place implements Serializable {
    private String placeName;
    private String distance;
    private String placeAddress;
    private double latitude;
    private double longitude;

    public Place(String placeName, String placeAddress,String distance,double latitude,double longitude)
    {
        this.placeName = placeName;
        this.distance = distance;
        this.placeAddress = placeAddress;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }


    /* @Override
    public int compareTo(Place arg0) {


    }*/

   /* @Override
    public int compareTo(Object obj) {
        if (this.distance > ((Place)(obj)).distance)
            return 1;
        else
            return 0;
    }*/
}
