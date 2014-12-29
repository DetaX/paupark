package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import fr.univpau.paupark.listener.ParkingClickListener;
import fr.univpau.paupark.listener.filter.ClearClickListener;
import fr.univpau.paupark.listener.filter.NameClickListener;
import fr.univpau.paupark.listener.filter.OuvrageClickListener;
import fr.univpau.paupark.listener.filter.PlacesClickListener;
import fr.univpau.paupark.listener.filter.PriceClickListener;
import fr.univpau.paupark.listener.gps.CustomLocationListener;
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.presenter.CustomPagerAdapter;
import fr.univpau.paupark.presenter.ParkingAdapter;
import fr.univpau.paupark.presenter.ParkingFilter;

public class ParkingsFragment extends Fragment {
    private ArrayList<Parking> parkings;
    public static boolean firstTime;
    private LocationManager locationManager;
    private CustomLocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.parkings_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firstTime = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        parkings = MainScreen.PARKINGS;
        if (firstTime)
            refresh();
        else makePages(parkings);
        if (locationManager == null)
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (Settings.PREFERENCE.getBoolean(Settings.GEOLOCATION_SETTING_KEY, false)) {
            locationListener = new CustomLocationListener(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        } else if (locationListener != null)
            locationManager.removeUpdates(locationListener);
    }

    public void makePages(ArrayList<Parking> parkings) {
        if (parkings == null) parkings = this.parkings;
        if (parkings.size() > 0) {
            parkings = ParkingFilter.filter(parkings);
            int size = (Settings.PREFERENCE.getBoolean(Settings.PAGINATION_SETTING_KEY, false)) ? Settings.PAGINATION_MAX_PARKINGS : parkings.size();
            Vector<View> pages = new Vector<>();
            for (int i = 0; i <= (parkings.size() / size); i++) {
                List<Parking> sublist = parkings.subList(i * size, Math.min(parkings.size(), size + i * size));
                if (sublist.size() > 0) {
                    ListView listview = new ListView(getActivity());
                    ParkingAdapter adapter = new ParkingAdapter(getActivity(), sublist);
                    pages.add(listview);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new ParkingClickListener(sublist));
                }
            }
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(pages);
            vp.setAdapter(pager_adapter);
        } else {
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(new Vector<View>());
            vp.setAdapter(pager_adapter);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem add = menu.findItem(R.id.add);
        add.setVisible(false);
        // inflate filter menu
        final MenuItem filterMenu = menu.findItem(R.id.filtermenu);
        inflater.inflate(R.menu.filter, filterMenu.getSubMenu());
        // filter by name
        MenuItem name = filterMenu.getSubMenu().findItem(R.id.nomMenu);
        name.setOnMenuItemClickListener(new NameClickListener(this));
        //filter by price
        MenuItem price = filterMenu.getSubMenu().findItem(R.id.prixMenu);
        price.setOnMenuItemClickListener(new PriceClickListener(this));
        //filter by ouvrage
        MenuItem ouvrage = filterMenu.getSubMenu().findItem(R.id.ouvrageMenu);
        ouvrage.setOnMenuItemClickListener(new OuvrageClickListener(this));
        //filter by places
        MenuItem places = filterMenu.getSubMenu().findItem(R.id.placesMenu);
        places.setOnMenuItemClickListener(new PlacesClickListener(this));
        // clear filter
        MenuItem clear = filterMenu.getSubMenu().findItem(R.id.clearMenu);
        clear.setOnMenuItemClickListener(new ClearClickListener(this));

        super.onCreateOptionsMenu(menu, inflater);
    }

    public ArrayList<Parking> getParkings() {
        return parkings;
    }

    public void refresh() {
        parkings.clear();
        ParkingsTask parkingtask = new ParkingsTask(this);
        parkingtask.execute();
    }
}

