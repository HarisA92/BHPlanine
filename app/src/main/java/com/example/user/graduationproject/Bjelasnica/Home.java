package com.example.user.graduationproject.Bjelasnica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResort;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.LiveStream;
import com.example.user.graduationproject.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String SARAJEVO = "Sarajevo";
    private static final String JAHORINA = "Jahorina";
    private static final String JAHORINAWEBCAMS = "Bjelasnica";
    private static final String BJELASNICAWEBCAMS = "Jahorina";
    private static String BJELASNICA_TEXT = "Bjelašnica je planina u centralnom dijelu Bosne i Hercegovine, pripada dinarskom planinskom sistemu. Susjedne planine su joj Igman sa sjeverne strane, koji se praktično naslanja na Bjelašnicu, te Treskavica i Visočica. Bjelašnica je prekrivena snijegom od novembra do maja, a nekada i u ljetnim mjesecima, i otud dolazi objašnjenje za njeno ime.";
    private static String JAHORINA_TEXT = "Jahorina je planina u Bosni i Hercegovini koja pripada Dinarskom planinskom sustavu. Najviši vrh je Ogorjelica sa 1.916 m nadmorske visine. Ljeti je prekrivena gustom zelenom travom, a zimi i do 3 m visokim snijegom. Izvanredna konfiguracija terena, obilje vrlo kvalitetnog snijega, pogodna klima, 20 kilometara staza za alpske discipline kao i blage padine (Rajska dolina) uvrstile su ovu planinu među najljepše i najpoznatije ski-centre.";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private WelcomeScreen welcomeScreen;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button MyButton = findViewById(R.id.bjelasnica);
        MyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.BJELASNICA, SARAJEVO, LiveStream.BJELASNICA_WEB_CAMS));
                startActivity(i);
            }
        });
        Button jahorinaButton = findViewById(R.id.jahorina);
        jahorinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.JAHORINA, JAHORINA, LiveStream.JAHORINA_WEB_CAMS));
                startActivity(i);
            }
        });
        TextView textBjelasnica = findViewById(R.id.bjelasnicaText);
        textBjelasnica.setText(BJELASNICA_TEXT);
        TextView textJahorina = findViewById(R.id.jahorinaText);
        textJahorina.setText(JAHORINA_TEXT);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        setupFirebaseListener();
    }

    private void setupFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //do nothing
                }
                else{
                    Toast.makeText(Home.this, "Signed out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, WelcomeScreen.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener((FirebaseAuth.AuthStateListener) mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener((FirebaseAuth.AuthStateListener) mAuthStateListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_hotel:
                break;
            case R.id.nav_mountains:
                break;
            case R.id.nav_stream:
                break;
            case R.id.nav_weather:
                break;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean isLoggedInOnFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
