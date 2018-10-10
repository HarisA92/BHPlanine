package com.example.user.graduationproject.Bjelasnica.PopUpWindows;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.graduationproject.Bjelasnica.Fragments.Report.PopUp;
import com.example.user.graduationproject.R;

public class NewSurface extends AppCompatActivity {

    public static final String[] items = {" - ", "Powder", "Machine Groomed", "Hard Packed", "Machine Made", "Spring", "Wet", "Variable"};
    public static final String SURFACE = "surface";
    ListView newSurfaceList;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_surface);
        onCreate();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * .8));
        newSurfaceList = findViewById(R.id.newSurfaceList);
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //final SharedPreferences.Editor editor = prefs.edit();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        newSurfaceList.setAdapter(arrayAdapter);
        newSurfaceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //editor.putString(SURFACE, ITEMS[position]);
                //editor.commit();
                Intent intent = new Intent(NewSurface.this, PopUp.class);
                intent.putExtra("new_value_surface", items[position]);
                startActivity(intent);
                finish();
            }
        });
    }


    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
