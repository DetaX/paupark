package fr.univpau.paupark.screen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.TipsTask;
import fr.univpau.paupark.listener.tip.TipClickListener;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.presenter.CustomPagerAdapter;
import fr.univpau.paupark.presenter.TipAdapter;

public class TipsFragment extends Fragment {
	private ArrayList<Tip> tips;
	public static boolean firstTime;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tips_fragment, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstTime = true;
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        tips = MainScreen.TIPS;
        if (firstTime)
            refresh();
        else makePages(tips);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem filter = menu.findItem(R.id.filtermenu);
        filter.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void makePages(ArrayList<Tip> tips) {
        if (tips.size() > 0) {
            int size = (Settings.PREFERENCE.getBoolean(Settings.PAGINATION_SETTING_KEY, false)) ? Settings.PAGINATION_MAX_PARKINGS : tips.size();
            Vector<View> pages = new Vector<>();
            for (int i = 0; i <= (tips.size() / size); i++) {
                List<Tip> sublist = tips.subList(i * size, Math.min(tips.size(), size + i * size));
                if (sublist.size() > 0) {
                    ListView listview = new ListView(getActivity());
                    TipAdapter adapter = new TipAdapter(getActivity(), sublist);
                    pages.add(listview);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new TipClickListener(sublist,getActivity()));
                }
            }
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(pages);
            vp.setAdapter(pager_adapter);
        } else {
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.pager);
            CustomPagerAdapter pager_adapter = new CustomPagerAdapter(new Vector<View>());
            vp.setAdapter(pager_adapter);
        }
    }

    public ArrayList<Tip> getTips() {
        return tips;
    }

	public void refresh() {
		tips.clear();
		TipsTask tiptask=new TipsTask(this);
		tiptask.execute();
	}
	
}
