package fr.univpau.paupark.listener.gps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import fr.univpau.paupark.util.Util;

public class ActivateGPSListener implements OnClickListener {
	private final Activity context;
	
	public ActivateGPSListener(Activity context) {
		this.context=context;
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE)
			dialog.cancel();
		else {
			Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            
            context.startActivityForResult(callGPSSettingIntent, Util.GPS_STATUS);
		}

	}

}
