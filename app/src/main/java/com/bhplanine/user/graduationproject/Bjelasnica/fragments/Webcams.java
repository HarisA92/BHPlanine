package com.bhplanine.user.graduationproject.Bjelasnica.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhplanine.user.graduationproject.R;

import java.util.Objects;


public class Webcams extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return Objects.requireNonNull(inflater).inflate(R.layout.fragment_webcams, container, false);
    }

}
