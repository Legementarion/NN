package com.lego.mydiploma.Activity;

import android.content.ActivityNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lego.mydiploma.Functions.CamDialog;
import com.lego.mydiploma.R;
import com.yalantis.ucrop.UCrop;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    CamDialog myDialogFragment;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_REQUEST = 2;
    Filter filter;
    private int minSize;
    private Uri sourceImageUri;
    final String CROPPED_IMAGE_NAME = "CroppedImage.png";

//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS: {
//                    Log.i("OpenCV", "OpenCV loaded successfully");
//                }
//                break;
//                default: {
//                    super.onManagerConnected(status);
//                }
//                break;
//            }
//        }
//    };

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initDebug();
//        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        myDialogFragment = new CamDialog();
        filter = new Filter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadFrom(View view) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            myDialogFragment.show(manager, "dialog");
        } catch (ActivityNotFoundException e) {
            String errorMessage = getString(R.string.error_camera);
            Toast toast = Toast
                    .makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
        if (imageView.getHeight() < imageView.getWidth()) {
            minSize = imageView.getHeight();
        } else {
            minSize = imageView.getWidth();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            sourceImageUri = myDialogFragment.getFileUri();
            if (requestCode == GALLERY_REQUEST) {
                Uri sourceUri = data.getData();
                //TODO: checking of file existing
                File croppedImgFile = null;
                try {
                    croppedImgFile = File.createTempFile(CROPPED_IMAGE_NAME, ".png", getCacheDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (croppedImgFile.exists()) {
                    sourceImageUri = Uri.fromFile(croppedImgFile);
                    toCrop(sourceUri, sourceImageUri);
                }
            }
            //returning from camera
            else if (requestCode == CAMERA_CAPTURE) {
                toCrop(sourceImageUri, sourceImageUri);
            }
            //returning from Yalantis crop
            else if (requestCode == UCrop.REQUEST_CROP) {
                sourceImageUri = UCrop.getOutput(data);
                setImg();
            } else if (resultCode == UCrop.RESULT_ERROR) {
                setImg();
            }
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    startActivity(intent);
                }
            }, 14000);

        }
    }

    private void toCrop(Uri source, Uri destination) {
        //Yalantis crop library (with rotation)
        UCrop.of(source, destination).start(this);
    }

    public void PressExit(View view) {
        System.exit(1);
    }

    public Bitmap getImg() {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    public void setImg(Bitmap bm) {
        imageView.setImageBitmap(bm);
    }

    public void setImg() {
        imageView.setImageURI(sourceImageUri);
//        filter.parse(getImg());
    }

    public void setCancelDialog() {
        myDialogFragment.dismiss();
    }


    class Filter {


        public void parse(Bitmap bm) {
            Mat mat = new Mat();
            Utils.bitmapToMat(bm, mat);
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2HSV);
            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Scalar a = Scalar.all(40);
            Scalar b = Scalar.all(150);
            Rect rect = new Rect();
            rect.width = 4;
            rect.height = 4;

            Mat roi = new Mat();
            roi = mat.submat(rect);
            Mat roiTmp = roi.clone();

//            Imgproc.cvtColor(roiTmp, roiTmp, Imgproc.COLOR_RGB2HSV);

            Core.inRange(roiTmp, a, b, roiTmp);

            Imgproc.cvtColor(roiTmp, roi, Imgproc.COLOR_GRAY2BGRA);

            Imgproc.threshold(mat, roiTmp,60, 255,Imgproc.THRESH_BINARY);

            Imgproc.findContours(roiTmp,contours,mat,Imgproc.RETR_CCOMP,Imgproc.CHAIN_APPROX_SIMPLE);
//
//            ArrayList<Mat> channels = new ArrayList<Mat>();
//            Core.split(mat, channels);
//            mat = channels.get(1);

//            BackgroundSubtractorMOG2 mBG = Video.createBackgroundSubtractorMOG2(16, 16, true);
//            mBG.setNMixtures(3);
//            mBG.setDetectShadows(true);
//            mBG.setShadowValue(1);  //resolved!
//            mBG.fTau = 0.5;           //resolved!
//
//            mBG.apply(mat, mat, 0.005);
//            HOGDescriptor hog = new HOGDescriptor();
            Utils.matToBitmap(roiTmp, bm);
            setImg(bm);
        }
    }
}


