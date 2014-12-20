package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.ParkingsTask;
import fr.univpau.paupark.listener.CustomLocationListener;
import fr.univpau.paupark.listener.ParkingClickListener;
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.presenter.CustomPagerAdapter;
import fr.univpau.paupark.presenter.ParkingAdapter;

public class ParkingsFragment extends Fragment {
    private ArrayList<Parking> parkings;
    public static boolean firstTime;
    private ViewGroup container;
    private LocationManager locationManager;
    private CustomLocationListener locationListener;
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
    public void onStart() {
        super.onStart();
        parkings = MainScreen.PARKINGS;
        if (firstTime)
            refresh();
        else makePages(parkings);
        if (locationManager == null)
            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (Settings.PREFERENCE.getBoolean(Settings.GEOLOCATION_SETTING_KEY, false)) {
            locationListener = new CustomLocationListener(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        else if (locationListener != null)
            locationManager.removeUpdates(locationListener);

    }

    public void makePages(ArrayList<Parking> parkings) {
        if (parkings.size() > 0) {
            int size = (Settings.PREFERENCE.getBoolean(Settings.PAGINATION_SETTING_KEY, false)) ? Settings.PAGINATION_MAX_PARKINGS : parkings.size();
            Vector<View> pages = new Vector<View>();
            Log.i("size", String.valueOf(parkings.size()));
            for (int i = 0; i <= (parkings.size() / size); i++) {
                List<Parking> sublist = parkings.subList(i * size, Math.min(parkings.size(), size + i * size));
                if (sublist.size() > 0 ) {
                    ListView listview = new ListView(getActivity());
                    ParkingAdapter adapter = new ParkingAdapter(getActivity(), fr.univpau.paupark.R.layout.parking_item, sublist);
                    pages.add(listview);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new ParkingClickListener(parkings));
                }
            }
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(pages);
            vp.setAdapter(pager_adapter);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem add = menu.findItem(R.id.add);
        add.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public ArrayList<Parking> getParkings() {
        return parkings;
    }
    public void refresh() {
        parkings.clear();
        ParkingsTask parkingtask=new ParkingsTask(container.getContext(), parkings,this);
        parkingtask.execute();
    }
}

