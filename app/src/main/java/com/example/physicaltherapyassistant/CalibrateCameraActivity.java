package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CalibrateCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private Button saveButton;
    private SeekBar hueSeekBar;
    private SeekBar satSeekBar;
    private SeekBar valSeekBar;

    private CameraBridgeViewBase calibrator;

    private Mat inputFrame;
    private Mat mat1;
    private Mat mat2;

    private Mat hsvMatI;
    private Mat hsvMatO;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {

            switch(status) {
                case BaseLoaderCallback.SUCCESS:
                    calibrator.enableView();

                    inputFrame = new Mat();
                    mat1 = new Mat();
                    mat2 = new Mat();

                    hsvMatI = new Mat();
                    hsvMatO = new Mat();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_camera);

        saveButton = findViewById(R.id.save_button);
        hueSeekBar = findViewById(R.id.hue_seekBar);
        satSeekBar = findViewById(R.id.saturation_seekBar);
        valSeekBar = findViewById(R.id.value_seekBar);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        calibrator = findViewById(R.id.camera_calibrator);
        calibrator.setVisibility(SurfaceView.VISIBLE);
        calibrator.setCameraIndex(0);
        calibrator.setCvCameraViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(calibrator != null) {
            calibrator.enableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(calibrator != null) {
            calibrator.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(calibrator != null) {
            calibrator.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        inputFrame = new Mat(width, height, CvType.CV_16UC4);
        mat1 = new Mat(width, height, CvType.CV_16UC4);
        mat2 = new Mat(width, height, CvType.CV_16UC4);
        hsvMatI = new Mat(width, height, CvType.CV_16UC4);
        hsvMatO = new Mat(width, height, CvType.CV_16UC4);
    }

    @Override
    public void onCameraViewStopped() {
        inputFrame.release();
        mat1.release();
        mat2.release();
        hsvMatI.release();
        hsvMatO.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame input) {
        inputFrame = input.rgba();

        Core.transpose(inputFrame, mat1);
        Imgproc.resize(mat1, mat2, inputFrame.size(), 0, 0, 0);
        Core.flip(mat2, inputFrame, 1);

        return inputFrame;
    }
}
