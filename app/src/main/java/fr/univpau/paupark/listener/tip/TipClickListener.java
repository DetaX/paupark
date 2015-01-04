package fr.univpau.paupark.listener.tip;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

public class TipClickListener implements OnItemClickListener {
	private final List<Tip> tips;
	private final Context context;

	public TipClickListener(List<Tip> tips, Context context) {
		this.tips = tips;
		this.context=context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Util.dialog_detail_tip(context, position, tips);
	}

}

