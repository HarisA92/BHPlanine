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
import android.widget.VideoView;

import com.example.user.graduationproject.R;

public class WebCam extends AppCompatActivity {
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
