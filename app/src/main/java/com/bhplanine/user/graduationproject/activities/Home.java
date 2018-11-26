package com.bhplanine.user.graduationproject.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.AccommodationDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.MountainsDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WeatherDrawerFragment;
import com.bhplanine.user.graduationproject.fragments.navigationFragments.WebcamsDrawerFragment;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MountainsDrawerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mountains);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mountains:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MountainsDrawerFragment()).commit();
                break;
            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeatherDrawerFragment()).commit();
                break;
            case R.id.nav_hotel:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccommodationDrawerFragment()).commit();
                break;
            case R.id.nav_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WebcamsDrawerFragment()).commit();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_out:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
            super.onBackPressed();
        }
    }
}
