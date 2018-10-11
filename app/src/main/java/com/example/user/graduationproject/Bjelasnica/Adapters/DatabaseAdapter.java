package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.graduationproject.Bjelasnica.Database.ReportTable;
import com.example.user.graduationproject.Bjelasnica.Database.UserReport;
import com.example.user.graduationproject.Bjelasnica.ZoomImageReport;
import com.example.user.graduationproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.TableViewHolder>{
    private Context context;
    private List<UserReport> userReport;
    public static final String IMAGE_ADAPTER = "imageAdapter";


    public DatabaseAdapter(Context mContext, List<UserReport> mUserReport){
        context = mContext;
        userReport = mUserReport;
    }
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_table, parent, false);
        return new TableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {

        holder.username.setText(userReport.get(position).getUsername());
        holder.commentbox.setText(userReport.get(position).getCommentBox());
        /*Picasso.with(context).load(uri).fit().centerCrop().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ZoomImageReport.class);
                intent.putExtra(IMAGE_ADAPTER, image);
                context.startActivity(intent);
            }
        });*/
        holder.snow.setText(userReport.get(position).getSnow());
        holder.surface.setText(userReport.get(position).getSurface());
        holder.date.setText(userReport.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return userReport.size();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView commentbox;
        public ImageView imageView;
        public TextView snow;
        public TextView surface;
        public TextView date;


        public TableViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameReport);
            commentbox = itemView.findViewById(R.id.CommentBoxReport);
            imageView = itemView.findViewById(R.id.imageReport);
            snow = itemView.findViewById(R.id.snow);
            surface = itemView.findViewById(R.id.surface);
            date = itemView.findViewById(R.id.date);
        }
    }
}
