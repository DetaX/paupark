package fr.univpau.paupark.listener.filter;

import android.widget.RadioGroup;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

class OuvrageCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
    private final ParkingsFragment fragment;

    public OuvrageCheckedChangeListener(ParkingsFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        ParkingFilter.ouvrageFilter = true;
        if (i == R.id.underground)
            ParkingFilter.underground = true;
        else if (i == R.id.outdoor)
            ParkingFilter.underground = false;
        else
            ParkingFilter.ouvrageFilter = false;
        fragment.makePages(null);
    }
}
