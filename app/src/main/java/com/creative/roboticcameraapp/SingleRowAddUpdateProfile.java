package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class SingleRowAddUpdateProfile extends AppCompatActivity {

    private EditText ed_single_row_name, ed_sr_num_of_shooting_position, ed_sr_num_of_bracketed_shot;
    private Button btn_save, btn_cancel;

    private boolean shouldUpdate;

    private SingleRow currentCamera; //Only be available if update is triggered

    private Spinner spinner_bracketing_style;

    private List<String> list_bracketing_style;

    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_single_row);

        shouldUpdate = getIntent().getBooleanExtra(SingleRowProfileList.KEY_SHOULD_UPDATE, false);

        init();

        if (shouldUpdate) {

            getSupportActionBar().setTitle(R.string.SingleRowAddProfile_update);

            int id = getIntent().getIntExtra(SingleRowProfileList.KEY_UPDATE_ID, 0);
            currentCamera = AppController.getInstance().getsqliteDbInstance().getSingleRow(id);
            // txtTitle.setText("Update Camera Profile");
            ed_single_row_name.setText(currentCamera.getSingleRowName());
            ed_sr_num_of_shooting_position.setText(currentCamera.getNumber_of_shooting_position() + "");
            ed_sr_num_of_bracketed_shot.setText(currentCamera.getNumber_of_bracketed_shot() + "");

            list_bracketing_style.add(currentCamera.getBracketed_style());

            for (int i = 0; i < AppConstant.bracketing_style.length; i++) {

                if (list_bracketing_style.contains(AppConstant.bracketing_style[i])) continue;

                list_bracketing_style.add(AppConstant.bracketing_style[i]);

            }

            dataAdapter.notifyDataSetChanged();

        } else {
            //txtTitle.setText("Add Camera Profile");
            currentCamera = new SingleRow();

            for (int i = 0; i < AppConstant.bracketing_style.length; i++) {

                list_bracketing_style.add(AppConstant.bracketing_style[i]);

            }
            dataAdapter.notifyDataSetChanged();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showWarningDialog()) {

                    Intent intent = new Intent();

                    currentCamera.setSigleRowName(ed_single_row_name.getText().toString());
                    currentCamera.setNumber_of_shooting_position(Integer.parseInt(ed_sr_num_of_shooting_position.getText().toString()));
                    currentCamera.setNumber_of_bracketed_shot(Integer.parseInt(ed_sr_num_of_bracketed_shot.getText().toString()));
                    currentCamera.setBracketed_style(spinner_bracketing_style.getSelectedItem().toString());
                    if (shouldUpdate) {
                        AppController.getInstance().getsqliteDbInstance().updateSingleRow(currentCamera);
                        Toast.makeText(SingleRowAddUpdateProfile.this, "Camera updated successfully", Toast.LENGTH_SHORT).show();
                        intent.putExtra("camera", getIntent().getIntExtra(SingleRowProfileList.KEY_UPDATE_POSITION, -1));
                    } else {

                        //Log.d("DEBUG",currentCamera.getSingleRowName());

                        AppController.getInstance().getsqliteDbInstance().addSingleRow(currentCamera);
                        Toast.makeText(SingleRowAddUpdateProfile.this, "SingleRow added successfully", Toast.LENGTH_SHORT).show();
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

        ed_single_row_name = (EditText) findViewById(R.id.ed_single_row_name);
        ed_sr_num_of_shooting_position = (EditText) findViewById(R.id.ed_sr_num_of_shooting_position);
        ed_sr_num_of_bracketed_shot = (EditText) findViewById(R.id.ed_sr_num_of_bracketed_shot);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        spinner_bracketing_style = (Spinner) findViewById(R.id.spinner_bracketing_style);

        list_bracketing_style = new ArrayList<>();
        dataAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_bracketing_style);

        spinner_bracketing_style.setAdapter(dataAdapter);

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (ed_sr_num_of_bracketed_shot.getText().toString().isEmpty()) {
            ed_sr_num_of_bracketed_shot.setError("Required");
            ed_sr_num_of_bracketed_shot.requestFocus();
            valid = false;
        } else {
            ed_sr_num_of_bracketed_shot.setError(null);
        }


        if (ed_sr_num_of_shooting_position.getText().toString().isEmpty()) {
            ed_sr_num_of_shooting_position.setError("Required");
            ed_sr_num_of_shooting_position.requestFocus();
            valid = false;
        } else {
            ed_sr_num_of_shooting_position.setError(null);
        }

        if (ed_single_row_name.getText().toString().isEmpty()) {
            ed_single_row_name.setError("Required");
            ed_single_row_name.requestFocus();
            valid = false;
        } else {
            ed_single_row_name.setError(null);
        }





        return valid;
    }
}
