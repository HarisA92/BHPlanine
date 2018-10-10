package com.example.user.graduationproject.Bjelasnica.Fragments.Report;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Database.Database;
import com.example.user.graduationproject.Bjelasnica.Database.ReportTable;
import com.example.user.graduationproject.Bjelasnica.Adapters.DatabaseAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment{


    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report3, container, false);
        onReportClick(v);
        final ProgressBar mProgressCircle = v.findViewById(R.id.progress_circle);
        Database database = new Database(getContext());
        sqLiteDatabase = database.getWritableDatabase();

        InternetConnection connection = new InternetConnection();

        if(connection.getInternetConnection() == true){
            final List<Upload> mUploads = new ArrayList<>();
            final ImageReportAdapter mAdapter = new ImageReportAdapter(getContext(), mUploads);
            mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(setUpLayoutManager());
            mRecyclerView.setAdapter(mAdapter);
            getDatabaseReference().addValueEventListener(valueEventListener(mAdapter, mProgressCircle, mUploads));
            //readSpecificData();
        }
        else{
            Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            mProgressCircle.setVisibility(View.INVISIBLE);
            final DatabaseAdapter databaseAdapter = new DatabaseAdapter(getContext(), getAllData());
            recyclerView = v.findViewById(R.id.table_recycler_view);
            recyclerView.setLayoutManager(setUpLayoutManager());
            recyclerView.setAdapter(databaseAdapter);
            databaseAdapter.notifyDataSetChanged();
        }
        return v;
    }

    private ValueEventListener valueEventListener(final ImageReportAdapter mAdapter,
                                                  final ProgressBar mProgressCircle,
                                                  final List<Upload> mUploads) {
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);

                    /*String name = upload.getmKey();
                    String snow = upload.getmSnow();
                    String trail = upload.getmTrail();
                    String image = upload.getImageUrl();
                    String commentBox = upload.getName();

                    addDatainDatabase(name, commentBox, image, snow, trail);*/
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void addDatainDatabase(String name, String commentBox, String image, String snow, String surface){
        Context context = getContext();
        Database db = new Database(context);

        if(!db.CheckIsInDBorNot(name, commentBox, image, snow, surface)){
            boolean isInserted = db.insertDatainDB(name, commentBox,
                    image,
                    snow,
                    surface);
            if(isInserted == true)
                Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(), "Duplicate data", Toast.LENGTH_SHORT).show();

        }

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private DatabaseReference getDatabaseReference() {
        return FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }

    private void onReportClick(View v) {
        v.findViewById(R.id.reportBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), PopUp.class);
                startActivity(intent);
            }
        });
    }

    private LinearLayoutManager setUpLayoutManager() {
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        return mLayoutManager;
    }

    public Cursor getAllData(){
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + ReportTable.UserTable.TABLE_NAME, null);
        return cursor;
    }

}


