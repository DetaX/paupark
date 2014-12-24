package fr.univpau.paupark.listener.filter;


import android.widget.RadioGroup;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

class PriceCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
    private final ParkingsFragment fragment;

    public PriceCheckedChangeListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        ParkingFilter.priceFilter = true;
        if (i == R.id.paying)
            ParkingFilter.free = false;
        else if (i == R.id.free)
            ParkingFilter.free = true;
        else
            ParkingFilter.priceFilter = false;
        fragment.makePages(null);
    }
}
