package fr.univpau.paupark.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class NoDataDialogListener implements DialogInterface.OnClickListener {
	private Context context;
	
	public NoDataDialogListener(Context context) {
		this.context=context;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) 
			dialog.cancel();
		else if (which == DialogInterface.BUTTON_NEUTRAL) 
			context.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));	
	}
}
