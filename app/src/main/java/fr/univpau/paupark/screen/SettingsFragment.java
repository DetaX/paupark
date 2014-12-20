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
        range = findPreference("range");
        gps = findPreference("gps");

        int rangeValue = Settings.PREFERENCE.getInt("range", 0) + 100;
        setTitle("Distance maximum : " + rangeValue);

        setSeekBarEnabled(Settings.PREFERENCE.getBoolean("gps", false));
        PackageManager packageManager = this.getActivity().getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (!hasGPS) {
            gps.setEnabled(false);
            gps.setSummary("VOUS DEVEZ AVOIR UN GPS POUR UTILISER CETTE FONCTIONNALITE. Permet de filtrer les parkings par rapport à votre position. Nécessite d'activer votre GPS.");
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
