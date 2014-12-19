package fr.univpau.paupark.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.preference.CheckBoxPreference;

public class ActivateGPSDismissListener implements OnDismissListener {
	private CheckBoxPreference gps;
	
	public ActivateGPSDismissListener(CheckBoxPreference gps) {
		this.gps=gps;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		gps.setChecked(false);
	}

}
