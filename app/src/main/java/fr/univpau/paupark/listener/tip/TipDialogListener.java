package fr.univpau.paupark.listener.tip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.pojo.Tip;

public class TipDialogListener implements OnClickListener {
	private final Context context;
	private final Tip tip;
	private final List<Tip> tips;
	public TipDialogListener(Context context, Tip tip,List<Tip> tips) {
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

			alertDialogBuilder.setTitle(context.getString(R.string.note_title) + tip.getTitre());
			alertDialogBuilder
				.setCancelable(true)
				.setNegativeButton(context.getString(R.string.back_dialog), new RatingListener(context, tip, ratingBar,tips))
				.setNeutralButton(context.getString(R.string.note_dialog_ok), new RatingListener(context, tip, ratingBar, tips));

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
