package fr.univpau.paupark.listener.tip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.RatingBar;

import java.util.List;

import fr.univpau.paupark.asynctask.RatingTask;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

class RatingListener implements OnClickListener {
	private final Context context;
	private final Tip tip;
	private final RatingBar note;
	private final List<Tip> tips;
	public RatingListener(Context context, Tip tip, RatingBar note, List<Tip> tips) {
		this.context=context;
		this.tip=tip;
		this.note=note;
		this.tips=tips;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEGATIVE)  {
			dialog.dismiss();
			Util.dialog_detail_tip(context, tip, tips);
		}
		else {
			
			 RatingTask tiptask=new RatingTask(context, note.getRating(), tip.getId(),tips);
			 tiptask.execute();

		}

	}

}
