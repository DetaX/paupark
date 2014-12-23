package fr.univpau.paupark.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

public class TipClickListener implements OnItemClickListener {
	private final ArrayList<Tip> tips;
	private final Context context;

	public TipClickListener(ArrayList<Tip> tips, Context context) {
		this.tips = tips;
		this.context=context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Util.dialog_detail_tip(context, position, tips);
	}

}

