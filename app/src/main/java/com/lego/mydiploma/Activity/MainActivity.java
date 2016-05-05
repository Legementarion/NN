package com.lego.mydiploma.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.content.CursorLoader;
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

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    CamDialog myDialogFragment;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_REQUEST = 2;
    //    Filter filter = new Filter();
    private int minSize;
    private Uri sourceImageUri;
    final String CROPPED_IMAGE_NAME = "CroppedImage.png";

//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS:
//                {
//                    Log.i("OpenCV", "OpenCV loaded successfully");
//                } break;
//                default:
//                {
//                    super.onManagerConnected(status);
//                } break;
//            }
//        }
//    };

    @Override
    public void onResume() {
        super.onResume();
//        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        myDialogFragment = new CamDialog();
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
                imageView.setImageURI(sourceImageUri);
            } else if (resultCode == UCrop.RESULT_ERROR) {
                imageView.setImageURI(sourceImageUri);
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
        return imageView.getDrawingCache();
    }

    public void setImg(Bitmap bm) {
        imageView.setImageBitmap(bm);
    }

    public void setCancelDialog() {
        myDialogFragment.dismiss();
    }


//    class Filter {
//        Bitmap bitmap = null;
//
//        public void parse(Bitmap bm) {
//            bitmap = bm;
//            Mat mat = new Mat();
//        Utils.bitmapToMat(bitmap, mat);
//        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY, 4);
//        HOGDescriptor hog = new HOGDescriptor();
//        Utils.matToBitmap( mat , bitmap );
//            setImg(bitmap);
//        }
}


