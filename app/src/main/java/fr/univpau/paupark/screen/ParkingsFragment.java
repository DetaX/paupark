package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

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
import fr.univpau.paupark.presenter.ParkingFilter;

public class ParkingsFragment extends Fragment {
    private ArrayList<Parking> parkings;
    private ArrayList<Parking> filteredList;
    public static boolean firstTime;
    private ViewGroup container;
    private LocationManager locationManager;
    private CustomLocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
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
        if (parkings.size() > 0) {
            int size = (Settings.PREFERENCE.getBoolean(Settings.PAGINATION_SETTING_KEY, false)) ? Settings.PAGINATION_MAX_PARKINGS : parkings.size();
            Vector<View> pages = new Vector<View>();
            Log.i("size", String.valueOf(parkings.size()));
            for (int i = 0; i <= (parkings.size() / size); i++) {
                List<Parking> sublist = parkings.subList(i * size, Math.min(parkings.size(), size + i * size));
                if (sublist.size() > 0) {
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
            filteredList = parkings;
        }
        else {
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(new Vector<View>());
            vp.setAdapter(pager_adapter);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem add = menu.findItem(R.id.add);
        add.setVisible(false);
        //SubMenu menuFilter = menu.addSubMenu(0, 5, 1, null);
        final MenuItem filterMenu = menu.findItem(R.id.filtermenu);
        inflater.inflate(R.menu.filter, filterMenu.getSubMenu());
        MenuItem nom = filterMenu.getSubMenu().findItem(R.id.nomMenu);
        nom.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SearchView searchFilter = new SearchView(getActivity());
                searchFilter.setIconifiedByDefault(false);
                getActivity().getActionBar().setCustomView(searchFilter);
                getActivity().getActionBar().setDisplayShowCustomEnabled(true);
                return false;
            }
        });
        MenuItem prix = filterMenu.getSubMenu().findItem(R.id.prixMenu);
        prix.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().getActionBar().setCustomView(R.layout.price_filter);
                getActivity().getActionBar().setDisplayShowCustomEnabled(true);
                RadioGroup radiogroup = (RadioGroup) getActivity().findViewById(R.id.prix);
                if (ParkingFilter.priceFilter) {
                    RadioButton radio;
                    if (ParkingFilter.free)
                        radio = (RadioButton) getActivity().findViewById(R.id.free);
                     else
                        radio = (RadioButton) getActivity().findViewById(R.id.paying);
                    radio.setChecked(true);
                }
                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        ParkingFilter.priceFilter = true;
                        if (i == R.id.paying) {
                            ParkingFilter.free = false;
                            makePages(ParkingFilter.filter(parkings));
                        } else if (i == R.id.free) {
                            ParkingFilter.free = true;
                            makePages(ParkingFilter.filter(parkings));
                        } else {
                            ParkingFilter.priceFilter = false;
                            makePages(ParkingFilter.filter(parkings));
                        }
                    }
                });
                return false;
            }
        });
        MenuItem ouvrage = filterMenu.getSubMenu().findItem(R.id.ouvrageMenu);
        ouvrage.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().getActionBar().setCustomView(R.layout.ouvrage_filter);
                getActivity().getActionBar().setDisplayShowCustomEnabled(true);
                RadioGroup radiogroup = (RadioGroup) getActivity().findViewById(R.id.ouvrage);
                if (ParkingFilter.ouvrageFilter) {
                    RadioButton radio;
                    if (ParkingFilter.underground)
                        radio = (RadioButton) getActivity().findViewById(R.id.underground);
                    else
                        radio = (RadioButton) getActivity().findViewById(R.id.outdoor);
                    radio.setChecked(true);
                }
                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        ParkingFilter.ouvrageFilter = true;
                        if (i == R.id.underground) {
                            ParkingFilter.underground = true;
                            makePages(ParkingFilter.filter(parkings));
                        } else if (i == R.id.outdoor) {
                            ParkingFilter.underground = false;
                            makePages(ParkingFilter.filter(parkings));
                        } else {
                            ParkingFilter.ouvrageFilter = false;
                            makePages(ParkingFilter.filter(parkings));
                        }
                    }
                });
                return false;
            }
        });

        MenuItem places = filterMenu.getSubMenu().findItem(R.id.placesMenu);
        places.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().getActionBar().setCustomView(R.layout.places_filter);
                getActivity().getActionBar().setDisplayShowCustomEnabled(true);
                final EditText editMinPlace = (EditText) getActivity().findViewById(R.id.editMinPlace);
                final EditText editMaxPlace = (EditText) getActivity().findViewById(R.id.editMaxPlace);
                editMinPlace.setText(String.valueOf(ParkingFilter.min));
                editMaxPlace.setText(String.valueOf(ParkingFilter.max));
                editMinPlace.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        int max = (editMaxPlace.getText().length() == 0) ? 0 : Integer.parseInt(editMaxPlace.getText().toString());
                        int min = (editable.length() == 0) ? 0 : Integer.parseInt(editable.toString());
                        if (min == 0 && max == 0)
                            ParkingFilter.placesFilter = false;
                        else
                            ParkingFilter.placesFilter = true;
                        ParkingFilter.min = min;
                        ParkingFilter.max = max;

                        makePages(ParkingFilter.filter(parkings));
                    }
                });
                editMaxPlace.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        int min = (editMinPlace.getText().length() == 0) ? 0 : Integer.parseInt(editMinPlace.getText().toString());
                        int max = (editable.length() == 0) ? 0 : Integer.parseInt(editable.toString());
                        if (min == 0 && max == 0)
                            ParkingFilter.placesFilter = false;
                        else
                            ParkingFilter.placesFilter = true;
                        ParkingFilter.min = min;
                        ParkingFilter.max = max;
                        makePages(ParkingFilter.filter(parkings));
                    }
                });
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public ArrayList<Parking> getParkings() {
        return parkings;
    }

    public void refresh() {
        parkings.clear();
        ParkingsTask parkingtask = new ParkingsTask(container.getContext(), parkings, this);
        parkingtask.execute();
    }
}

