package com.bhplanine.user.graduationproject.Bjelasnica.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhplanine.user.graduationproject.Bjelasnica.fragments.Gallery;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.LiftTickets;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.report.Report;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.TrailMap;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.Weather;
import com.bhplanine.user.graduationproject.Bjelasnica.fragments.Webcams;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public ViewPagerAdapter(final FragmentManager fm, final int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Report();
            case 1:
                return new Weather();
            case 2:
                return new Webcams();
            case 3:
                return new TrailMap();
            case 4:
                return new LiftTickets();
            case 5:
                return new Gallery();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
