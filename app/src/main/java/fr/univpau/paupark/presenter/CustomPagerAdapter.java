package fr.univpau.paupark.presenter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

public class CustomPagerAdapter extends PagerAdapter {

    private final Vector<View> pages;

    public CustomPagerAdapter(Vector<View> pages) {
        this.pages=pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = pages.get(position);
        container.addView(page);
        return page;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

