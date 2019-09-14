package com.example.physicaltherapyassistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Camera extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    public String algorythm;

    private static final String TAG = "Camera";

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

    private int centerX;
    private int centerY;

    private ArrayList<Integer> xRange;
    private ArrayList<Integer> yRange;

    public static ArrayList<Integer> xRepRange;
    public static ArrayList<Integer> yRepRange;

    private boolean isLeft;
    private int markerX;
    private Boolean isWrongRep;
    private String direction;

    private TextView correctRepText;
    private TextView totalRepText;
    private TextView correctRepNum;
    private TextView totalRepNum;
    private int correctReps;
    private int totalReps;

    private long t1;
    private long t2;

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

        Intent intent = getIntent();
        algorythm = intent.getStringExtra("CameraFragment");

        correctRepText = findViewById(R.id.correct_reps_text);
        totalRepText = findViewById(R.id.total_reps_text);
        correctRepNum = findViewById(R.id.correct_reps_num);
        totalRepNum = findViewById(R.id.total_reps_num);

        correctRepText.setTextColor(Color.WHITE);
        totalRepText.setTextColor(Color.WHITE);
        correctRepNum.setTextColor(Color.WHITE);
        totalRepNum.setTextColor(Color.WHITE);
        correctReps = 0;
        totalReps = 0;

        isWrongRep = null;

        t1 = 0;
        t2 = 0;

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
        switch(algorythm){
            case "face_pull":
                return detectFacePulls(input);
            case "lower_traps":
                return detectLowerTraps(input);
            case "rotator_cuffs":
                return detectRotatorCuffs(input);
            case "swimmers":
                return detectSwimmers(input);
            default:
                return input.rgba();
        }
    }

    private Mat detectFacePulls(CameraBridgeViewBase.CvCameraViewFrame input) {
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

        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ArrayList<Integer> tempXRange = new ArrayList<>();
                ArrayList<Integer> tempYRange = new ArrayList<Integer>();
                tempYRange.add(centerY - 50);
                tempYRange.add(centerY + 50);

                yRange = tempYRange;
                tempYRange = new ArrayList<Integer>();

                tempXRange.add(centerX - 20);
                tempXRange.add(centerX + 20);

                xRepRange = tempXRange;

                tempYRange.add(centerY - 20);
                tempYRange.add(centerY + 20);
                yRepRange = tempYRange;

                isWrongRep = false;

                return false;
            }
        });

        for(int i = 0 ; i < contourList.size(); i++) {
            double area = Imgproc.contourArea(contourList.get(i));
            if(area > 4000) {
                Rect rect = Imgproc.boundingRect(contourList.get(i));
                centerX = rect.x;
                centerY = rect.y;

                Log.d(TAG, "centerX: " + centerX);
                Log.d(TAG, "centerY: " + centerY);

                if(isWrongRep == null){
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 4);
                }
                else if(yRange != null && (centerY >= yRange.get(0) && centerY <= yRange.get(1))) {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0,255,0), 4);
                }
                else {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    isWrongRep = true;
                }

                if(xRepRange != null && (centerX >= xRepRange.get(0) && centerX <= xRepRange.get(1))) {
                    Log.d(TAG, " isWrongRep: " + isWrongRep);
                    updateReps(2000);
                }

            }
        }

        return inputFrame;
    }

    private Mat detectRotatorCuffs(CameraBridgeViewBase.CvCameraViewFrame input) {
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

        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ArrayList<Integer> tempXRange = new ArrayList<Integer>();
                ArrayList<Integer> tempYRange = new ArrayList<Integer>();
                tempYRange.add(centerY - 50);
                tempYRange.add(centerY + 50);

                yRange = tempYRange;
                tempYRange = new ArrayList<Integer>();


                tempXRange.add(centerX - 20);
                tempXRange.add(centerX + 20);

                xRepRange = tempXRange;

                tempYRange.add(centerY - 20);
                tempYRange.add(centerY + 20);

                yRepRange = tempYRange;

                isWrongRep = false;

                return false;
            }
        });

        for(int i = 0 ; i < contourList.size(); i++) {

            double area = Imgproc.contourArea(contourList.get(i));
            if(area > 4000) {
                Rect rect = Imgproc.boundingRect(contourList.get(i));
                centerX = rect.x;
                centerY = rect.y;

                Log.d(TAG, "centerX: " + centerX);
                Log.d(TAG, "centerY: " + centerY);

                if(isWrongRep == null) {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 4);
                }
                else if(yRange != null && (centerY >= yRange.get(0) && centerY <= yRange.get(1))) {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0,255,0), 4);
                }
                else {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    isWrongRep = true;
                }

                if(xRepRange != null && (centerX >= xRepRange.get(0) && centerX <= xRepRange.get(1))) {
                    updateReps(2000);
                }
            }
        }

        return inputFrame;
    }

    private Mat detectLowerTraps(CameraBridgeViewBase.CvCameraViewFrame input) {
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

        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ArrayList<Integer> tempXRange = new ArrayList<Integer>();
                tempXRange.add(centerX - 20);
                tempXRange.add(centerX + 20);

                xRange = tempXRange;

                ArrayList<Integer> tempYRange = new ArrayList<Integer>();
                tempYRange.add(centerY - 20);
                tempYRange.add(centerY + 20);

                yRange = tempYRange;

                tempXRange = new ArrayList<Integer>();
                tempYRange = new ArrayList<Integer>();

                tempXRange.add(centerX - 10);
                tempXRange.add(centerX + 10);
                xRepRange = tempXRange;

                tempYRange.add(centerY - 10);
                tempYRange.add(centerY + 10);
                yRepRange = tempYRange;

                isWrongRep = false;

                return false;
            }
        });

        for(int i = 0 ; i < contourList.size(); i++) {

            double area = Imgproc.contourArea(contourList.get(i));
            Log.d(TAG, "contour area: " + area);
            if(area > 4000) {
                Rect rect = Imgproc.boundingRect(contourList.get(i));
                centerX = rect.x;
                centerY = rect.y;

                Log.d(TAG, "centerX: " + centerX);
                Log.d(TAG, "centerY: " + centerY);


                if(isWrongRep == null) {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 4);
                }
                else if(xRange != null && (centerX >= xRange.get(0) && centerX <= xRange.get(1))) {
                    if(yRange != null && (centerY >= yRange.get(0) && centerY <= yRange.get(1))) {
                        Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0,255,0), 4);
                    }
                    else {
                        Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    }
                }
                else {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    isWrongRep = true;
                }

                if(xRepRange != null && (centerX >= xRepRange.get(0) && centerX <= xRepRange.get(1))) {
                    Log.d(TAG, " isWrongRep: " + isWrongRep);
                    updateReps(2000);
                }
            }
        }

        return inputFrame;
    }

    private Mat detectSwimmers(CameraBridgeViewBase.CvCameraViewFrame input) {
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

        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ArrayList<Integer> tempXRange = new ArrayList<Integer>();
                tempXRange.add(centerX - 20);
                tempXRange.add(centerX + 20);

                xRange = tempXRange;

                ArrayList<Integer> tempYRange = new ArrayList<Integer>();
                tempYRange.add(centerY - 20);
                tempYRange.add(centerY + 20);

                yRange = tempYRange;

                tempXRange = new ArrayList<Integer>();
                tempYRange = new ArrayList<Integer>(); 

                tempXRange.add(centerX - 10);
                tempXRange.add(centerX + 10);

                tempYRange.add(centerY - 10);
                tempYRange.add(centerY + 10);

                xRepRange = tempXRange;
                yRepRange = tempYRange;

                isWrongRep = false;

                return false;
            }
        });

        for(int i = 0 ; i < contourList.size(); i++) {

            double area = Imgproc.contourArea(contourList.get(i));
            Log.d(TAG, "contour area: " + area);
            if(area > 4000) {
                Rect rect = Imgproc.boundingRect(contourList.get(i));
                centerX = rect.x;
                centerY = rect.y;

                Log.d(TAG, "centerX: " + centerX);
                Log.d(TAG, "centerY: " + centerY);

                if(isWrongRep == null) {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 4);
                }
                else if(xRange != null && (centerX >= xRange.get(0) && centerX <= xRange.get(1))) {
                    if(yRange != null && (centerY >= yRange.get(0) && centerY <= yRange.get(1))) {
                        Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0,255,0), 4);
                    }
                    else {
                        Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    }
                }
                else {
                    Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(255,0,0), 4);
                    isWrongRep = true;
                }

                if(xRepRange != null && (centerX >= xRepRange.get(0) && centerX <= xRepRange.get(1))) {
                    updateReps(2000);
                }
            }
        }

        return inputFrame; 
    }

    public void updateReps(long timeout) {
        if(t1 == 0|| t1 >= t2) {
            t1 = System.currentTimeMillis();
            t2 = t1 + timeout;
            if(yRepRange != null && (centerY >=  yRepRange.get(0) && centerY <= yRepRange.get(1)) && !isWrongRep) {
                correctReps += 1;
                Log.d(TAG, "Correct num: " + correctReps);
                //correctRepNum.setText("correct");

                totalReps += 1;
                Log.d(TAG, "Total num: " + totalReps);
                //totalRepNum.setText("correct");
            }
            else {
                totalReps += 1;
                //totalRepNum.setText("wrong");
                Log.d(TAG, "Total num: " + totalReps);
                isWrongRep = false;
                long x = System.currentTimeMillis();
            }
        }
        else {
            t1 = System.currentTimeMillis();
        }
        Log.d(TAG, "Time: " + t1 + " " + t2);
    }



}
