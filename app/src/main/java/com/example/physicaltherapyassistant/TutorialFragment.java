package com.example.physicaltherapyassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

public class TutorialFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    VideoView videoView;
    String videopath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial_fragment, container, false);
        videoView = view.findViewById(R.id.videoViewFrag);

        MediaController mediaController = new MediaController(view.getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Spinner spinner = view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.vidOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if(text.equals("Swimmers")) {
            videopath="android.resource://com.example.physicaltherapyassistant" +  "/" + R.raw.swimmers;
            Uri uri = Uri.parse(videopath);
            videoView.setVideoURI(uri);
            videoView.start();
        } else if(text.equals("Face Pulls")) {
            videopath="android.resource://com.example.physicaltherapyassistant" +  "/" + R.raw.facePull;
            Uri uri = Uri.parse(videopath);
            videoView.setVideoURI(uri);
            videoView.start();
        } else if(text.equals("Lower Traps")) {
            videopath="android.resource://com.example.physicaltherapyassistant" +  "/" + R.raw.lowerTraps;
            Uri uri = Uri.parse(videopath);
            videoView.setVideoURI(uri);
            videoView.start();
        } else if(text.equals("Rotator Cuffs")) {
            videopath="android.resource://com.example.physicaltherapyassistant" +  "/" + R.raw.rotatorCuff;
            Uri uri = Uri.parse(videopath);
            videoView.setVideoURI(uri);
            videoView.start();
        }
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
