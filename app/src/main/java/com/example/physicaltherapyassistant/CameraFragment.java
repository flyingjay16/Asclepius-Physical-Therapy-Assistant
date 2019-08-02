package com.example.physicaltherapyassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    public static final String TAG = "CameraFragment";

    Button facePullButton;
    Button lowerTrapsButton;
    Button rotatorCuffsButton;
    Button swimmersButton;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.camera_fragment, container, false);
            facePullButton = view.findViewById(R.id.cam_face_pulls);

            facePullButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Camera.class);
                    intent.putExtra(TAG, "face_pull");
                    startActivity(intent);
                }
            });

            lowerTrapsButton = view.findViewById(R.id.cam_lower_traps);

            lowerTrapsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Camera.class);
                    intent.putExtra(TAG, "lower_traps");
                    startActivity(intent);
                }
            });

            rotatorCuffsButton = view.findViewById(R.id.cam_rotator_cuff);
            rotatorCuffsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Camera.class);
                    intent.putExtra(TAG, "rotator_cuffs");
                    startActivity(intent);
                }
            });

            swimmersButton = view.findViewById(R.id.cam_swimmers);
            swimmersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Camera.class);
                    intent.putExtra(TAG, "swimmers");
                    startActivity(intent);
                }
            });

            setHasOptionsMenu(true);


            return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.camera_calibrator, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_calibrator_menu:
                Intent intent = new Intent(view.getContext(), CalibrateCameraActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
