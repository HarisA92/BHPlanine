package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Adapters.GalleryAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.ViewPager.ImagePopUp;
import com.example.user.graduationproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

import static com.facebook.share.internal.ShareConstants.IMAGE_URL;

public class Gallery extends Fragment {
    private InternetConnection internetConnection = new InternetConnection();
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GalleryAdapter galleryAdapter;
    private ImagePopUp imagePopUp = new ImagePopUp();
    private List<GalleryImageHolder> listImages;
    private ArrayList<String> imagesArrayList = new ArrayList<>();
    private Uri urid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_gallery, container, false);
        if(internetConnection.getInternetConnection() == true){
            buildRecyclerView(v);
            firebaseHolder.getDatabaseReferenceForGallery().addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String url = dataSnapshot.getValue(String.class);
                    imagesArrayList.add(url);
                    galleryAdapter.notifyDataSetChanged();
                    int a = 0;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "eror je: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
        }
        int a = 0;
        return v;
    }


    private void buildRecyclerView(View v){
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        //listImages = imagePopUp.getData(SkiResortHolder.getSkiResort().getMountain());

        galleryAdapter = new GalleryAdapter(imagesArrayList, getContext());
        recyclerView.setAdapter(galleryAdapter);
    }


}
