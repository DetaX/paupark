package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.presenter.CustomPagerAdapter;
import fr.univpau.paupark.presenter.ParkingAdapter;

public class ParkingsFragment extends Fragment {
    private ArrayList<Parking> parkings;
    public static boolean firstTime;
    private ParkingAdapter adapter;
    private ViewGroup container;

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
        parkings = MainScreen.parkings;
        if (firstTime)
            refresh();
        else makePages();
    }

    public void makePages() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int size = (prefs.getBoolean("pagination", false)) ? Settings.PAGINATION_MAX_PARKINGS : parkings.size();
        Vector<View> pages = new Vector<View>();
        for (int i = 0;i<=parkings.size()/size;i++) {
            ListView listview = new ListView(getActivity());
            List<Parking> sublist = parkings.subList(i*size, Math.min(parkings.size(), size+i*size));
            ParkingAdapter adapter = new ParkingAdapter(getActivity(), fr.univpau.paupark.R.layout.parking_item, sublist);
            pages.add(listview);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new ParkingClickListener(parkings));
            if (size==parkings.size()) break;
        }
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
        CustomPagerAdapter pager_adapter = new CustomPagerAdapter(pages);
        vp.setAdapter(pager_adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem add = menu.findItem(R.id.add);
        add.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void refresh() {
        parkings.clear();
        ParkingsTask parkingtask=new ParkingsTask(container.getContext(), parkings,this);
        parkingtask.execute();
    }
}

