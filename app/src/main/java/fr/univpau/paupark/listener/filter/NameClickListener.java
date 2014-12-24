package fr.univpau.paupark.listener.filter;


import android.view.MenuItem;
import android.widget.SearchView;

import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

public class NameClickListener implements MenuItem.OnMenuItemClickListener{

    private final ParkingsFragment fragment;
    public NameClickListener(ParkingsFragment fragment) {
        this.fragment=fragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        SearchView searchFilter = new SearchView(fragment.getActivity());
        searchFilter.setIconifiedByDefault(false);
        fragment.getActivity().getActionBar().setCustomView(searchFilter);
        fragment.getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        searchFilter.setQuery(ParkingFilter.nom, false);
        searchFilter.setOnQueryTextListener(new NameQueryTextListener(fragment,searchFilter));
        return false;
    }
}
