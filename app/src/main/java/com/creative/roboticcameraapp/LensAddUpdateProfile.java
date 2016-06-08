package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.Lens;

/**
 * Created by comsol on 02-Jun-16.
 */
public class LensAddUpdateProfile extends AppCompatActivity {

    private EditText ed_lens_name, ed_focus_length;
    private Button btn_save, btn_cancel;

    private boolean shouldUpdate;

    private Switch switch_fisheye;

    private Lens currentLens; //Only be available if update is triggered

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_lens);

        shouldUpdate = getIntent().getBooleanExtra(LensProfileList.KEY_SHOULD_UPDATE, false);

        init();

        if (shouldUpdate) {

            getSupportActionBar().setTitle(R.string.LensAddProfile_update);

            int id = getIntent().getIntExtra(LensProfileList.KEY_UPDATE_ID, 0);
            currentLens = AppController.getInstance().getsqliteDbInstance().getLens(id);
            // txtTitle.setText("Update Camera Profile");
            ed_lens_name.setText(currentLens.getLensName());
            ed_focus_length.setText(currentLens.getFocalLength() + "");
            switch_fisheye.setChecked(currentLens.isFishEyeStatus());
        } else {
            //txtTitle.setText("Add Camera Profile");
            currentLens = new Lens();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showWarningDialog()) {

                    Intent intent = new Intent();

                    currentLens.setLensName(ed_lens_name.getText().toString());
                    currentLens.setFocalLength(Double.parseDouble(ed_focus_length.getText().toString()));
                    currentLens.setFishEyeStatus(switch_fisheye.isChecked());
                    if (shouldUpdate) {
                        AppController.getInstance().getsqliteDbInstance().updateLens(currentLens);
                        Toast.makeText(LensAddUpdateProfile.this, "Lens updated successfully", Toast.LENGTH_SHORT).show();
                        intent.putExtra("lens", getIntent().getIntExtra(LensProfileList.KEY_UPDATE_POSITION, -1));
                    } else {
                        AppController.getInstance().getsqliteDbInstance().addLens(currentLens);
                        Toast.makeText(LensAddUpdateProfile.this, "Lens added successfully", Toast.LENGTH_SHORT).show();
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

        ed_lens_name = (EditText) findViewById(R.id.ed_lens_name);
        ed_focus_length = (EditText) findViewById(R.id.ed_focus_length);
        switch_fisheye = (Switch) findViewById(R.id.switch_fisheye);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (ed_focus_length.getText().toString().isEmpty()) {
            ed_focus_length.setError("Focus length is required");
            ed_focus_length.requestFocus();
            valid = false;
        } else {
            ed_focus_length.setError(null);
        }

        if (ed_lens_name.getText().toString().isEmpty()) {
            ed_lens_name.setError("Lens Name is required");
            ed_lens_name.requestFocus();
            valid = false;
        } else {
            ed_lens_name.setError(null);
        }




        return valid;
    }
}
