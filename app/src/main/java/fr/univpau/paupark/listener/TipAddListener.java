package fr.univpau.paupark.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.TipAddTask;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.screen.Settings;

public class TipAddListener implements OnClickListener {
	
	private Context context;
	private ArrayList<Tip> tips;
		
	public TipAddListener(Context context, ArrayList<Tip> tips){
		this.context = context;
		this.tips = tips;
	}

	@Override
	public void onClick(View v) {
		
		View parent = (View)v.getParent();
		EditText edit_nom = (EditText)parent.findViewById(R.id.add_nom);
		String nom = edit_nom.getText().toString();
		EditText edit_adresse = (EditText)parent.findViewById(R.id.add_adresse);
		String adresse = edit_adresse.getText().toString();
		EditText edit_commune = (EditText)parent.findViewById(R.id.add_commune);
		String commune = edit_commune.getText().toString();
		EditText edit_places = (EditText)parent.findViewById(R.id.add_places);
		String places = edit_places.getText().toString();
		EditText edit_commentaire = (EditText)parent.findViewById(R.id.add_commentaire);
		String commentaire = edit_commentaire.getText().toString();


		if(!nom.equals("") && !adresse.equals("") && !commune.equals("") && !places.equals("") && !commentaire.equals("")){

			SharedPreferences prefs = PreferenceManager
		            .getDefaultSharedPreferences(context);
		String pseudo = prefs.getString(Settings.PSEUDO_SETTING_KEY, "Anonymous");
		pseudo = (!pseudo.equals("")) ? pseudo : "Anonymous";
			Tip tip = new Tip(nom, adresse, commune, Integer.parseInt(places), commentaire,	pseudo, -1.d, 0);
			TipAddTask tipAddTask = new TipAddTask(context, tips, tip);
			tipAddTask.execute();
		
		}
		
		else{
			Toast toast = Toast.makeText(context, "Veuillez remplir les champs correctement", Toast.LENGTH_SHORT);
			toast.show();
		}
		

	}

}
