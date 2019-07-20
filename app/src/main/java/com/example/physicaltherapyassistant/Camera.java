package com.example.physicaltherapyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Camera extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = "MainActivity";

    private CameraBridgeViewBase tracker;

    private Mat inputFrame;
    private Mat mat1;
    private Mat mat2;

    public static Scalar lScalar = new Scalar(100,112,50);  //100,112,50        112,131,15
    public static Scalar uScalar = new Scalar(180,255,255); //180,255,255       180,255,255

    private Mat hsvMatI;
    private Mat hsvMatO;

    private Mat mBlurMat;

    private Mat hierarchy;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {

            switch(status) {
                case BaseLoaderCallback.SUCCESS:
                    tracker.enableView();
                    inputFrame = new Mat();
                    mat1 = new Mat();
                    mat2 = new Mat();
                    hsvMatI = new Mat();
                    hsvMatO = new Mat();
                    mBlurMat = new Mat();
                    hierarchy = new Mat();
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
        setContentView(R.layout.camera);
        Log.d(TAG, "onCreate: called");

        tracker = findViewById(R.id.tracker);
        tracker.setVisibility(SurfaceView.VISIBLE);
        tracker.setCameraIndex(0);
        tracker.setCvCameraViewListener(this);


        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: called");
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

        Log.d(TAG, "onPause: called");

        if(tracker != null) {
            tracker.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: called");

        if(tracker != null) {
            tracker.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        inputFrame = new Mat(width, height, CvType.CV_16UC4);
        mat1 = new Mat(width, height, CvType.CV_16UC4);
        mat2 = new Mat(width, height, CvType.CV_16UC4);
        hsvMatI = new Mat(width, height, CvType.CV_16UC4);
        hsvMatO = new Mat(width, height, CvType.CV_16UC4);
        mBlurMat = new Mat(width, height, CvType.CV_16UC4);
        hierarchy = new Mat(width, height, CvType.CV_16UC4);
    }

    @Override
    public void onCameraViewStopped() {
        inputFrame.release();
        mat1.release();
        mat2.release();

        hsvMatI.release();
        hsvMatO.release();

        mBlurMat.release();

        hierarchy.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame input) {
        inputFrame = input.rgba();

        Core.transpose(inputFrame, mat1);
        Imgproc.resize(mat1, mat2, inputFrame.size(), 0, 0, 0);
        Core.flip(mat2, inputFrame, 1);

        Imgproc.cvtColor(inputFrame, hsvMatI, Imgproc.COLOR_RGB2HSV_FULL);
        Core.inRange(hsvMatI, lScalar, uScalar, hsvMatO);

        Imgproc.medianBlur(hsvMatO, mBlurMat, 35); //45,55

        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mBlurMat, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        hierarchy.release();

        for(int i = 0 ; i < contourList.size(); i++) {
            /*hierarchy.release();
            Imgproc.drawContours(inputFrame, contourList, i,  new Scalar(255,0,0), 2);*/

            /*MatOfPoint2f temp = new MatOfPoint2f(contourList.get(i).toArray());
            RotatedRect rect = Imgproc.minAreaRect(temp);

            Mat box = new Mat();


            Imgproc.boxPoints(rect, box);


            List<MatOfPoint> boxList = new ArrayList<MatOfPoint>();
            boxList.add(box);

            Imgproc.drawContours(inputFrame, boxList, i, new Scalar(255,0,0), 2);*/

            double area = Imgproc.contourArea(contourList.get(i));
            if(area > 1000) {
                Rect rect = Imgproc.boundingRect(contourList.get(i));
                Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 2);
            }
        }

        return inputFrame;
    }
}
