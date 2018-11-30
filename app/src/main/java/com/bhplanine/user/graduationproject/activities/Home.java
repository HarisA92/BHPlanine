package com.bhplanine.user.graduationproject.activities;

import android.content.Intent;
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
import com.bhplanine.user.graduationproject.fragments.navigationFragments.AccommodationDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.MountainsDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.NewsDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WeatherDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WebcamsDrawerFragment;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private InternetConnection internetConnection = new InternetConnection();
    private TextView username, email;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MountainsDrawerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mountains);
        }

        View view = navigationView.getHeaderView(0);
        username = view.findViewById(R.id.user_google_facebook);
        email = view.findViewById(R.id.email_google_facebook);
        imageView = view.findViewById(R.id.image_google_facebook);
        getUsernameAndEmail();
        setupFirebaseListener();
        hideItem();
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mountains:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MountainsDrawerFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeatherDrawerFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_hotel:
                showItem();
                break;
            case R.id.nav_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WebcamsDrawerFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewsDrawerFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccommodationDrawerFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);
            hideItem();
        } else if (item.getItemId() == R.id.jahorina) {

        } else if (item.getItemId() == R.id.ravnaplanina) {

        } else if (item.getItemId() == R.id.vlasic) {

        } else if (item.getItemId() == R.id.igman) {

        }
        return true;
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
        } else {
            finishAffinity();
            super.onBackPressed();
        }
    }

    private void sendEmail() {
        if (internetConnection.getInternetConnection()) {
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
                Toast.makeText(Home.this, getResources().getString(R.string.Sign_out), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, WelcomeScreen.class);
                startActivity(intent);
            }
        };
    }
}
