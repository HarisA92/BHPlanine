package com.bhplanine.user.graduationproject.utils;

import android.content.Context;

import com.bhplanine.user.graduationproject.models.SkiResortHolder;
import com.bhplanine.user.graduationproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHolder {


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

    public DatabaseReference getDatabaseReferenceForTrailMap() {
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getTrailMap());
    }

    public DatabaseReference getDatabseReferenceForMountainInformation() {
        return FirebaseDatabase.getInstance().getReference("MountainInformation");
    }

    public DatabaseReference getDatabaseReferenceForAccommodation(String title){
        return FirebaseDatabase.getInstance().getReference(title);
    }

}
