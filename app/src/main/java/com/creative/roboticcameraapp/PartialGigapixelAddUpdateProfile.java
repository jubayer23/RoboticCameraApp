package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.Camera;
import com.creative.roboticcameraapp.model.Lens;
import com.creative.roboticcameraapp.model.Partial;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class PartialGigapixelAddUpdateProfile extends AppCompatActivity {

    private EditText ed_profile_name, ed_overlap;
    private Button btn_save, btn_cancel;

    private boolean shouldUpdate;

    private Partial currentCamera; //Only be available if update is triggered

    private Spinner sp_camera_name,sp_lens_name;

    private List<String> list_camera,list_lens;

    private ArrayAdapter<String> dataAdapterCamera,dataAdapterLens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_partial_gigapixel);

        shouldUpdate = getIntent().getBooleanExtra(PartialGigapixelProfileList.KEY_SHOULD_UPDATE, false);

        init();

        if (shouldUpdate) {

            getSupportActionBar().setTitle(R.string.PartialGigapixelUpdateProfile);

            int id = getIntent().getIntExtra(PartialGigapixelProfileList.KEY_UPDATE_ID, 0);
            currentCamera = AppController.getInstance().getsqliteDbInstance().getPartialGigapixel(id);
            // txtTitle.setText("Update Camera Profile");
            ed_profile_name.setText(currentCamera.getPartialName());
            ed_overlap.setText(currentCamera.getOverlap() + "");

            list_camera.add(currentCamera.getCameraName());
            List<Camera> cameras = AppController.getInstance().getsqliteDbInstance().getAllCamera();
            for (int i = 0; i < cameras.size(); i++) {

                if (list_camera.contains(cameras.get(i).getCameraName()))continue;

                list_camera.add(cameras.get(i).getCameraName());

            }

            dataAdapterCamera.notifyDataSetChanged();


            list_lens.add(currentCamera.getLensName());
            List<Lens> lens = AppController.getInstance().getsqliteDbInstance().getAllLenses();
            for (int i = 0; i < lens.size(); i++) {

                if (list_lens.contains(lens.get(i).getLensName()))continue;

                list_lens.add(lens.get(i).getLensName());

            }

            dataAdapterLens.notifyDataSetChanged();

        } else {
            //txtTitle.setText("Add Camera Profile");
            currentCamera = new Partial();

            List<Camera> cameras = AppController.getInstance().getsqliteDbInstance().getAllCamera();
            List<Lens> lens = AppController.getInstance().getsqliteDbInstance().getAllLenses();

            for (int i = 0; i < cameras.size(); i++) {

                list_camera.add(cameras.get(i).getCameraName());

            }

            dataAdapterCamera.notifyDataSetChanged();

            for (int i = 0; i < lens.size(); i++) {

                list_lens.add(lens.get(i).getLensName());

            }

            dataAdapterLens.notifyDataSetChanged();


        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showWarningDialog()) {

                    Intent intent = new Intent();

                    currentCamera.setPartialName(ed_profile_name.getText().toString());
                    currentCamera.setCameraName(sp_camera_name.getSelectedItem().toString());
                    currentCamera.setLensName(sp_lens_name.getSelectedItem().toString());
                    currentCamera.setOverlap(Integer.parseInt(ed_overlap.getText().toString()));
                    if (shouldUpdate) {
                        AppController.getInstance().getsqliteDbInstance().updatePartialGigapixel(currentCamera);
                        Toast.makeText(PartialGigapixelAddUpdateProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {

                        //Log.d("DEBUG",currentCamera.getSingleRowName());

                        AppController.getInstance().getsqliteDbInstance().addPartialGigapixel(currentCamera);
                        Toast.makeText(PartialGigapixelAddUpdateProfile.this, "Profile added successfully", Toast.LENGTH_SHORT).show();
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

        ed_profile_name = (EditText) findViewById(R.id.ed_profile_name);
        ed_overlap = (EditText) findViewById(R.id.ed_overlap);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        sp_camera_name = (Spinner) findViewById(R.id.sp_camera_name);
        sp_lens_name = (Spinner) findViewById(R.id.sp_lens_name);

        list_camera = new ArrayList<>();
        dataAdapterCamera = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_camera);
        sp_camera_name.setAdapter(dataAdapterCamera);

        list_lens = new ArrayList<>();
        dataAdapterLens = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_lens);
        sp_lens_name.setAdapter(dataAdapterLens);

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (ed_overlap.getText().toString().isEmpty()) {
            ed_overlap.setError("Required");
            ed_overlap.requestFocus();
            valid = false;
        } else {
            ed_overlap.setError(null);
        }


        if (ed_profile_name.getText().toString().isEmpty()) {
            ed_profile_name.setError("Required");
            ed_profile_name.requestFocus();
            valid = false;
        } else {
            ed_profile_name.setError(null);
        }



        return valid;
    }
}
