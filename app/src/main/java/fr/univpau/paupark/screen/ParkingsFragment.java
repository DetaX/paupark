package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.ParkingsTask;
import fr.univpau.paupark.listener.ParkingClickListener;
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.presenter.ParkingAdapter;

public class ParkingsFragment extends Fragment {
	private ArrayList<Parking> parkings;
	private ViewGroup container;
	public static boolean firstTime;
	private ParkingAdapter adapter;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		this.container=container;
        return inflater.inflate(R.layout.parkings_fragment, container, false);
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	    firstTime=true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem add = menu.findItem(R.id.add);
		add.setVisible(false);
	    super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onStart() {
		super.onStart();
		parkings = MainScreen.parkings;
		ListView list = (ListView)container.findViewById(R.id.list_parking);
	    adapter = new ParkingAdapter(container.getContext(), fr.univpau.paupark.R.layout.parking_item, parkings);
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new ParkingClickListener(parkings));
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity());
        if (prefs.getBoolean("gps", false)) {
            LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putBoolean("gps", locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
            prefEditor.commit();
        }
	    if(ParkingsFragment.firstTime){
		    refresh();
	    }
	    else {
	    	if (prefs.getBoolean("gps", false))
	    		adapter.getFilter().filter(String.valueOf(prefs.getInt("range", 2500)+100));
	    }
	}

	public void refresh() {
		parkings.clear();
		ParkingsTask parkingtask=new ParkingsTask(container.getContext(), adapter,parkings);
		parkingtask.execute();
	}
}

