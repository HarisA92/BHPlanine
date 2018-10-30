package com.example.user.graduationproject.Bjelasnica.JSONWeather;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.graduationproject.Bjelasnica.JSONWeather.Model.WeatherDay;
import com.example.user.graduationproject.R;

import java.util.Date;
import java.util.List;

public class RecyclerWeather extends RecyclerView.Adapter<RecyclerWeather.WeatherViewHolder> {

    private List<WeatherDay> weatherDays;
    private Context context;
    private Typeface weatherFont;

    public RecyclerWeather(List<WeatherDay> WeatherDays, Context mContext, Typeface WeatherFont) {
        weatherDays = WeatherDays;
        context = mContext;
        weatherFont = WeatherFont;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.weather_day_list, parent, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDay weather = weatherDays.get(position);
        holder.weather_icon.setTypeface(weatherFont);
        int a = 0;
        switch (weather.getWeather().get(a).getIcon()) {
            case "01d":
                holder.weather_icon.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                holder.weather_icon.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                holder.weather_icon.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                holder.weather_icon.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                holder.weather_icon.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                holder.weather_icon.setText(R.string.wi_day_snow);
                break;
            case "01n":
                holder.weather_icon.setText(R.string.wi_night_clear);
                break;
            case "04d":
                holder.weather_icon.setText(R.string.wi_cloudy);
                break;
            case "04n":
                holder.weather_icon.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                holder.weather_icon.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                holder.weather_icon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                holder.weather_icon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                holder.weather_icon.setText(R.string.wi_night_rain);
                break;
            case "13n":
                holder.weather_icon.setText(R.string.wi_night_snow);
                break;
        }
        long timestamp = weather.getDt();
        Date expiry = new Date(timestamp * 1000);
        String date = expiry.toString();
        String[] parts = date.split(" ");
        String day = parts[0];
        String Date = parts[1];
        String dateInMonth = parts[2];
        holder.time.setText(day + " " + Date + " " + dateInMonth);
        String setDescription = weather.getWeather().get(a).getDescription();
        String getDescription = setDescription.substring(0, 1).toUpperCase() + setDescription.substring(1);
        holder.description.setText(getDescription);
        double temp = weather.getMain().getTemp();
        int temperature = (int) temp;
        String weather_temp = String.valueOf(temperature);
        holder.temp.setText(weather_temp + "°C");
        a++;
    }

    @Override
    public int getItemCount() {
        try {
            return weatherDays.size();
        } catch (java.lang.NullPointerException ignored) {
        }
        return 0;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView time;
        TextView weather_icon;
        TextView temp;

        WeatherViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            temp = itemView.findViewById(R.id.temperature);
            time = itemView.findViewById(R.id.dateAndTime);
            weather_icon = itemView.findViewById(R.id.weather_icon_text);
        }
    }
}
