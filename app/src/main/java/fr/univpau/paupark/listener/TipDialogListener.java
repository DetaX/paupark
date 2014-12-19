package fr.univpau.paupark.listener;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import fr.univpau.paupark.R;
import fr.univpau.paupark.pojo.Tip;

public class TipDialogListener implements OnClickListener {
	private Context context;
	private Tip tip;
	private ArrayList<Tip> tips;
	public TipDialogListener(Context context, Tip tip,ArrayList<Tip> tips) {
		this.context=context;
		this.tip=tip;
		this.tips=tips;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE)
			dialog.cancel();
		else if (which == DialogInterface.BUTTON_NEUTRAL) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			final View modifyView = inflater.inflate(R.layout.note, null);
			alertDialogBuilder.setView(modifyView);
			
			RatingBar ratingBar = (RatingBar) modifyView.findViewById(R.id.ratingBar1);

			alertDialogBuilder.setTitle("Noter : " + tip.getTitre());
			alertDialogBuilder
				.setCancelable(true)
				.setNegativeButton("Retour", new RatingListener(context, tip, ratingBar,tips))
				.setNeutralButton("Noter", new RatingListener(context, tip, ratingBar,tips));

				AlertDialog alertDialog = alertDialogBuilder.create();

				alertDialog.show();
				
		}
		else {
			String uri = "geo:0,0?q=" + tip.getAdresse() + " " + tip.getCommune();
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
			context.startActivity(intent);
		}

	}

}
