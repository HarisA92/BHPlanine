package com.bhplanine.user.graduationproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhplanine.user.graduationproject.R;

import java.util.Objects;


public class WebcamsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return Objects.requireNonNull(inflater).inflate(R.layout.fragment_webcams, container, false);
    }

}
