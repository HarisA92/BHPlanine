package com.bhplanine.user.graduationproject.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.MountainsDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WeatherDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WebcamsDrawerFragment;
import com.bhplanine.user.graduationproject.utils.FragmentNavigationManager;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.utils.NavigationManager;
import com.bhplanine.user.graduationproject.utils.SelectedFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.perf.metrics.AddTrace;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SelectedFragment {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView username, email;
    private ImageView imageView;
    private NavigationManager navigationManager;
    private ActionBarDrawerToggle toggle;
    private String[] TAG = {"Mountains", "Weather", "Webcams"};

    @AddTrace(name = "onCreateHomeActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationManager = FragmentNavigationManager.obtain(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MountainsDrawerFragment(), TAG[0]).commit();
        }
        setDataToNavigationLayout();
        getUsernameAndEmail();
        setupFirebaseListener();
        hideItem();
    }

    @Override
    public void selectDrawerFragment(int fragmentId) {
        navigationView.setCheckedItem(fragmentId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String[] mountain = {"Bjelašnica", "Jahorina", "Ravna Planina", "Vlašić", "Igman"};

        switch (item.getItemId()) {
            case R.id.nav_mountains:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MountainsDrawerFragment(), TAG[0]).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeatherDrawerFragment(), TAG[1]).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_hotel:
                showItem();
                break;
            case R.id.nav_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WebcamsDrawerFragment(), TAG[2]).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_send:
                sendEmail();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }

        if (item.getItemId() == R.id.bjelasnica) {
            navigationManager.showFragmentAccommodation(mountain[0]);
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        } else if (item.getItemId() == R.id.jahorina) {
            navigationManager.showFragmentAccommodation(mountain[1]);
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        } else if (item.getItemId() == R.id.ravnaplanina) {
            navigationManager.showFragmentAccommodation(mountain[2]);
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        } else if (item.getItemId() == R.id.vlasic) {
            navigationManager.showFragmentAccommodation(mountain[3]);
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        } else if (item.getItemId() == R.id.igman) {
            navigationManager.showFragmentAccommodation(mountain[4]);
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        }
        getMountainFragment();
        super.onBackPressed();
    }

    private void getMountainFragment() {
        MountainsDrawerFragment mountainsDrawerFragment = (MountainsDrawerFragment)
                getSupportFragmentManager().findFragmentByTag("Mountains");
        if (mountainsDrawerFragment.isVisible()) {
            finishAffinity();
        }
    }

    private void setDataToNavigationLayout() {
        View view = navigationView.getHeaderView(0);
        username = view.findViewById(R.id.user_google_facebook);
        email = view.findViewById(R.id.email_google_facebook);
        imageView = view.findViewById(R.id.image_google_facebook);
    }

    private void getUsernameAndEmail() {
        String name = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Uri userImage = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        username.setText(name);
        email.setText(user_email);
        Glide.with(this).load(userImage).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    private void showItem() {
        Menu menu = navigationView.getMenu();
        boolean bjelasnica = !menu.findItem(R.id.bjelasnica).isVisible();
        menu.findItem(R.id.bjelasnica).setVisible(bjelasnica);
        boolean jahorina = !menu.findItem(R.id.jahorina).isVisible();
        menu.findItem(R.id.jahorina).setVisible(jahorina);
        boolean ravnaplanina = !menu.findItem(R.id.ravnaplanina).isVisible();
        menu.findItem(R.id.ravnaplanina).setVisible(ravnaplanina);
        boolean vlasic = !menu.findItem(R.id.vlasic).isVisible();
        menu.findItem(R.id.vlasic).setVisible(vlasic);
        boolean igman = !menu.findItem(R.id.igman).isVisible();
        menu.findItem(R.id.igman).setVisible(igman);
    }

    private void hideItem() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.bjelasnica).setVisible(false);
        menu.findItem(R.id.jahorina).setVisible(false);
        menu.findItem(R.id.ravnaplanina).setVisible(false);
        menu.findItem(R.id.vlasic).setVisible(false);
        menu.findItem(R.id.igman).setVisible(false);
    }

    private void sendEmail() {
        InternetConnection internetConnection = new InternetConnection(this);
        if (internetConnection.checkConnectivity()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "bhplaninesupp@gmail.com"));
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFirebaseListener() {
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                Toast.makeText(HomeActivity.this, getResources().getString(R.string.Sign_out), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);
            }
        };
    }

}
