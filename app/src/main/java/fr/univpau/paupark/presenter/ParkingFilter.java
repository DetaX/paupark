package fr.univpau.paupark.presenter;


import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import fr.univpau.paupark.pojo.Parking;

public abstract class ParkingFilter {

    public static ArrayList<Parking> gps(ArrayList<Parking> parkings, double latitude, double longitude) {
        int range = 5000;
        float results[] = new float[1];
        Log.i("longitude", String.valueOf(longitude));Log.i("latitude", String.valueOf(latitude));
        ArrayList<Parking> filteredList = new ArrayList<Parking>();
        for(Parking pk:parkings){
            Location.distanceBetween(latitude, longitude, pk.getCoord()[0], pk.getCoord()[1], results);
            Log.i("distance", String.valueOf(results[0]));
            if(results[0] < range) {
                filteredList.add(pk);
            }
        }
        return filteredList;
    }

}
