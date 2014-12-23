package fr.univpau.paupark.listener;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.screen.MainScreen;
import fr.univpau.paupark.screen.ParkingsFragment;
import fr.univpau.paupark.screen.TipsFragment;
import fr.univpau.paupark.util.DBHandler;

public class NoDataDialogListener implements DialogInterface.OnClickListener {
	private final Context context;
	private final DBHandler db;
    private final Fragment fragment;

	public NoDataDialogListener(Context context, DBHandler db, Fragment fragment) {
		this.context=context;
        this.db = db;
        this.fragment = fragment;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) {
            dialog.cancel();
            if (fragment.getTag().equals(MainScreen.PARKING_TAG)) {
                ArrayList<Parking> parkings = db.getAllParkings();
                MainScreen.PARKINGS = parkings;
                ParkingsFragment.firstTime = false;
                ((ParkingsFragment)fragment).makePages(parkings);
                Toast toast = Toast.makeText(context, "La liste de parkings de votre dernière connexion a été chargé", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                ArrayList<Tip> tips = db.getAllTips();
                MainScreen.TIPS = tips;
                TipsFragment.firstTime = false;
                ((TipsFragment)fragment).makePages(tips);
                Toast toast = Toast.makeText(context, "La liste de bons plans de votre dernière connexion a été chargé", Toast.LENGTH_LONG);
                toast.show();
            }
        }
		else if (which == DialogInterface.BUTTON_NEUTRAL) 
			context.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));	
	}
}
