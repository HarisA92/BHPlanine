package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.graduationproject.Bjelasnica.Fragments.Gallery;
import com.example.user.graduationproject.Bjelasnica.Fragments.LiftTickets;
import com.example.user.graduationproject.Bjelasnica.Fragments.Report.Report;
import com.example.user.graduationproject.Bjelasnica.Fragments.TrailMap;
import com.example.user.graduationproject.Bjelasnica.Fragments.Weather;
import com.example.user.graduationproject.Bjelasnica.Fragments.Webcams;

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
