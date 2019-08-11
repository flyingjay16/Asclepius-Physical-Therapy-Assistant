package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TrackerActivity extends AppCompatActivity {

    private static final String TAG = "TrackerActivity";

    private String exerciseName;
    private int repNum;
    private TextView exerciseText;
    private TextView accelNum;
    private TextView repNumView;

    private double acceleration;
    private float lastAccelNum;

    private Accelerometer accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        accelerometer = new Accelerometer(getApplicationContext());

        Intent intent = getIntent();
        exerciseName = intent.getStringExtra("TrackerFragment");
        exerciseText = findViewById(R.id.exercise_text);
        accelNum = findViewById(R.id.accel_num);
        repNumView = findViewById(R.id.accel_rep_num);

        repNum = 0;

        lastAccelNum = 0;

        exerciseText.setText(exerciseName);

        switch(exerciseName) {
            case "Face Pulls":
                acceleration = 0.5;
                break;
            case "Lower_Traps":
                acceleration = 0.3;
                break;
            case "Swimmers":
                acceleration = 0.3;
                break;
            case "Rotator Cuffs":
                acceleration = 0.5;
                break;
            default:
                acceleration = -1;
        }

        Log.d(TAG, "acceleration: " + acceleration);
        accelerometer.setListener(new Accelerometer.Listener() {

            @Override
            public void onTranslation(float tx, float ty, float tz) {
                accelNum.setText(Float.toString(ty));
                switch(exerciseName) {
                    case "Face Pulls":
                        detectFacePulls(ty);
                        break;
                    case "Lower Traps":
                        detectLowerTraps(ty);
                        break;
                    case "Swimmers":
                        detectSwimmers(ty);
                        break;
                    case "Rotator Cuffs":
                        detectRotatorCuffs(ty);
                        break;
                    default:
                        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unRegister();
    }

    private void detectFacePulls(float ty) {
        if(ty > -acceleration && ty < (acceleration + 1)) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }
        else {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }

        if((lastAccelNum < 0 && ty > 0) || (lastAccelNum > 0 && ty < 0)) {
            repNum++;
            repNumView.setText(Integer.toString(repNum));
        }

        lastAccelNum = ty;
    }

    private void detectLowerTraps(float ty) {
        if(ty > -acceleration && ty < (acceleration + 1)) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }
        else {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
    }

    private void detectRotatorCuffs(float ty) {
        if(ty > -acceleration && ty < (acceleration + 1)) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }
        else {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
    }

    private void detectSwimmers(float ty) {
        if(ty > -acceleration && ty < (acceleration + 1)) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }
        else {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
    }
}
