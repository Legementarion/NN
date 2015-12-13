package com.lego.mydiploma.Functions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.dialog, container, false);

        camera = (ImageButton) rootView.findViewById(R.id.imageButton1);

        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        gallery = (ImageButton) rootView.findViewById(R.id.imageButton2);

        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return rootView;
    }
}