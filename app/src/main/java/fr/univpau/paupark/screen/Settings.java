package fr.univpau.paupark.screen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import fr.univpau.paupark.R;
import fr.univpau.paupark.util.Util;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    private SettingsFragment fragment;
    public static final String GEOLOCATION_SETTING_KEY = "gps";
    public static final String GEOLOCATION_RANGE_SETTING_KEY = "range";
    public static final String PSEUDO_SETTING_KEY = "pseudo";
    public static final String PAGINATION_SETTING_KEY = "pagination";
    public static final int PAGINATION_MAX_PARKINGS = 10;
    public static SharedPreferences PREFERENCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment).commit();

        PREFERENCE.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {

        switch (key) {
            case GEOLOCATION_SETTING_KEY:
                boolean enabled = sharedPreferences.getBoolean(key, false);
                fragment.setSeekBarEnabled(enabled);
                if (enabled) {
                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !this.isFinishing()) {
                        Util.showGPSDisabledAlertToUser(this, fragment.getGPS());
                    }
                }
                break;
            case GEOLOCATION_RANGE_SETTING_KEY:
                int range = sharedPreferences.getInt(key, 0) + 100;
                fragment.setTitle(getString(R.string.settings_distance) + range);
                break;
            case PAGINATION_SETTING_KEY:
                break;
            case PSEUDO_SETTING_KEY:
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.GPS_STATUS && resultCode == 0) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                fragment.getGPS().setChecked(false);
            } else
                fragment.getGPS().setChecked(true);
        }
    }
}
