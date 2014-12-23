package fr.univpau.paupark.listener;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;
import fr.univpau.paupark.screen.Settings;

public class CustomLocationListener implements LocationListener {

    private final ParkingsFragment fragment;

    public CustomLocationListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
        SharedPreferences.Editor prefEditor = Settings.PREFERENCE.edit();
        prefEditor.putBoolean(Settings.GEOLOCATION_SETTING_KEY, false);
        prefEditor.apply();
        ParkingFilter.gpsFilter = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (Settings.PREFERENCE.getBoolean(Settings.GEOLOCATION_SETTING_KEY, false)) {
            ParkingFilter.gpsFilter = true;
            ParkingFilter.latitude = location.getLatitude();
            ParkingFilter.longitude = location.getLongitude();
            fragment.makePages(ParkingFilter.filter(fragment.getParkings()));
        }
    }


}
