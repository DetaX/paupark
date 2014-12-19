package fr.univpau.paupark.listener;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.RatingBar;
import fr.univpau.paupark.asynctask.RatingTask;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

public class RatingListener implements OnClickListener {
	private Context context;
	private Tip tip;
	private RatingBar note;
	private ArrayList<Tip> tips;
	public RatingListener(Context context, Tip tip, RatingBar note, ArrayList<Tip> tips) {
		this.context=context;
		this.tip=tip;
		this.note=note;
		this.tips=tips;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE)  {
			dialog.dismiss();
			Util.dialog(context,tip,tips);	
		}
		else {
			
			 RatingTask tiptask=new RatingTask(context, note.getRating(), tip.getId(),tips);
			 tiptask.execute();

		}

	}

}
