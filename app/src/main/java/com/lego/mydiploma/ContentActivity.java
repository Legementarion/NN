package com.lego.mydiploma;

/**
 * Created by Lego on 12.12.2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ContentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    }


    public void BackToMain(View view) {
        Intent intent = new Intent(ContentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
