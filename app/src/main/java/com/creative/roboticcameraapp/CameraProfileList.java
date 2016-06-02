package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by comsol on 02-Jun-16.
 */
public class CameraProfileList extends AppCompatActivity {
    private FloatingActionButton addCameraProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_plist);

        init();
    }

    private void init() {
        addCameraProfile = (FloatingActionButton) findViewById(R.id.add_camera_profile);
        addCameraProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraProfileList.this,CameraAddProfile.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
