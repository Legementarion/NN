package com.lego.mydiploma.Activity;

/**
 * Created by Lego on 12.12.2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lego.mydiploma.Adapters.ListAdapter;
import com.lego.mydiploma.Objects.SandObject;
import com.lego.mydiploma.R;

public class ContentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        SandObject sandObject = new SandObject("sss","sss");
        sandObject.initializeData();
        ListAdapter adapter = new ListAdapter(sandObject.Data);
        rv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
