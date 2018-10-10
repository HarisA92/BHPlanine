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
import com.example.user.graduationproject.Bjelasnica.ZoomImageReport;
import com.example.user.graduationproject.R;
import com.squareup.picasso.Picasso;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.TableViewHolder>{
    private Context context;
    private Cursor cursor;
    public static final String IMAGE_ADAPTER = "imageAdapter";


    public DatabaseAdapter(Context mContext, Cursor mCursor){
        context = mContext;
        cursor = mCursor;
    }
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_table, parent, false);
        return new TableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.USERNAME));
        String commentBox = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.COMMENTBOX));
        final String image = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.IMAGE));
        String snow = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.SNOW));
        String surface = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.SURFACE));
        String date = cursor.getString(cursor.getColumnIndex(ReportTable.UserTable.COLUMN_TIMESTAMP));

        Uri uri = Uri.parse(image);
        holder.username.setText(name);
        holder.commentbox.setText(commentBox);
        Picasso.with(context).load(uri).fit().centerCrop().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ZoomImageReport.class);
                intent.putExtra(IMAGE_ADAPTER, image);
                context.startActivity(intent);
            }
        });
        holder.snow.setText(snow);
        holder.surface.setText(surface);
        //holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
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
