package com.example.myapplicationopencv;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";
    private static final int[] matriceX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    private static final int[] matriceY = {-1, -2, -1, 0, 0, 0, 1, 2, 1};

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native byte[] gradientJNI(byte[] data, int w, int h);
    public native byte[] sobelJNI(byte[] data, int w, int h);

    private byte[] outarray;
    private byte[] tmparray;

    private int w;
    private int h;

    private CameraBridgeViewBase   mOpenCvCameraView;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    // Load native library after(!) OpenCV initialization
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial2_activity_surface_view);
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.enableFpsMeter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        return true;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
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
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        //mRgba = new Mat(height, width, CvType.CV_8UC4);
        outarray = new byte[width*height];
        tmparray = new byte[width*height];
        w=width;
        h=height;
    }

    public void onCameraViewStopped() {
        //mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat gray = inputFrame.gray();
        MatToArray(gray);

        //gradient(); // Java
        //tmparray = gradientJNI(outarray, w, h); // C++
        //filtreSobel(); // Java
        tmparray = sobelJNI(outarray, w, h);

        Mat out=ArrayToMat(gray,tmparray);
        return out;
    }

    private Mat ArrayToMat(Mat gray, byte[] grayarray) {
        Mat out = gray.clone();
        out.put(0,0,grayarray);
        return out;
    }

    private void MatToArray(Mat gray) {
        gray.get(0, 0, outarray);
    }

    private byte[] gradient() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // On ne traite pas les bordures
                if (x == 0 || y == 0 || x >= w - 1 || y >= h - 1) {
                    tmparray[getIndice(x, y)] = outarray[getIndice(x, y)];
                } else {
                    int gradH = outarray[getIndice(x-1, y)] - outarray[getIndice(x+1, y)];
                    int gradV = outarray[getIndice(x, y-1)] - outarray[getIndice(x, y+1)];
                    byte gradient = (byte) ((gradH + gradV + 255) / 2);
                    tmparray[getIndice(x, y)] = gradient;
                }
            }
        }
        return tmparray;
    }

    private void filtreSobel() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // On ne traite pas les bordures
                if (x == 0 || y == 0 || x >= w - 1 || y >= h - 1) {
                    tmparray[getIndice(x, y)] = outarray[getIndice(x, y)];
                } else {
                    int gradH = convolution(matriceX, outarray, x, y);
                    int gradV = convolution(matriceY, outarray, x, y);
                    int grad = (int) Math.sqrt(Math.pow(gradH, 2) + Math.pow(gradV, 2));
                    tmparray[getIndice(x, y)] = (byte) grad;
                }
            }
        }
    }

    private int convolution(int[] matriceConvolution, byte[] image, int x, int y){
        int k = 0;
        int result = 0;
        for (int j = y-1; j <= y+1; j++){
            for (int i = x-1; i <= x+1; i++){
                result += matriceConvolution[k] * image[getIndice(i, j)];
                k++;
            }
        }
        return result;
    }

    private int getIndice(int x, int y) {
        return x + w * y;
    }
}