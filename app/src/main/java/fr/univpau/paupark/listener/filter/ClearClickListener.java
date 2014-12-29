package fr.univpau.paupark.listener.filter;

import android.view.MenuItem;

import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

public class ClearClickListener implements MenuItem.OnMenuItemClickListener {
    private final ParkingsFragment fragment;

    public ClearClickListener(ParkingsFragment fragment) {
        this.fragment=fragment;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        fragment.getActivity().getActionBar().setDisplayShowCustomEnabled(false);
        ParkingFilter.nomFilter = false;
        ParkingFilter.nom = "";
        ParkingFilter.ouvrageFilter = false;
        ParkingFilter.placesFilter = false;
        ParkingFilter.min = 0;
        ParkingFilter.max = 0;
        ParkingFilter.priceFilter = false;
        fragment.makePages(null);
        return false;
    }
}
