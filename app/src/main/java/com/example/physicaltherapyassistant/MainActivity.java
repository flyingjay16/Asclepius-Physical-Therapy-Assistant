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

    BottomNavigationView bottomNav;
    HomeFragment homeFrag;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        homeFrag = new HomeFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, homeFrag).commit();

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
                        selectedFrag = new CameraFragment();
                        break;
                    case R.id.accel:
                        selectedFrag = new TrackerFragment();
                        break;
                    case R.id.tutorials:
                        selectedFrag = new TutorialFragment();
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