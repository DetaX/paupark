package fr.univpau.paupark.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.paupark.pojo.Parking;

public class ParkingAdapter extends ArrayAdapter<Parking> implements Filterable{

	private final Context context;
	private List<Parking> parkings;
	private Filter customFilter;
	
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
	
	@Override
	public Filter getFilter(){
		if(customFilter == null){
			customFilter = new seekBarFilter();
		}
		return customFilter; 
				
	}
	
	private class seekBarFilter extends Filter{
		double longitude;
		double latitude;
		
		private final LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		        longitude = location.getLongitude();
		        latitude = location.getLatitude();

		    }

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
		};
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults res = new FilterResults();
			int range = 2500;
			try {
				range = Integer.parseInt(constraint.toString());
			}
			catch(NumberFormatException e) {
			}
			LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE); 
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
				float results[] = new float[1];
				ArrayList<Parking> p = new ArrayList<Parking>();
				for(Parking pk:parkings){
					Location.distanceBetween(latitude, longitude, pk.getCoord()[0], pk.getCoord()[1], results);
					Log.i("distance", String.valueOf(results[0]));
					if(results[0] < range) {
						p.add(pk);
					}
				}
			
			res.values = p;
			res.count = p.size();
			}
			return res;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// Now we have to inform the adapter about the new list filtered
			parkings = (ArrayList<Parking>) results.values;
			if (results.count == 0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder
					.setMessage("Il n'y a pas de parking à afficher selon vos préférences. Veuillez augmenter la distance maximum de géolocalisation")
					.setCancelable(true);
				
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					alertDialog.show();
			}
	        notifyDataSetChanged();
			
		}
		
	}

}
