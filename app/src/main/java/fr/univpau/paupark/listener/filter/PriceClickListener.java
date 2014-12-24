package fr.univpau.paupark.listener.filter;


import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

public class PriceClickListener implements MenuItem.OnMenuItemClickListener{
    private final ParkingsFragment fragment;

    public PriceClickListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        fragment.getActivity().getActionBar().setCustomView(R.layout.price_filter);
        fragment.getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        RadioGroup radiogroup = (RadioGroup) fragment.getActivity().findViewById(R.id.prix);
        if (ParkingFilter.priceFilter) {
            RadioButton radio;
            if (ParkingFilter.free)
                radio = (RadioButton) fragment.getActivity().findViewById(R.id.free);
            else
                radio = (RadioButton) fragment.getActivity().findViewById(R.id.paying);
            radio.setChecked(true);
        }
        radiogroup.setOnCheckedChangeListener(new PriceCheckedChangeListener(fragment));
        return false;
    }
}
