package fr.univpau.paupark.presenter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.univpau.paupark.pojo.Tip;

public class TipAdapter extends ArrayAdapter<Tip> {

	private final Context context;
	private List<Tip> tips;
	
	@Override
	public int getCount() {
		return tips.size();		
	}

	public TipAdapter(Context context, int resource, List<Tip> tips) {
		super(context, resource);
		this.context = context;
		this.tips = tips;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row_view = convertView;
		
		if (row_view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row_view = inflater.inflate(fr.univpau.paupark.R.layout.tip_item, parent, false);
		}

		Tip tip = tips.get(position);

		ImageView ouvrage = (ImageView) row_view.findViewById(fr.univpau.paupark.R.id.icon_ouvrage);
		ImageView prix = (ImageView) row_view.findViewById(fr.univpau.paupark.R.id.icon_prix);
		
		TextView nom = (TextView) row_view.findViewById(fr.univpau.paupark.R.id.nom);
		TextView commune_places = (TextView) row_view.findViewById(fr.univpau.paupark.R.id.commune_places);
		
		ouvrage.setImageResource(fr.univpau.paupark.R.drawable.ic_launcher);
		prix.setImageResource(fr.univpau.paupark.R.drawable.ic_launcher);
		nom.setText(tip.getTitre());
		commune_places.setText(tip.getCommune() + " - " + tip.getCapacite() + " places estimées");
		return row_view;
	}

}
