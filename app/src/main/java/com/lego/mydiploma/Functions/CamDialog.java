package com.lego.mydiploma.Functions;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.lego.mydiploma.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lego on 12.12.2015.
 */

public class CamDialog extends DialogFragment implements DialogInterface.OnCancelListener{

    ImageButton camera;
    ImageButton gallery;
    public static Uri fileUri;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_REQUEST = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "ololo";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog, container, false);

        camera = (ImageButton) rootView.findViewById(R.id.imageButton1);
        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    dismiss();
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(captureIntent, CAMERA_CAPTURE);
                }
                catch (ActivityNotFoundException e){
                    String errorMessage = getString(R.string.error_camera);
                    Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        gallery = (ImageButton) rootView.findViewById(R.id.imageButton2);
        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    dismiss();
                    startActivityForResult(photoPickerIntent, GALLERY_REQUEST);}
                catch (ActivityNotFoundException e){
                    String errorMessage = getString(R.string.error_gallery);
                    Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return rootView;
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

}
