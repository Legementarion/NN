package com.lego.mydiploma.Functions;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.lego.mydiploma.R;

/**
 * Created by Lego on 12.12.2015.
 */

public class CamDialog extends DialogFragment implements DialogInterface.OnCancelListener{

    ImageButton camera;
    ImageButton gallery;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_REQUEST = 2;

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
                    startActivityForResult(captureIntent, CAMERA_CAPTURE);
                }
                catch (ActivityNotFoundException e){
                    String errorMessage = "Ваше устройство не поддерживает съемку";
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
                    String errorMessage = "Ваше устройство не поддерживает съемку";
                    Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return rootView;
    }

}
