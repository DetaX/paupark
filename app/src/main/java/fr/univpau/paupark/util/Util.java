package fr.univpau.paupark.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.preference.CheckBoxPreference;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.ActivateGPSDismissListener;
import fr.univpau.paupark.listener.ActivateGPSListener;
import fr.univpau.paupark.listener.TipDialogListener;
import fr.univpau.paupark.pojo.Tip;

public class Util {
	public static final int GPS_STATUS = 43;
	
	public static void dialog(Context context, Tip tip, ArrayList<Tip> tips) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		final View detailsView = inflater.inflate(R.layout.tip_details, null);
		
		TextView titre = (TextView)detailsView.findViewById(R.id.tip_details_titre);
		underlineText(titre, "Titre", " : " + tip.getTitre());
		TextView adresse = (TextView)detailsView.findViewById(R.id.tip_details_adresse);
		underlineText(adresse, "Adresse", " : " + tip.getAdresse());
		TextView commune = (TextView)detailsView.findViewById(R.id.tip_details_commune);
		underlineText(commune, "Commune", " : " + tip.getCommune());
		TextView places = (TextView)detailsView.findViewById(R.id.tip_details_places);
		underlineText(places, "Places", " : " + tip.getCapacite());
		TextView commentaire = (TextView)detailsView.findViewById(R.id.tip_details_commentaire);
		underlineText(commentaire, "Commentaire", " : " + tip.getCommentaire());
		TextView pseudo = (TextView)detailsView.findViewById(R.id.tip_details_pseudo);
		underlineText(pseudo, "Ajouté par", " : " + tip.getPseudo());
		TextView fiabilite = (TextView)detailsView.findViewById(R.id.tip_details_fiabilite);
		String strFiabilite = (tip.getFiabilite() == -1) ? "Pas de note" : tip.getFiabilite() + "/5";
		underlineText(fiabilite, "Note moyenne", " : " + strFiabilite);
		
		

		alertDialogBuilder.setView(detailsView);
		
		alertDialogBuilder
			.setCancelable(true)
			.setNegativeButton("Retour", new TipDialogListener(context, tip, tips))
			.setNeutralButton("Noter", new TipDialogListener(context, tip, tips))
			.setPositiveButton("Voir sur GMAPs",new TipDialogListener(context, tip, tips));
		
			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
		
	}
	public static void dialog(Context context, int position, ArrayList<Tip>tips) {
		Tip tip = tips.get(position);
		dialog(context, tip,tips);
	}
	
	private static void underlineText(TextView view, String underline, String not_underline){
		SpannableString content = new SpannableString(underline + not_underline);
		content.setSpan(new UnderlineSpan(), 0, underline.length(), 0);
		view.setText(content);
	}
	
	public static void showGPSDisabledAlertToUser(Activity context, CheckBoxPreference gps){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Le GPS est désactivé sur votre appareil. Voulez-vous l'activer ?")
        .setCancelable(false)
        .setPositiveButton("Activer le GPS dans les options", new ActivateGPSListener(context));
        alertDialogBuilder.setNegativeButton("Retour", new ActivateGPSListener(context));
        AlertDialog alert = alertDialogBuilder.create();
        alert.setOnDismissListener(new ActivateGPSDismissListener(gps));
        alert.show();
    }
	


}
