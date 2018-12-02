package com.bhplanine.user.graduationproject.fragments.navigationFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhplanine.user.graduationproject.R;

import java.util.Objects;

public class WebcamsDrawerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcams_drawer, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Webcams");
        return v;
    }

}
