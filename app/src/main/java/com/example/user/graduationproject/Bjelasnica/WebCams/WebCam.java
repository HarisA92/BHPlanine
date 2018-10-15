package com.example.user.graduationproject.Bjelasnica.WebCams;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.Bjelasnica.Utils.WebCamLinks;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WebCam extends AppCompatActivity {
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private InternetConnection internetConnection = new InternetConnection();
    ProgressDialog pDialog;
    VideoView videoview;
    String videoURL = "http_for_security_reason_I_have_to_delete_part_of_this_link!Jahorina%2FJahorina_Hotel_Lavina.stream_720p%2Fplaylist.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_cam);
        onCreate();
        fullScreen();
        videoview = (VideoView) findViewById(R.id.video);
        if(internetConnection != null){
            cam();
            firebaseHolder.getDatabaseReferenceForWebcam();
        }
        else{
            Toast.makeText(this, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "RECI NESTO", Toast.LENGTH_SHORT).show();
        int a = 0;

    }

    private void cam(){
        Uri uri = Uri.parse(videoURL);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Jahorina, Hotel Lavina");
        pDialog.setMessage("Please wait...");
        pDialog.show();
        try {
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoview);

            Uri video = Uri.parse(videoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                mp.setLooping(true);
                videoview.start();
            }
        });
    }
    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //WebCamLinks webCamLinks = postSnapshot.getValue(WebCamLinks.class);
                    WebCamLinks webCamLinks = postSnapshot.getValue(WebCamLinks.class);
                    String webcam = webCamLinks.getJahorinaLavina();
                    Toast.makeText(WebCam.this, "ovo je: " + webcam, Toast.LENGTH_SHORT).show();

                    int a = 0;

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }



    public void onCreate(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void fullScreen() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;

        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
        } else { }
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
