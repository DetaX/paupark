package fr.univpau.paupark.listener;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

public class TipClickListener implements OnItemClickListener {
	private ArrayList<Tip> tips;
	private Context context;

	public TipClickListener(ArrayList<Tip> tips, Context context) {
		this.tips = tips;
		this.context=context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Util.dialog(context, position, tips);
	}

}

