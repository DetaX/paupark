package fr.univpau.paupark.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
import fr.univpau.paupark.util.Util;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	SettingsFragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 fragment = new SettingsFragment();
		 getFragmentManager().beginTransaction()
         .replace(android.R.id.content, fragment).commit();
		 SharedPreferences prefs = PreferenceManager
		            .getDefaultSharedPreferences(this);
		 prefs.registerOnSharedPreferenceChangeListener(this);
		 
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		switch(key){
		case "gps":
			boolean enabled = sharedPreferences.getBoolean(key, false);
			fragment.setSeekBarEnabled(enabled);
			if (enabled) {
				LocationManager locationManager = (LocationManager) this.getSystemService("location");

     	        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
     	        	Util.showGPSDisabledAlertToUser(this,fragment.getGPS());
     	        }
			}
			break;
		case "range":
			int range = sharedPreferences.getInt(key, 0) + 100;
			fragment.setTitle("Distance maximum : " + range);
			break;
		case "pagination":
			break;
		case "pseudo":
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Util.GPS_STATUS && resultCode == 0){
        	LocationManager locationManager = (LocationManager) this.getSystemService("location");

 	        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
 	        	fragment.getGPS().setChecked(false);
 	        }
 	        else
 	        	fragment.getGPS().setChecked(true);
        }
    }
}
