package com.example.physicaltherapyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFrag = null;
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        selectedFrag = new HomeFragment();
                        break;
                    case R.id.camera:
                        selectedFrag = new CameraFragment();
                        break;
                    case R.id.accel:
                        selectedFrag = new LiftFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFrag).commit();
                return true;
            }

        });

    }

}
