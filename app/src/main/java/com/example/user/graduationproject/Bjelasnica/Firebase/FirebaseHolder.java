package com.example.user.graduationproject.Bjelasnica.Firebase;

import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.google.firebase.database.DatabaseReference;

public class FirebaseHolder {

    public DatabaseReference getDatabaseReferenceForWebcam() {
        return com.google.firebase.database.FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getLiveStream().getValue());
    }

    public DatabaseReference getDatabaseReferenceForReport() {
        return com.google.firebase.database.FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }
}
