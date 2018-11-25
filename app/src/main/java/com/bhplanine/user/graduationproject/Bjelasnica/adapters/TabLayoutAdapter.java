package com.bhplanine.user.graduationproject.Bjelasnica.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhplanine.user.graduationproject.Bjelasnica.fragments.GalleryFragment;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.LiftTicketsFragment;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.ReportFragment;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.TrailMapFragment;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.WeatherFragment;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.WebcamsFragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabLayoutAdapter(final FragmentManager fm, final int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReportFragment();
            case 1:
                return new WeatherFragment();
            case 2:
                return new WebcamsFragment();
            case 3:
                return new TrailMapFragment();
            case 4:
                return new LiftTicketsFragment();
            case 5:
                return new GalleryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
