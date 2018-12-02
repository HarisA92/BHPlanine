package com.bhplanine.user.graduationproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.models.AccommodationMountainHolder;

import java.util.List;

public class AccommodationMountainsAdapter extends RecyclerView.Adapter<AccommodationMountainsAdapter.ViewHolder> {

    private List<AccommodationMountainHolder> list;
    private Context context;

    public AccommodationMountainsAdapter(List<AccommodationMountainHolder> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AccommodationMountainsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.accomodation_mountain_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationMountainsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
