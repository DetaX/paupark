package fr.univpau.paupark.screen;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.TipsTask;
import fr.univpau.paupark.listener.TipClickListener;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.presenter.TipAdapter;

public class TipsFragment extends Fragment {
	private ArrayList<Tip> tips;
	private ViewGroup container;
	public static boolean firstTime;
	private TipAdapter adapter;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		this.container=container;
		
        return inflater.inflate(R.layout.tips_fragment, container, false);
    }
	
	@Override
	public void onStart() {
		super.onStart();
		tips = MainScreen.tips;
		ListView list = (ListView)container.findViewById(R.id.list_tip);
	    adapter = new TipAdapter(container.getContext(), fr.univpau.paupark.R.layout.tip_item, tips);
	    list.setAdapter(adapter);  
	    list.setOnItemClickListener(new TipClickListener(tips, container.getContext()));
	    
	    if(!TipsFragment.firstTime){
		    refresh();
	    }
	}
	public void refresh() {
		tips.clear();
		TipsTask tiptask=new TipsTask(container.getContext(), adapter,tips);
		tiptask.execute();
	}
	
}
