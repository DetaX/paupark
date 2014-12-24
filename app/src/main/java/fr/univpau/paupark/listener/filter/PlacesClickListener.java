package fr.univpau.paupark.listener.filter;

import android.view.MenuItem;
import android.widget.EditText;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

public class PlacesClickListener implements MenuItem.OnMenuItemClickListener {
    private final ParkingsFragment fragment;

    public PlacesClickListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        fragment.getActivity().getActionBar().setCustomView(R.layout.places_filter);
        fragment.getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        EditText editMinPlace = (EditText) fragment.getActivity().findViewById(R.id.editMinPlace);
        EditText editMaxPlace = (EditText) fragment.getActivity().findViewById(R.id.editMaxPlace);
        editMinPlace.setText(String.valueOf(ParkingFilter.min));
        editMaxPlace.setText(String.valueOf(ParkingFilter.max));
        editMinPlace.addTextChangedListener(new PlacesTextChangedListener(fragment, editMaxPlace, editMinPlace));
        editMaxPlace.addTextChangedListener(new PlacesTextChangedListener(fragment, editMaxPlace, editMinPlace));
        return false;
    }
}
