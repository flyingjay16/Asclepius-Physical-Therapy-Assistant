package com.example.physicaltherapyassistant;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";
    private int CAMERA_PERMISSION_CODE = 1;

    Button facePullButton;
    Button lowerTrapsButton;
    Button rotatorCuffsButton;
    Button swimmersButton;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.camera_fragment, container, false);

            if(ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getContext(), "You have already granted this permission!", Toast.LENGTH_SHORT).show();
            } else {
                requestCameraPermission();
            }

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

    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("Camera permission is needed to track objects")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT);
            }
        }
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
