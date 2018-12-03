package com.bhplanine.user.graduationproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.models.AccommodationHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {

    private ArrayList<AccommodationHolder> accommodationList;
    private Context context;

    public AccommodationAdapter(ArrayList<AccommodationHolder> list, Context context) {
        this.accommodationList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AccommodationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.accomodation_mountain_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationAdapter.ViewHolder holder, int position) {
        final AccommodationHolder accommodationHolder = accommodationList.get(position);
        Glide.with(context).load(accommodationHolder.getImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
        holder.title.setText(accommodationHolder.getTitle());
        holder.overview.setText(accommodationHolder.getOverview());
        String website = accommodationHolder.getWebsite();
        String URL = "<a href=" + website + ">" + "Saznaj više" + "</a>";
        holder.website.setMovementMethod(LinkMovementMethod.getInstance());
        holder.website.setText(Html.fromHtml(URL));
        holder.website.setClickable(true);

    }

    @Override
    public int getItemCount() {
        return accommodationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, overview;
        private Button website, reservation;
        private ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_accommodation);
            overview = itemView.findViewById(R.id.text_accommodation);
            image = itemView.findViewById(R.id.image_accommodation);
            website = itemView.findViewById(R.id.website_accommodation);
            reservation = itemView.findViewById(R.id.reservation_accommodation);
        }
    }
}
