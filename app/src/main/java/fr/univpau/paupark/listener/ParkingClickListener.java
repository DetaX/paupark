package fr.univpau.paupark.listener;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import fr.univpau.paupark.pojo.Parking;

public class ParkingClickListener implements OnItemClickListener {
	private ArrayList<Parking> parkings;

	public ParkingClickListener(ArrayList<Parking> parkings) {
		this.parkings = parkings;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String uri = "geo:" + parkings.get(position).getCoord()[0] + "," + parkings.get(position).getCoord()[1] + "?q=" + parkings.get(position).getCoord()[0] + "," + parkings.get(position).getCoord()[1] + "(" + parkings.get(position).getNom() + ")";
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
		view.getContext().startActivity(intent);
	}

}

