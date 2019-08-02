package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Lift extends AppCompatActivity {

    Accelerometer accelerometer;
    Gyroscope gyroscope;
    TextView xText, yText, zText;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift);

        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);
        handler = new Handler();
        xText = findViewById(R.id.A_X);
        yText = findViewById(R.id.A_Y);
        zText = findViewById(R.id.A_Z);

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                xText.setText("Delta X: " + tx);
                yText.setText("Delta Y: " + ty);
                zText.setText("Delta Z: " + tz);
                if(ty > -0.5 && ty < 0.6) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
            }
        });


        /*gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(ry >= 2) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                        }
                    }, 1000);

                }
            }
        });*/
    }

    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    }

    protected void onPause() {
        super.onPause();

        accelerometer.unRegister();
        gyroscope.unregister();
    }
}
