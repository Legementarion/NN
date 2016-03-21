package com.lego.mydiploma;

import android.content.ActivityNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    CamDialog myDialogFragment;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_REQUEST = 2;
    //    Filter filter = new Filter();
    private int minSize;


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
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                int scale = 1;
                while (options.outWidth / scale / 2 >= minSize
                        && options.outHeight / scale / 2 >= minSize) {
                    scale *= 2;
                }
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
//                filter.parse(bm);
                setImg(bm);
            }
        }

        if (requestCode == CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                int scale = 1;
                while (options.outWidth / scale / 2 >= minSize
                        && options.outHeight / scale / 2 >= minSize) {
                    scale *= 2;
                }
                options.inSampleSize = scale;

                final Bitmap bitmap = BitmapFactory.decodeFile(myDialogFragment.getFileUri().getPath(),
                        options);
//                filter.parse(thumbnail);
                setImg(bitmap);
            }
        }
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


