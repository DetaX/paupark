package fr.univpau.paupark.listener.filter;


import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

public class OuvrageClickListener implements MenuItem.OnMenuItemClickListener{
    private final ParkingsFragment fragment;

    public OuvrageClickListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        fragment.getActivity().getActionBar().setCustomView(R.layout.ouvrage_filter);
        fragment.getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        RadioGroup radiogroup = (RadioGroup) fragment.getActivity().findViewById(R.id.ouvrage);
        if (ParkingFilter.ouvrageFilter) {
            RadioButton radio;
            if (ParkingFilter.underground)
                radio = (RadioButton) fragment.getActivity().findViewById(R.id.underground);
            else
                radio = (RadioButton) fragment.getActivity().findViewById(R.id.outdoor);
            radio.setChecked(true);
        }
        radiogroup.setOnCheckedChangeListener(new OuvrageCheckedChangeListener(fragment));
        return false;
    }
}
