package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.Camera;

/**
 * Created by comsol on 02-Jun-16.
 */
public class CameraAddUpdateProfile extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText ed_camera_name, ed_sensor_width, ed_sensor_height;
    private Button btn_save, btn_cancel;

    private boolean shouldUpdate;

    private Camera currentCamera; //Only be available if update is triggered

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_camera);

        shouldUpdate = getIntent().getBooleanExtra(CameraProfileList.KEY_SHOULD_UPDATE, false);

        init();

        if (shouldUpdate) {

            getSupportActionBar().setTitle(R.string.CameraAddProfile_update);

            int id = getIntent().getIntExtra(CameraProfileList.KEY_UPDATE_ID, 0);
            currentCamera = AppController.getInstance().getsqliteDbInstance().getCamera(id);
            // txtTitle.setText("Update Camera Profile");
            ed_camera_name.setText(currentCamera.getCameraName());
            ed_sensor_width.setText(currentCamera.getSensorWidth() + "");
            ed_sensor_height.setText(currentCamera.getSensorHeight() + "");
        } else {
            //txtTitle.setText("Add Camera Profile");
            currentCamera = new Camera();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showWarningDialog()) {

                    Intent intent = new Intent();

                    currentCamera.setCameraName(ed_camera_name.getText().toString());
                    currentCamera.setSensorWidth(Double.parseDouble(ed_sensor_width.getText().toString()));
                    currentCamera.setSensorHeight(Double.parseDouble(ed_sensor_height.getText().toString()));
                    if (shouldUpdate) {
                        AppController.getInstance().getsqliteDbInstance().updateCamera(currentCamera);
                        Toast.makeText(CameraAddUpdateProfile.this, "Camera updated successfully", Toast.LENGTH_SHORT).show();
                        intent.putExtra("camera", getIntent().getIntExtra(CameraProfileList.KEY_UPDATE_POSITION, -1));
                    } else {
                        AppController.getInstance().getsqliteDbInstance().addCamera(currentCamera);
                        Toast.makeText(CameraAddUpdateProfile.this, "Camera added successfully", Toast.LENGTH_SHORT).show();
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {

        ed_camera_name = (EditText) findViewById(R.id.ed_camera_name);
        ed_sensor_width = (EditText) findViewById(R.id.ed_sensor_width);
        ed_sensor_width.setOnFocusChangeListener(this);
        ed_sensor_height = (EditText) findViewById(R.id.ed_sensor_height);
        ed_sensor_height.setOnFocusChangeListener(this);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

    }

    public boolean showWarningDialog() {

        boolean valid = true;
        if (!ed_sensor_height.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_sensor_height.getText().toString());
            if (value < 0 || value > 100) {
                ed_sensor_height.setError("Invalid input");
                ed_sensor_height.requestFocus();
                valid = false;
            } else {
                ed_sensor_height.setError(null);
            }
        } else {
            ed_sensor_height.setError("Sensor Height is required");
            ed_sensor_height.requestFocus();
            valid = false;
        }
        if (!ed_sensor_width.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_sensor_width.getText().toString());
            if (value < 0 || value > 100) {
                ed_sensor_width.setError("Invalid input");
                ed_sensor_width.requestFocus();
                valid = false;
            } else {
                ed_sensor_width.setError(null);
            }
        } else {
            ed_sensor_width.setError("Sensor Width is required");
            ed_sensor_width.requestFocus();
            valid = false;
        }

        if (ed_camera_name.getText().toString().isEmpty()) {
            ed_camera_name.setError("Camera Name is required");
            ed_camera_name.requestFocus();
            valid = false;
        } else {
            ed_camera_name.setError(null);
        }


        return valid;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        if (!b) {
            int id = view.getId();
            switch (id) {
                case R.id.ed_sensor_width:
                    if (!ed_sensor_width.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_sensor_width.getText().toString());
                        if (value < 0 || value > 100) {
                            ed_sensor_width.setError("Invalid input");
                        } else {
                            ed_sensor_width.setError(null);
                        }
                    }
                    break;
                case R.id.ed_sensor_height:
                    if (!ed_sensor_height.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_sensor_height.getText().toString());
                        if (value < 0 || value > 100) {
                            ed_sensor_height.setError("Invalid input");
                        } else {
                            ed_sensor_height.setError(null);
                        }
                    }
                    break;
            }
        }

    }
}
