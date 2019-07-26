package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class TutorialFragment extends AppCompatActivity {
    Button clk;
    VideoView videov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        clk=(Button) findViewById(R.id.button);
        videov=(VideoView) findViewById(R.id.VideoView);

    }

    public void videoplay(View v) {
        String videopath="android.resource://"+R.raw.placeholder;
        Uri uri = Uri.parse(videopath);
        videov.setVideoURI(uri);
        videov.start();
    }

}
