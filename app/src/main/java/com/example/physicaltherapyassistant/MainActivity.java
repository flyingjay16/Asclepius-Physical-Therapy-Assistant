package com.example.physicaltherapyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNav;
    public static HomeFragment homeFrag;
    public static CameraFragment cameraFrag;
    public static TrackerFragment trackerFrag;
    public static TutorialFragment tutorialFrag;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        homeFrag = new HomeFragment();
        cameraFrag = new CameraFragment();
        trackerFrag = new TrackerFragment();
        tutorialFrag = new TutorialFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, homeFrag).commit();

        /*Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        String exercise = bun.getString("Exercise");

        if(exercise.length() > 0) {
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout, cameraFrag).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout, homeFrag).commit();
        }*/

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFrag = null;
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        selectedFrag = homeFrag;
                        break;
                    case R.id.camera:
                        selectedFrag = cameraFrag;
                        break;
                    case R.id.accel:
                        selectedFrag = trackerFrag;
                        break;
                    case R.id.tutorials:
                        selectedFrag = tutorialFrag;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFrag).commit();
                return true;
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}