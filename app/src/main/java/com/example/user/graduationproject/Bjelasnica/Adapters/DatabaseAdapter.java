package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.user.graduationproject.Bjelasnica.Database.WeatherTable;
import com.example.user.graduationproject.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.TableViewHolder>{
    private List<WeatherTable> list = new ArrayList<>();


    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_day_list, parent, false);
        return new TableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        WeatherTable weatherTable = list.get(position);
        holder.description.setText(weatherTable.getDescription());
        holder.temperature.setText(weatherTable.getTemperature());
        holder.time.setText(weatherTable.getTime());
        holder.weather_icon.setText(weatherTable.getWeather_icon());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setUserReport(List<WeatherTable> weatherList){
        this.list = weatherList;
        notifyDataSetChanged();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder{
        public TextView description;
        public TextView temperature;
        public TextView time;
        public TextView weather_icon;



        public TableViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            temperature = itemView.findViewById(R.id.temperature);
            time = itemView.findViewById(R.id.dateAndTime);
            weather_icon = itemView.findViewById(R.id.weather_icon_text);
        }
    }
}
