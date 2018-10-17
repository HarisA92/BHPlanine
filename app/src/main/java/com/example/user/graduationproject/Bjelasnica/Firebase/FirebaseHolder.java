package com.example.user.graduationproject.Bjelasnica.Firebase;

import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHolder {

    public DatabaseReference getDatabaseReferenceForTicketPrice(){
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getTicketPriceList());
    }

    public DatabaseReference getDatabaseReferenceForReport() {
        return FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }
}
