package com.example.user.graduationproject.Bjelasnica.Firebase;

import android.content.Context;

import com.example.user.graduationproject.Bjelasnica.utils.SkiResortHolder;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHolder {

    private Context context;

    public FirebaseHolder(Context context) {
        this.context = context;
    }

    public DatabaseReference getDatabaseReferenceForTicketPrice() {
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getTicketPriceList());
    }

    public DatabaseReference getDatabaseReferenceForReport() {
        return FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }

    public DatabaseReference getDatabaseReferenceForGallery() {
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getGallery());
    }

    public DatabaseReference getDatabaseReferenceForWebCams() {
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getLiveStream());
    }

    public DatabaseReference getDatabaseReferenceForTrailMap() {
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getTrailMap());
    }

    public DatabaseReference getDatabseReferenceForMountainInformation() {
        return FirebaseDatabase.getInstance().getReference(context.getResources().getString(R.string.mountain_information));
    }


}
