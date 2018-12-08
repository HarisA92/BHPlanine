package com.bhplanine.user.graduationproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.activities.Main;
import com.bhplanine.user.graduationproject.models.AllMountainInformationHolder;
import com.bhplanine.user.graduationproject.models.Mountain;
import com.bhplanine.user.graduationproject.models.SkiResort;
import com.bhplanine.user.graduationproject.models.SkiResortHolder;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<AllMountainInformationHolder> allMountainInformationHolders;
    private Context context;

    public HomeAdapter(ArrayList<AllMountainInformationHolder> allMountainInformationHolders) {
        this.allMountainInformationHolders = allMountainInformationHolders;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.ViewHolder holder, int position) {
        context = holder.itemView.getContext();
        final AllMountainInformationHolder informationHolder = allMountainInformationHolders.get(position);
        holder.base_cm.setText(informationHolder.getBase_depth());
        holder.snowfall.setText(informationHolder.getRecent_snowfall());
        holder.trails_open.setText(informationHolder.getTrails_open());
        holder.lifts_open.setText(informationHolder.getLifts_open());
        holder.location_of_mountain.setText(informationHolder.getLocation_of_mountain());
        holder.name_of_mountain.setText(informationHolder.getName_of_mountain());
        holder.opening_date_of_mountain.setText(informationHolder.getOpening_date());
        holder.lifts_number_of.setText(informationHolder.getLifts_open_of());
        holder.trails_open_of.setText(informationHolder.getTrails_open_of());
    }

    @Override
    public int getItemCount() {
        return allMountainInformationHolders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView base_cm, lifts_open, trails_open, snowfall, location_of_mountain, name_of_mountain,
                opening_date_of_mountain, lifts_number_of, trails_open_of;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            base_cm = itemView.findViewById(R.id.base_cm);
            snowfall = itemView.findViewById(R.id.snowfall_cm);
            lifts_open = itemView.findViewById(R.id.lifts_number);
            lifts_number_of = itemView.findViewById(R.id.number_of_lifts);
            trails_open = itemView.findViewById(R.id.trails_opened);
            trails_open_of = itemView.findViewById(R.id.number_of_trails_open);
            location_of_mountain = itemView.findViewById(R.id.location_mountain);
            name_of_mountain = itemView.findViewById(R.id.name_of_mountain);
            opening_date_of_mountain = itemView.findViewById(R.id.opening_date);
        }

        @Override
        public void onClick(View view) {
            switch (getLayoutPosition()) {
                case 0: {
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.BJELASNICA, context.getResources().getString(R.string.BJELASNICA_WEATHER), context.getResources().getString(R.string.BJELASNICA_WEBCAMS), context.getResources().getString(R.string.BJELASNICA_PRICETICKETS), context.getResources().getString(R.string.BJELASNICA_GALLERY), context.getResources().getString(R.string.BJELASNICA_TRAIL_MAP)));
                    context.startActivity(intent);
                    break;
                }
                case 1: {
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.IGMAN, context.getResources().getString(R.string.IGMAN_WEATHER), context.getResources().getString(R.string.IGMAN_WEBCAMS), context.getResources().getString(R.string.IGMAN_PRICETICKETS), context.getResources().getString(R.string.IGMAN_GALLERY), context.getResources().getString(R.string.IGMAN_TRAIL_MAP)));
                    context.startActivity(intent);
                    break;
                }
                case 2: {
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.JAHORINA, context.getResources().getString(R.string.JAHORINA_WEATHER), context.getResources().getString(R.string.JAHORINA_WEBCAMS), context.getResources().getString(R.string.JAHORINA_PRICETICKETS), context.getResources().getString(R.string.JAHORINA_GALLERY), context.getResources().getString(R.string.JAHORINA_TRAIL_MAP)));
                    context.startActivity(intent);
                    break;
                }
                case 3: {
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.RAVNAPLANINA, context.getResources().getString(R.string.RAVNAPLANINA_WEATHER), context.getResources().getString(R.string.RAVNAPLANINA_WEBCAMS), context.getResources().getString(R.string.RAVNAPLANINA_PRICETICKETS), context.getResources().getString(R.string.RAVNAPLANINA_GALLERY), context.getResources().getString(R.string.RAVNAPLANINA_TRAIL_MAP)));
                    context.startActivity(intent);
                    break;
                }
                case 4: {
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.VLASIC, context.getResources().getString(R.string.VLASIC_WEATHER), context.getResources().getString(R.string.VLASIC_WEBCAMS), context.getResources().getString(R.string.VLASIC_PRICETICKETS), context.getResources().getString(R.string.VLASIC_GALLERY), context.getResources().getString(R.string.VLASIC_TRAIL_MAP)));
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }
}
