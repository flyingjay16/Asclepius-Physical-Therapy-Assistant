package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CalibrateCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "CalibrateCameraActivity";

    public static Scalar lScalar;
    public static Scalar uScalar;

    private Button saveButton;
    private RangeSeekBar<Integer> hueSeekBar;
    private RangeSeekBar<Integer> satSeekBar;
    private RangeSeekBar<Integer> valSeekBar;

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

        hueSeekBar.setRangeValues(0, 180);
        satSeekBar.setRangeValues(0, 255);
        valSeekBar.setRangeValues(0, 255);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.lScalar = lScalar;
                Camera.uScalar = uScalar;
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

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
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

        lScalar = new Scalar(hueSeekBar.getSelectedMinValue(), satSeekBar.getSelectedMinValue(), valSeekBar.getSelectedMinValue());
        uScalar = new Scalar(hueSeekBar.getSelectedMaxValue(), satSeekBar.getSelectedMaxValue(), valSeekBar.getSelectedMaxValue());

        Imgproc.cvtColor(inputFrame, hsvMatI, Imgproc.COLOR_RGB2HSV_FULL);
        Core.inRange(hsvMatI, lScalar, uScalar, hsvMatO);

        return hsvMatO;
    }
}
