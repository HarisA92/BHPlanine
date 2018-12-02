package com.bhplanine.user.graduationproject.fragments.navigationFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhplanine.user.graduationproject.R;

import java.util.Objects;

public class NewsDrawerFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_drawer, container, false);
        Objects.requireNonNull(getActivity()).setTitle("News");

        return v;
    }


}
