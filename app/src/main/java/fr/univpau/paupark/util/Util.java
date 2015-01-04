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

import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.gps.ActivateGPSDismissListener;
import fr.univpau.paupark.listener.gps.ActivateGPSListener;
import fr.univpau.paupark.listener.tip.TipDialogListener;
import fr.univpau.paupark.pojo.Tip;

public class Util {
	public static final int GPS_STATUS = 43;
	
	public static void dialog_detail_tip(Context context, Tip tip, List<Tip> tips) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		final View detailsView = inflater.inflate(R.layout.tip_details, null);
		
		TextView titre = (TextView)detailsView.findViewById(R.id.tip_details_titre);
		underlineText(titre, context.getString(R.string.detail_tip_titre), " : " + tip.getTitre());
		TextView adresse = (TextView)detailsView.findViewById(R.id.tip_details_adresse);
		underlineText(adresse, context.getString(R.string.detail_tip_adresse), " : " + tip.getAdresse());
		TextView commune = (TextView)detailsView.findViewById(R.id.tip_details_commune);
		underlineText(commune, context.getString(R.string.detail_tip_commune), " : " + tip.getCommune());
		TextView places = (TextView)detailsView.findViewById(R.id.tip_details_places);
		underlineText(places, context.getString(R.string.detail_tip_places), " : " + tip.getCapacite());
		TextView commentaire = (TextView)detailsView.findViewById(R.id.tip_details_commentaire);
		underlineText(commentaire, context.getString(R.string.detail_tip_comment), " : " + tip.getCommentaire());
		TextView pseudo = (TextView)detailsView.findViewById(R.id.tip_details_pseudo);
		underlineText(pseudo, context.getString(R.string.detail_tip_added_by), " : " + tip.getPseudo());
		TextView fiabilite = (TextView)detailsView.findViewById(R.id.tip_details_fiabilite);
		String strFiabilite = (tip.getFiabilite() == -1) ? context.getString(R.string.detail_tip_no_note) : tip.getFiabilite() + "/5";
		underlineText(fiabilite, context.getString(R.string.detail_tip_average), " : " + strFiabilite);
		
		

		alertDialogBuilder.setView(detailsView);
		
		alertDialogBuilder
			.setCancelable(true)
			.setNegativeButton(context.getString(R.string.back_dialog), new TipDialogListener(context, tip, tips))
			.setNeutralButton(context.getString(R.string.note_dialog_ok), new TipDialogListener(context, tip, tips))
			.setPositiveButton(context.getString(R.string.detail_tip_gmap),new TipDialogListener(context, tip, tips));
		
			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
		
	}
	public static void dialog_detail_tip(Context context, int position, List<Tip> tips) {
		Tip tip = tips.get(position);
		dialog_detail_tip(context, tip, tips);
	}
	
	private static void underlineText(TextView view, String underline, String not_underline){
		SpannableString content = new SpannableString(underline + not_underline);
		content.setSpan(new UnderlineSpan(), 0, underline.length(), 0);
		view.setText(content);
	}
	
	public static void showGPSDisabledAlertToUser(Activity context, CheckBoxPreference gps){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getString(R.string.gps_non_active))
        .setCancelable(false)
        .setPositiveButton(context.getString(R.string.activate_gps), new ActivateGPSListener(context));
        alertDialogBuilder.setNegativeButton(context.getString(R.string.back_dialog), new ActivateGPSListener(context));
        AlertDialog alert = alertDialogBuilder.create();
        alert.setOnDismissListener(new ActivateGPSDismissListener(gps));
        alert.show();
    }

}
