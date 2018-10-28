package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.graduationproject.Bjelasnica.Fragments.Report.Report;
import com.example.user.graduationproject.Bjelasnica.Main;
import com.example.user.graduationproject.Bjelasnica.Utils.AllMountainInformationHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResort;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllMountainInformationHolder> allMountainInformationHolders;
    private static final String SARAJEVO = "Sarajevo";
    private static final String JAHORINA = "Jahorina";
    private static final String RAVNA_PLANINA = "Pale";
    private static final String IGMAN = "Hadzici";
    private static final String VLASIC = "Travnik";
    private static String BJELASNICA_WEB_CAMS = "Bjelasnica_livestream";
    private static String BJELASNICA_CJENOVNIK = "Bjelasnica_cjenovnik";
    private static String BJELASNICA_GALLERY = "Bjelasnica_Gallery";
    private static String BJELASNICA_TRAIL_MAP = "Bjelasnica_trail_map";
    private static String JAHORINA_WEB_CAMS = "Jahorina_livestream";
    private static String JAHORINA_CJENOVNIK = "Jahorina_cjenovnik";
    private static String JAHORINA_GALLERY = "Jahorina_Gallery";
    private static String JAHORINA_TRAIL_MAP = "Jahorina_trail_map";
    private static String RAVNAPLANINA_WEB_CAMS = "RavnaPlanina_livestream";
    private static String RAVNAPLANINA_CJENOVNIK = "RavnaPlanina_cjenovnik";
    private static String RAVNAPLANINA_GALLERY = "RavnaPlanina_Gallery";
    private static String RAVNAPLANINA_TRAIL_MAP = "RavnaPlanina_trail_map";
    private static String VLASIC_WEB_CAMS = "Vlasic_livestream";
    private static String VLASIC_CJENOVNIK = "Vlasic_cjenovnik";
    private static String VLASIC_GALLERY = "Vlasic_Gallery";
    private static String VLASIC_TRAIL_MAP = "Vlasic_trail_map";
    private static String IGMAN_WEB_CAMS = "Igman_livestream";
    private static String IGMAN_CJENOVNIK = "Igman_cjenovnik";
    private static String IGMAN_GALLERY = "Igman_Gallery";
    private static String IGMAN_TRAIL_MAP = "Igman_trail_map";

    public HomeAdapter(Context context, ArrayList<AllMountainInformationHolder> allMountainInformationHolders) {
        this.context = context;
        this.allMountainInformationHolders = allMountainInformationHolders;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recycler_view, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.ViewHolder holder, int position) {
        final AllMountainInformationHolder informationHolder = allMountainInformationHolders.get(position);
        holder.base_cm.setText(informationHolder.getBase_depth());
        holder.snowfall.setText(informationHolder.getRecent_snowfall());
        holder.trails_open.setText(informationHolder.getTrails_open());
        holder.lifts_open.setText(informationHolder.getLifts_open());
        holder.location_of_mountain.setText(informationHolder.getLocation_of_mountain());
        holder.name_of_mountain.setText(informationHolder.getName_of_mountain());

        holder.name_of_mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.name_of_mountain.getText().toString().equals("Jahorina")){
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.JAHORINA, JAHORINA, JAHORINA_WEB_CAMS, JAHORINA_CJENOVNIK, JAHORINA_GALLERY,JAHORINA_TRAIL_MAP));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
                else if(holder.name_of_mountain.getText().toString().equals("Bjelasnica")){
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.BJELASNICA, SARAJEVO, BJELASNICA_WEB_CAMS, BJELASNICA_CJENOVNIK, BJELASNICA_GALLERY,BJELASNICA_TRAIL_MAP));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
                else if(holder.name_of_mountain.getText().toString().equals("Ravna Planina")){
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.RAVNAPLANINA, RAVNA_PLANINA, RAVNAPLANINA_WEB_CAMS, RAVNAPLANINA_CJENOVNIK, RAVNAPLANINA_GALLERY, RAVNAPLANINA_TRAIL_MAP));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
                else if(holder.name_of_mountain.getText().toString().equals("Igman")){
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.IGMAN, IGMAN, IGMAN_WEB_CAMS, IGMAN_CJENOVNIK, IGMAN_GALLERY, IGMAN_TRAIL_MAP));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
                else if(holder.name_of_mountain.getText().toString().equals("Vlasic")){
                    Intent intent = new Intent(context, Main.class);
                    SkiResortHolder.setSkiResort(new SkiResort(Mountain.VLASIC, VLASIC, VLASIC_WEB_CAMS, VLASIC_CJENOVNIK, VLASIC_GALLERY, VLASIC_TRAIL_MAP));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        try{
            return allMountainInformationHolders.size();
        }catch (java.lang.NullPointerException e){}
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView base_cm, lifts_open, trails_open, snowfall, location_of_mountain;
        private Button name_of_mountain;

        public ViewHolder(View itemView) {
            super(itemView);
            base_cm = itemView.findViewById(R.id.base_cm);
            snowfall = itemView.findViewById(R.id.snowfall_cm);
            lifts_open = itemView.findViewById(R.id.lifts_number);
            trails_open = itemView.findViewById(R.id.trails_opened);
            location_of_mountain = itemView.findViewById(R.id.location_mountain);
            name_of_mountain = itemView.findViewById(R.id.name_of_mountain);
        }
    }
}
