package com.example.physicaltherapyassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrackerFragment extends Fragment {

    private static final String TAG = "TrackerFragment";

    Button face_pullsButton;
    Button lower_trapsButton;
    Button swimmersButton;
    Button rotator_cuffButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.tracker_fragment, container, false);

            face_pullsButton = view.findViewById(R.id.accel_face_pulls);
            face_pullsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TrackerActivity.class);
                    intent.putExtra(TAG, "Face Pulls");
                    startActivity(intent);
                }
            });

            lower_trapsButton = view.findViewById(R.id.accel_lower_traps);
            lower_trapsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TrackerActivity.class);
                    intent.putExtra(TAG, "Lower Traps");
                    startActivity(intent);
                }
            });

            swimmersButton = view.findViewById(R.id.accel_swimmers);
            swimmersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TrackerActivity.class);
                    intent.putExtra(TAG, "Swimmers");
                    startActivity(intent);
                }
            });

            rotator_cuffButton = view.findViewById(R.id.accel_rotator_cuff);
            rotator_cuffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TrackerActivity.class);
                    intent.putExtra(TAG, "Rotator Cuffs");
                    startActivity(intent);
                }
            });

            return view;
    }

}
