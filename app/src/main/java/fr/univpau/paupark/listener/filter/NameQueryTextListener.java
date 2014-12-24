package fr.univpau.paupark.listener.filter;

import android.widget.SearchView;

import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;


class NameQueryTextListener implements SearchView.OnQueryTextListener {
    private final ParkingsFragment fragment;
    private final SearchView searchFilter;

    public NameQueryTextListener(ParkingsFragment fragment, SearchView searchFilter) {
        this.fragment = fragment;
        this.searchFilter = searchFilter;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchFilter.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            ParkingFilter.nomFilter = true;
            ParkingFilter.nom = s;
        } else {
            ParkingFilter.nomFilter = false;
            ParkingFilter.nom = "";
        }
        fragment.makePages(null);
        return false;
    }
}
