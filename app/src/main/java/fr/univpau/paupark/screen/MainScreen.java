package fr.univpau.paupark.screen;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.TabListener;
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.pojo.Tip;


public class MainScreen extends Activity {
	public static ArrayList<Parking> parkings = new ArrayList<Parking>();
	public static ArrayList<Tip> tips = new ArrayList<Tip>();
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		actionBar = getActionBar();
		
		actionBar.setDisplayShowHomeEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab()
	                       .setText(R.string.parkings)
	                       .setTabListener(new TabListener<ParkingsFragment>(
	                               this, getString(R.string.parkings), ParkingsFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	                   .setText(R.string.tips)
	                   .setTabListener(new TabListener<TipsFragment>(
	                           this, getString(R.string.tips), TipsFragment.class));
	    actionBar.addTab(tab);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    // Handle item selection
	    switch (item.getItemId())
	    {

		    case R.id.refresh:
		    {
		        refresh(actionBar.getSelectedTab().getText().toString());
		        return true;
		    }
	
		    case R.id.settings:
		    {
		        Intent i = new Intent(this, Settings.class);
		        startActivity(i);
		        return true;
		    }
		    case R.id.add:
		    {
		        Intent i = new Intent(this, TipAddScreen.class);
		        startActivity(i);
		        return true;
		    }
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	
	void refresh(String tab) {
		if (tab == getString(R.string.parkings)) {
			ParkingsFragment fragment = (ParkingsFragment)getFragmentManager().findFragmentByTag(getString(R.string.parkings));
			fragment.refresh();
		}
		else {
			TipsFragment fragment = (TipsFragment)getFragmentManager().findFragmentByTag(getString(R.string.tips));
			fragment.refresh();
		}
	}
}
