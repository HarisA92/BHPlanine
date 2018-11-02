package com.example.user.graduationproject.Bjelasnica.WebCams;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.example.user.graduationproject.R;

public class WebCam extends AppCompatActivity {

    private ProgressDialog pDialog;
    private VideoView videoview;
    private String bundle_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_cam);
        onCreate();
        fullScreen();

        FirebaseHolder firebaseHolder = new FirebaseHolder(this);
        InternetConnection internetConnection = new InternetConnection();
        videoview = (VideoView) findViewById(R.id.video);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bundle_url = extras.getString(getResources().getString(R.string.URL_LINK));
        }
        cam(bundle_url);
    }

    private void cam(String URL) {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Jahorina, Hotel Lavina");
        pDialog.setMessage("Please wait...");
        pDialog.show();
        try {
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoview);

            Uri video = Uri.parse(URL);
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

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void fullScreen() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
