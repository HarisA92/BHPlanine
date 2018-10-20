package com.example.user.graduationproject.Bjelasnica.Firebase;

import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHolder {

    public DatabaseReference getDatabaseReferenceForTicketPrice(){
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getTicketPriceList());
    }

    public DatabaseReference getDatabaseReferenceForReport() {
        return FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }

    public DatabaseReference getDatabaseReferenceForGallery(){
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getGallery());
    }

    public DatabaseReference getDatabaseReferenceForWebCams(){
        return FirebaseDatabase.getInstance().getReference(SkiResortHolder.getSkiResort().getLiveStream());
    }
}
