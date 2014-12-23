package fr.univpau.paupark.screen;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import fr.univpau.paupark.R;

public class SettingsFragment extends PreferenceFragment {
    private Preference range;
    private Preference gps;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        range = findPreference(Settings.GEOLOCATION_RANGE_SETTING_KEY);
        gps = findPreference(Settings.GEOLOCATION_SETTING_KEY);

        int rangeValue = Settings.PREFERENCE.getInt(Settings.GEOLOCATION_RANGE_SETTING_KEY, 0) + 100;
        setTitle(getString(R.string.settings_distance) + rangeValue);

        setSeekBarEnabled(Settings.PREFERENCE.getBoolean(Settings.GEOLOCATION_SETTING_KEY, false));
        PackageManager packageManager = this.getActivity().getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (!hasGPS) {
            gps.setEnabled(false);
            gps.setSummary(getString(R.string.nogps));
        }
    }

    public void setTitle(String title) {
        range.setTitle(title);
    }

    public void setSeekBarEnabled(boolean b) {
        range.setEnabled(b);
    }

    public CheckBoxPreference getGPS() {
        return (CheckBoxPreference) gps;
    }

}
