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
    private TextView exerciseText;

    private double acceleration;

    private Accelerometer accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        accelerometer = new Accelerometer(getApplicationContext());

        Intent intent = getIntent();
        exerciseName = intent.getStringExtra("TrackerFragment");
        exerciseText = findViewById(R.id.exercise_text);

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
                if(ty > -acceleration && ty < (acceleration + 1)) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
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
}
