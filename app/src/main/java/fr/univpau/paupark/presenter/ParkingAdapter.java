package fr.univpau.paupark.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.univpau.paupark.pojo.Parking;

public class ParkingAdapter extends ArrayAdapter<Parking> {
	private final Context context;
	private List<Parking> parkings;
	
	@Override
	public int getCount() {
		if (parkings != null)
        return parkings.size();
        else return 0;
	}

	public ParkingAdapter(Context context, int resource, List<Parking> parkings) {
		super(context, resource);
		this.context = context;
		this.parkings = parkings;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row_view = convertView;
		
		if (row_view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row_view = inflater.inflate(fr.univpau.paupark.R.layout.parking_item, parent, false);
		}

		Parking parking = parkings.get(position);
		
		ImageView ouvrage = (ImageView) row_view.findViewById(fr.univpau.paupark.R.id.icon_ouvrage);
		ImageView prix = (ImageView) row_view.findViewById(fr.univpau.paupark.R.id.icon_prix);
		
		TextView nom = (TextView) row_view.findViewById(fr.univpau.paupark.R.id.nom);
		TextView commune_places = (TextView) row_view.findViewById(fr.univpau.paupark.R.id.commune_places);
		
		ouvrage.setImageResource(fr.univpau.paupark.R.drawable.ic_launcher);
		prix.setImageResource(fr.univpau.paupark.R.drawable.ic_launcher);
		nom.setText(parking.getNom());
		commune_places.setText(parking.getCommune() + " - " + parking.getPlaces() + " places");
		return row_view;
	}

}
