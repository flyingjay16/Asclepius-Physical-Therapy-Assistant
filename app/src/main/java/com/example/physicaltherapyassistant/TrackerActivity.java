package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TrackerActivity extends AppCompatActivity {

    private String exerciseName;
    private TextView exerciseText;

    private double acceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        Intent intent = getIntent();
        exerciseName = intent.getStringExtra("TrackerFragment");
        exerciseText = findViewById(R.id.exercise_text);

        exerciseText.setText(exerciseName);

        switch(exerciseName) {
            case "face_pulls":
                acceleration = 0.5;
                break;
            case "lower_traps":
                acceleration = 0.3;
                break;
            case "swimmers":
                acceleration = 0.3;
                break;
            case "rotator_cuff":
                acceleration = 0.5;
                break;
            default:
                acceleration = -1;
        }

    }
}
