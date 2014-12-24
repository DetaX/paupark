package fr.univpau.paupark.listener.filter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import fr.univpau.paupark.presenter.ParkingFilter;
import fr.univpau.paupark.screen.ParkingsFragment;

class PlacesTextChangedListener implements TextWatcher {
    private final ParkingsFragment fragment;
    private final EditText editMaxPlace;
    private final EditText editMinPlace;

    public PlacesTextChangedListener(ParkingsFragment fragment, EditText editMaxPlace, EditText editMinPlace) {
        this.fragment = fragment;
        this.editMaxPlace = editMaxPlace;
        this.editMinPlace = editMinPlace;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int max = (editMaxPlace.getText().length() == 0) ? 0 : Integer.parseInt(editMaxPlace.getText().toString());
        int min = (editMinPlace.getText().length() == 0) ? 0 : Integer.parseInt(editMinPlace.getText().toString());
        ParkingFilter.placesFilter = (min != 0 || max != 0);
        ParkingFilter.min = min;
        ParkingFilter.max = max;
        fragment.makePages(null);
    }
}
