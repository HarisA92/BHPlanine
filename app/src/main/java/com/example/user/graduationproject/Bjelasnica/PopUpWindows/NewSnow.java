package com.example.user.graduationproject.Bjelasnica.PopUpWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.graduationproject.Bjelasnica.Fragments.Report.PopUp;
import com.example.user.graduationproject.R;

public class NewSnow extends AppCompatActivity {
    public static final String[] items = {" - ", "1cm", "2cm", "3cm", "4cm", "5cm", "10cm", "15cm", "20cm", "25cm", "30cm", "35cm", "40cm", "45cm", "50cm"};

    private ListView newSnowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_snow);
        onCreate();
        newSnowList = findViewById(R.id.newSnowList);
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //final SharedPreferences.Editor editor = prefs.edit();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * .8));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        newSnowList.setAdapter(arrayAdapter);
        newSnowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //editor.putString("snow", ITEMS[position]);
                //editor.commit();
                Intent intent = new Intent(NewSnow.this, PopUp.class);
                intent.putExtra("new_value_snow", items[position]);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onCreate() {
        getSupportActionBar().hide();
    }
}
