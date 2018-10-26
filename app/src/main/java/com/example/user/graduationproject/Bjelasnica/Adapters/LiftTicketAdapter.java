package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.graduationproject.Bjelasnica.Utils.LiftTicketHolder;
import com.example.user.graduationproject.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LiftTicketAdapter extends RecyclerView.Adapter<LiftTicketAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiftTicketHolder> list;

    @NonNull
    @Override
    public LiftTicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lift_ticket_price, parent, false);
        return new LiftTicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiftTicketAdapter.ViewHolder holder, int position) {
        LiftTicketHolder liftTicketsHolder = list.get(position);
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
