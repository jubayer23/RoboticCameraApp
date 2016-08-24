package com.creative.roboticcameraapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.Camera;
import com.creative.roboticcameraapp.model.Lens;
import com.creative.roboticcameraapp.model.Partial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class PartialGigapixelAddUpdateProfile extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText ed_profile_name, ed_overlap, ed_num_of_bracketed_shot,
            ed_after_shot_delay, ed_startup_delay, ed_focus_delay, ed_before_shot_delay, ed_speed, ed_acceleration, ed_max_frame_rate,
            ed_num_of_panoramas, ed_delay_between_panoramas, ed_shutter_length, ed_focus_signal_length, ed_camera_wakeup_signal_length, ed_camera_wakeup_delay;

    private Switch sw_camera_wakeup, sw_continuous_rotation, sw_return_to_start;

    private Button btn_save, btn_cancel;

    private boolean shouldUpdate;

    private Partial currentCamera; //Only be available if update is triggered

    private Spinner sp_camera_name, sp_lens_name, sp_bracketing_style, sp_direction, sp_continuos_rotation_shutter_release;

    private List<String> list_camera, list_lens, list_bracketing_style, list_direction, list_continuos_rotation_shutter_release;

    private ArrayAdapter<String> dataAdapterCamera, dataAdapterLens, dataAdapterBracketingStyle, dataAdapterDirection, dataAdapterContinuosRotationShutter;

    private EditText edError;

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


            list_camera.add(currentCamera.getCameraName());
            List<Camera> cameras = AppController.getInstance().getsqliteDbInstance().getAllCamera();
            for (int i = 0; i < cameras.size(); i++) {
                if (list_camera.contains(cameras.get(i).getCameraName())) continue;
                list_camera.add(cameras.get(i).getCameraName());
            }
            dataAdapterCamera.notifyDataSetChanged();


            list_lens.add(currentCamera.getLensName());
            List<Lens> lens = AppController.getInstance().getsqliteDbInstance().getAllLenses();
            for (int i = 0; i < lens.size(); i++) {
                if (list_lens.contains(lens.get(i).getLensName())) continue;
                list_lens.add(lens.get(i).getLensName());
            }
            dataAdapterLens.notifyDataSetChanged();


            ed_overlap.setText(currentCamera.getOverlap() + "");
            sw_continuous_rotation.setChecked(currentCamera.getContinuous_rotation() == 0 ? false : true);
            sw_return_to_start.setChecked(currentCamera.getReturn_to_start() == 0 ? false : true);
            ed_num_of_bracketed_shot.setText(currentCamera.getNum_of_bracketed_shot() + "");

            list_bracketing_style.add(currentCamera.getBracketed_style());
            for (int i = 0; i < AppConstant.bracketing_style.length; i++) {
                if (list_bracketing_style.contains(AppConstant.bracketing_style[i])) continue;
                list_bracketing_style.add(AppConstant.bracketing_style[i]);
            }
            dataAdapterBracketingStyle.notifyDataSetChanged();

            ed_after_shot_delay.setText(currentCamera.getAfter_shot_delay() + "");
            ed_startup_delay.setText(currentCamera.getStartup_delay() + "");
            ed_focus_delay.setText(currentCamera.getFocus_delay() + "");
            ed_before_shot_delay.setText(currentCamera.getBefore_shot_delay() + "");


            list_direction.add(currentCamera.getDirection());
            for (int i = 0; i < AppConstant.direction.length; i++) {
                if (list_direction.contains(AppConstant.direction[i])) continue;
                list_direction.add(AppConstant.direction[i]);
            }
            dataAdapterDirection.notifyDataSetChanged();

            list_continuos_rotation_shutter_release.add(currentCamera.getContinuosRotationShutter());
            for (int i = 0; i < AppConstant.contuNiousRotationShutterRelease.length; i++) {
                if (list_continuos_rotation_shutter_release.contains(AppConstant.contuNiousRotationShutterRelease[i]))
                    continue;
                list_continuos_rotation_shutter_release.add(AppConstant.contuNiousRotationShutterRelease[i]);
            }
            dataAdapterContinuosRotationShutter.notifyDataSetChanged();

            ed_speed.setText(currentCamera.getSpeed() + "");
            ed_acceleration.setText(currentCamera.getAcceleration() + "");
            ed_max_frame_rate.setText(currentCamera.getMax_frame_rate() + "");
            ed_num_of_panoramas.setText(currentCamera.getNum_of_panoramas() + "");
            ed_delay_between_panoramas.setText(currentCamera.getDelay_between_panoramas() + "");
            ed_shutter_length.setText(currentCamera.getShutter_signal_length() + "");
            ed_focus_signal_length.setText(currentCamera.getFocus_signal_length() + "");
            sw_camera_wakeup.setChecked(currentCamera.getCamera_wakeup() == 0 ? false : true);
            ed_camera_wakeup_signal_length.setText(currentCamera.getCamera_wakeup_signal_length() + "");
            ed_camera_wakeup_delay.setText(currentCamera.getCamera_wakeup_delay() + "");

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

            for (int i = 0; i < AppConstant.bracketing_style.length; i++) {
                list_bracketing_style.add(AppConstant.bracketing_style[i]);
            }
            dataAdapterBracketingStyle.notifyDataSetChanged();

            for (int i = 0; i < AppConstant.direction.length; i++) {
                list_direction.add(AppConstant.direction[i]);
            }
            dataAdapterDirection.notifyDataSetChanged();

            for (int i = 0; i < AppConstant.contuNiousRotationShutterRelease.length; i++) {
                list_continuos_rotation_shutter_release.add(AppConstant.contuNiousRotationShutterRelease[i]);
            }
            dataAdapterContinuosRotationShutter.notifyDataSetChanged();


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

                    currentCamera.setContinuous_rotation(sw_continuous_rotation.isChecked() ? 1 : 0);
                    currentCamera.setReturn_to_start(sw_return_to_start.isChecked() ? 1 : 0);
                    currentCamera.setBracketed_style(sp_bracketing_style.getSelectedItem().toString());
                    if (ed_num_of_bracketed_shot.getText().toString().isEmpty()) {
                        currentCamera.setNum_of_bracketed_shot(AppConstant.DEFAULT_NUM_OF_BRACKETED_SHOTS);
                    } else {
                        currentCamera.setNum_of_bracketed_shot(Integer.parseInt(ed_num_of_bracketed_shot.getText().toString()));
                    }
                    if (ed_after_shot_delay.getText().toString().isEmpty()) {
                        currentCamera.setAfter_shot_delay(AppConstant.DEFAULT_AFTER_SHOT_DELAY);
                    } else {
                        currentCamera.setAfter_shot_delay(Integer.parseInt(ed_after_shot_delay.getText().toString()));
                    }
                    if (ed_startup_delay.getText().toString().isEmpty()) {
                        currentCamera.setStartup_delay(AppConstant.DEFAULT_STARTUP_DELAY);
                    } else {
                        currentCamera.setStartup_delay(Integer.parseInt(ed_startup_delay.getText().toString()));
                    }
                    if (ed_focus_delay.getText().toString().isEmpty()) {
                        currentCamera.setFocus_delay(AppConstant.DEFAULT_FOCUS_DELAY);
                    } else {
                        currentCamera.setFocus_delay(Integer.parseInt(ed_focus_delay.getText().toString()));
                    }
                    if (ed_before_shot_delay.getText().toString().isEmpty()) {
                        currentCamera.setBefore_shot_delay(AppConstant.DEFAULT_BEFORE_SHOT_DELAY);
                    } else {
                        currentCamera.setBefore_shot_delay(Integer.parseInt(ed_before_shot_delay.getText().toString()));
                    }

                    currentCamera.setDirection(sp_direction.getSelectedItem().toString());

                    currentCamera.setContinuosRotationShutter(sp_continuos_rotation_shutter_release.getSelectedItem().toString());


                    if (ed_speed.getText().toString().isEmpty()) {
                        currentCamera.setSpeed(AppConstant.DEFAULT_SPEED);
                    } else {
                        currentCamera.setSpeed(Integer.parseInt(ed_speed.getText().toString()));
                    }
                    if (ed_acceleration.getText().toString().isEmpty()) {
                        currentCamera.setAcceleration(AppConstant.DEFAULT_ACCELERATION);
                    } else {
                        currentCamera.setAcceleration(Integer.parseInt(ed_acceleration.getText().toString()));
                    }
                    if (ed_max_frame_rate.getText().toString().isEmpty()) {
                        currentCamera.setMax_frame_rate(AppConstant.DEFAULT_MAX_FRAME_RATE);
                    } else {
                        currentCamera.setMax_frame_rate(Integer.parseInt(ed_max_frame_rate.getText().toString()));
                    }
                    if (ed_num_of_panoramas.getText().toString().isEmpty()) {
                        currentCamera.setNum_of_panoramas(AppConstant.DEFAULT_NUM_OF_PANORAMAS);
                    } else {
                        currentCamera.setNum_of_panoramas(Integer.parseInt(ed_num_of_panoramas.getText().toString()));
                    }
                    if (ed_delay_between_panoramas.getText().toString().isEmpty()) {
                        currentCamera.setDelay_between_panoramas(AppConstant.DEFAULT_DELAY_BETWEEN_PANORAMAS);
                    } else {
                        currentCamera.setDelay_between_panoramas(Integer.parseInt(ed_delay_between_panoramas.getText().toString()));
                    }
                    if (ed_shutter_length.getText().toString().isEmpty()) {
                        currentCamera.setShutter_signal_length(AppConstant.DEFAULT_SHUTTER_SIGNAL_LENGTH);
                    } else {
                        currentCamera.setShutter_signal_length(Integer.parseInt(ed_shutter_length.getText().toString()));
                    }

                    if (ed_focus_signal_length.getText().toString().isEmpty()) {
                        currentCamera.setFocus_signal_length(AppConstant.DEFAULT_FOCUS_SIGNAL_LENGTH);
                    } else {
                        currentCamera.setFocus_signal_length(Integer.parseInt(ed_focus_signal_length.getText().toString()));
                    }

                    currentCamera.setCamera_wakeup(sw_camera_wakeup.isChecked() ? 1 : 0);

                    if (ed_camera_wakeup_signal_length.getText().toString().isEmpty()) {
                        currentCamera.setCamera_wakeup_signal_length(AppConstant.DEFAULT_CAMERA_WAKEUP_SIGNAL_LENGTH);
                    } else {
                        currentCamera.setCamera_wakeup_signal_length(Integer.parseInt(ed_camera_wakeup_signal_length.getText().toString()));
                    }
                    if (ed_camera_wakeup_delay.getText().toString().isEmpty()) {
                        currentCamera.setCamera_wakeup_delay(AppConstant.DEFAULT_CAMERA_WAKEUP_DELAY);
                    } else {
                        currentCamera.setCamera_wakeup_delay(Integer.parseInt(ed_camera_wakeup_delay.getText().toString()));
                    }
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
        ed_overlap.setOnFocusChangeListener(this);

        ed_num_of_bracketed_shot = (EditText) findViewById(R.id.ed_num_of_bracketed_shot);
        ed_num_of_bracketed_shot.setOnFocusChangeListener(this);
        ed_after_shot_delay = (EditText) findViewById(R.id.ed_after_delay_shot);
        ed_after_shot_delay.setOnFocusChangeListener(this);
        ed_startup_delay = (EditText) findViewById(R.id.ed_startup_delay);
        ed_startup_delay.setOnFocusChangeListener(this);
        ed_focus_delay = (EditText) findViewById(R.id.ed_focus_delay);
        ed_focus_delay.setOnFocusChangeListener(this);
        ed_before_shot_delay = (EditText) findViewById(R.id.ed_before_shot_delay);
        ed_before_shot_delay.setOnFocusChangeListener(this);
        ed_speed = (EditText) findViewById(R.id.ed_speed);
        ed_speed.setOnFocusChangeListener(this);
        ed_acceleration = (EditText) findViewById(R.id.ed_acceleration);
        ed_acceleration.setOnFocusChangeListener(this);
        ed_max_frame_rate = (EditText) findViewById(R.id.ed_max_frame_rate);
        ed_max_frame_rate.setOnFocusChangeListener(this);
        ed_num_of_panoramas = (EditText) findViewById(R.id.ed_num_of_panoramas);
        ed_num_of_panoramas.setOnFocusChangeListener(this);
        ed_delay_between_panoramas = (EditText) findViewById(R.id.ed_delay_between_panoramas);
        ed_delay_between_panoramas.setOnFocusChangeListener(this);
        ed_shutter_length = (EditText) findViewById(R.id.ed_shutter_signal_length);
        ed_shutter_length.setOnFocusChangeListener(this);
        ed_focus_signal_length = (EditText) findViewById(R.id.ed_focus_signal_length);
        ed_focus_signal_length.setOnFocusChangeListener(this);
        ed_camera_wakeup_signal_length = (EditText) findViewById(R.id.ed_camera_wakeup_signal_length);
        ed_camera_wakeup_signal_length.setOnFocusChangeListener(this);
        ed_camera_wakeup_delay = (EditText) findViewById(R.id.ed_camera_wakeup_delay);
        ed_camera_wakeup_delay.setOnFocusChangeListener(this);

        sw_continuous_rotation = (Switch) findViewById(R.id.sw_continious_rotation);
        if (AppConstant.DEFAULT_CONTINUOUS_ROTATION) sw_continuous_rotation.setChecked(true);
        sw_camera_wakeup = (Switch) findViewById(R.id.sw_camera_wakeup);
        if (AppConstant.DEFAULT_CAMERA_WAKEUP) sw_camera_wakeup.setChecked(true);
        sw_return_to_start = (Switch) findViewById(R.id.sw_return_to_start);
        if (AppConstant.DEFAULT_RETURN_TO_START) sw_return_to_start.setChecked(true);

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


        sp_bracketing_style = (Spinner) findViewById(R.id.spinner_bracketing_style);
        list_bracketing_style = new ArrayList<>();
        dataAdapterBracketingStyle = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_bracketing_style);

        sp_bracketing_style.setAdapter(dataAdapterBracketingStyle);

        sp_direction = (Spinner) findViewById(R.id.sp_direction);
        list_direction = new ArrayList<>();
        dataAdapterDirection = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_direction);

        sp_direction.setAdapter(dataAdapterDirection);

        sp_continuos_rotation_shutter_release = (Spinner) findViewById(R.id.sp_continuos_rotation_shutter_release);
        list_continuos_rotation_shutter_release = new ArrayList<>();
        dataAdapterContinuosRotationShutter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, list_continuos_rotation_shutter_release);

        sp_continuos_rotation_shutter_release.setAdapter(dataAdapterContinuosRotationShutter);

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (!ed_camera_wakeup_delay.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_camera_wakeup_delay.getText().toString());
            if (value < 100 || value > 5000) {
                ed_camera_wakeup_delay.setError("Invalid input");
                ed_camera_wakeup_delay.requestFocus();
                valid = false;
            } else {
                ed_camera_wakeup_delay.setError(null);
            }
        }

        if (!ed_camera_wakeup_signal_length.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_camera_wakeup_signal_length.getText().toString());
            if (value < 100 || value > 1000) {
                ed_camera_wakeup_signal_length.setError("Invalid input");
                ed_camera_wakeup_signal_length.requestFocus();
                valid = false;
            } else {
                ed_camera_wakeup_signal_length.setError(null);
            }
        }
        if (!ed_focus_signal_length.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_focus_signal_length.getText().toString());
            if (value < 100 || value > 1000) {
                ed_focus_signal_length.setError("Invalid input");
                ed_focus_signal_length.requestFocus();
                valid = false;
            } else {
                ed_focus_signal_length.setError(null);
            }
        }
        if (!ed_shutter_length.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_shutter_length.getText().toString());
            if (value < 100 || value > 1000) {
                ed_shutter_length.setError("Invalid input");
                ed_shutter_length.requestFocus();
                valid = false;
            } else {
                ed_shutter_length.setError(null);
            }
        }

        if (!ed_delay_between_panoramas.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_delay_between_panoramas.getText().toString());
            if (value < 0 || value > 64000) {
                ed_delay_between_panoramas.setError("Invalid input");
                ed_delay_between_panoramas.requestFocus();
                valid = false;
            } else {
                ed_delay_between_panoramas.setError(null);
            }
        }
        if (!ed_num_of_panoramas.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_num_of_panoramas.getText().toString());
            if (value < 0 || value > 64000) {
                ed_num_of_panoramas.setError("Invalid input");
                ed_num_of_panoramas.requestFocus();
                valid = false;
            } else {
                ed_num_of_panoramas.setError(null);
            }
        }
        if (!ed_max_frame_rate.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_max_frame_rate.getText().toString());
            if (value < 0 || value > 20) {
                ed_max_frame_rate.setError("Invalid input");
                ed_max_frame_rate.requestFocus();
                valid = false;
            } else {
                ed_max_frame_rate.setError(null);
            }
        }
        if (!ed_acceleration.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_acceleration.getText().toString());
            if (value < 0 || value > 10000) {
                ed_acceleration.setError("Invalid input");
                ed_acceleration.requestFocus();
                valid = false;
            } else {
                ed_acceleration.setError(null);
            }
        }
        if (!ed_speed.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_speed.getText().toString());
            if (value < 1 || value > 64000) {
                ed_speed.setError("Invalid input");
                ed_speed.requestFocus();
                valid = false;
            } else {
                ed_speed.setError(null);
            }
        }
        if (!ed_before_shot_delay.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_before_shot_delay.getText().toString());
            if (value < -1 || value > 32000) {
                ed_before_shot_delay.setError("Invalid input");
                ed_before_shot_delay.requestFocus();
                valid = false;
            } else {
                ed_before_shot_delay.setError(null);
            }
        }

        if (!ed_focus_delay.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_focus_delay.getText().toString());
            if (value < 0 || value > 64000) {
                ed_focus_delay.setError("Invalid input");
                ed_focus_delay.requestFocus();
                valid = false;
            } else {
                ed_focus_delay.setError(null);
            }
        }
        if (!ed_startup_delay.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_startup_delay.getText().toString());
            if (value < 0 || value > 64000) {
                ed_startup_delay.setError("Invalid input");
                ed_startup_delay.requestFocus();
                valid = false;
            } else {
                ed_startup_delay.setError(null);
            }
        }
        if (!ed_after_shot_delay.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_after_shot_delay.getText().toString());
            if (value < -1 || value > 32000) {
                ed_after_shot_delay.setError("Invalid input");
                ed_after_shot_delay.requestFocus();
                valid = false;
            } else {
                ed_after_shot_delay.setError(null);
            }
        }
        if (!ed_num_of_bracketed_shot.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_num_of_bracketed_shot.getText().toString());
            if (value < 1 || value > 30) {
                ed_num_of_bracketed_shot.setError("Invalid input");
                ed_num_of_bracketed_shot.requestFocus();
                valid = false;
            } else {
                ed_num_of_bracketed_shot.setError(null);
            }
        }


        if (!ed_overlap.getText().toString().isEmpty()) {
            long value = Long.parseLong(ed_overlap.getText().toString());
            if (value < 0 || value > 99) {
                ed_overlap.setError("Invalid input");
                ed_overlap.requestFocus();
                valid = false;
            } else {
                ed_overlap.setError(null);
            }
        } else {
            ed_overlap.setError("Required");
            ed_overlap.requestFocus();
            valid = false;
        }


        if (ed_profile_name.getText().toString().isEmpty()) {
            ed_profile_name.setError("Required");
            ed_profile_name.requestFocus();
            valid = false;
        } else {
            ed_profile_name.setError(null);
        }


        if (AppController.getInstance().getsqliteDbInstance().getAllCamera().size() <= 0) {
            valid = false;
            Toast.makeText(this, "There is no camera profile!!", Toast.LENGTH_LONG).show();
        } else if (AppController.getInstance().getsqliteDbInstance().getAllLenses().size() <= 0) {
            valid = false;
            Toast.makeText(this, "There is no Lens profile!!", Toast.LENGTH_LONG).show();
        }

        return valid;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            int id = view.getId();
            //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();

            switch (id) {

                case R.id.ed_overlap:
                    if (!ed_overlap.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_overlap.getText().toString());
                        if (value < 0 || value > 99) {
                            ed_overlap.setError("Invalid input");
                            ed_overlap.requestFocus();
                            edError = ed_overlap;
                        } else {
                            edError = null;
                            ed_overlap.setError(null);
                        }
                    }
                    break;
                case R.id.ed_num_of_bracketed_shot:
                    if (!ed_num_of_bracketed_shot.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_num_of_bracketed_shot.getText().toString());
                        if (value < 1 || value > 30) {
                            ed_num_of_bracketed_shot.setError("Invalid input");
                            //ed_sr_num_of_bracketed_shot.requestFocus();
                            edError = ed_num_of_bracketed_shot;
                        } else {
                            edError = null;
                            ed_num_of_bracketed_shot.setError(null);
                        }
                    }
                    break;
                case R.id.ed_after_delay_shot:
                    if (!ed_after_shot_delay.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_after_shot_delay.getText().toString());
                        if (value < -1 || value > 32000) {
                            ed_after_shot_delay.setError("Invalid input");
                            // ed_after_shot_delay.requestFocus();
                            edError = ed_after_shot_delay;
                        } else {
                            edError = null;
                            ed_after_shot_delay.setError(null);
                        }
                    }
                    break;
                case R.id.ed_startup_delay:
                    if (!ed_startup_delay.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_startup_delay.getText().toString());
                        if (value < 0 || value > 64000) {
                            ed_startup_delay.setError("Invalid input");
                            // ed_startup_delay.requestFocus();
                            edError = ed_startup_delay;
                        } else {
                            edError = null;
                            ed_startup_delay.setError(null);
                        }
                    }
                    break;
                case R.id.ed_focus_delay:
                    if (!ed_focus_delay.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_focus_delay.getText().toString());
                        if (value < 0 || value > 64000) {
                            ed_focus_delay.setError("Invalid input");
                            // ed_focus_delay.requestFocus();
                            edError = ed_focus_delay;
                        } else {
                            edError = null;
                            ed_focus_delay.setError(null);
                        }
                    }
                    break;
                case R.id.ed_before_shot_delay:
                    if (!ed_before_shot_delay.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_before_shot_delay.getText().toString());
                        if (value < -1 || value > 32000) {
                            ed_before_shot_delay.setError("Invalid input");
                            // ed_before_shot_delay.requestFocus();
                            edError = ed_before_shot_delay;
                        } else {
                            ed_before_shot_delay.setError(null);
                            edError = null;
                        }
                    }
                    break;
                case R.id.ed_speed:
                    if (!ed_speed.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_speed.getText().toString());
                        if (value < 1 || value > 64000) {
                            ed_speed.setError("Invalid input");
                            // ed_speed.requestFocus();
                            edError = ed_speed;
                        } else {
                            edError = null;
                            ed_speed.setError(null);
                        }
                    }
                    break;
                case R.id.ed_acceleration:
                    if (!ed_acceleration.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_acceleration.getText().toString());
                        if (value < 0 || value > 10000) {
                            ed_acceleration.setError("Invalid input");
                            // ed_acceleration.requestFocus();
                            edError = ed_acceleration;
                        } else {
                            edError = null;
                            ed_acceleration.setError(null);
                        }
                    }
                    break;
                case R.id.ed_max_frame_rate:
                    if (!ed_max_frame_rate.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_max_frame_rate.getText().toString());
                        if (value < 0 || value > 20) {
                            ed_max_frame_rate.setError("Invalid input");
                            //  ed_max_frame_rate.requestFocus();
                            edError = ed_max_frame_rate;
                        } else {
                            edError = null;
                            ed_max_frame_rate.setError(null);
                        }
                    }
                    break;
                case R.id.ed_num_of_panoramas:


                    if (!ed_num_of_panoramas.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_num_of_panoramas.getText().toString());
                        if (value < 0 || value > 64000) {
                            ed_num_of_panoramas.setError("Invalid input");
                            //  ed_num_of_panoramas.requestFocus();
                            edError = ed_num_of_panoramas;
                        } else {
                            edError = null;
                            ed_num_of_panoramas.setError(null);
                        }
                    }
                    break;
                case R.id.ed_delay_between_panoramas:
                    if (!ed_delay_between_panoramas.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_delay_between_panoramas.getText().toString());
                        if (value < 0 || value > 64000) {
                            ed_delay_between_panoramas.setError("Invalid input");
                            //   ed_delay_between_panoramas.requestFocus();
                            edError = ed_delay_between_panoramas;
                        } else {
                            edError = null;
                            ed_delay_between_panoramas.setError(null);
                        }
                    }
                    break;
                case R.id.ed_shutter_signal_length:
                    if (!ed_shutter_length.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_shutter_length.getText().toString());
                        if (value < 100 || value > 1000) {
                            ed_shutter_length.setError("Invalid input");
                            //  ed_shutter_length.requestFocus();
                            edError = ed_shutter_length;
                        } else {
                            edError = null;
                            ed_shutter_length.setError(null);
                        }
                    }
                    break;
                case R.id.ed_focus_signal_length:
                    if (!ed_focus_signal_length.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_focus_signal_length.getText().toString());
                        if (value < 100 || value > 1000) {
                            ed_focus_signal_length.setError("Invalid input");
                            //   ed_focus_signal_length.requestFocus();
                            edError = ed_focus_signal_length;
                        } else {
                            edError = null;
                            ed_focus_signal_length.setError(null);
                        }
                    }
                    break;
                case R.id.ed_camera_wakeup_signal_length:
                    if (!ed_camera_wakeup_signal_length.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_camera_wakeup_signal_length.getText().toString());
                        if (value < 100 || value > 1000) {
                            ed_camera_wakeup_signal_length.setError("Invalid input");
                            //  ed_camera_wakeup_signal_length.requestFocus();
                            edError = ed_camera_wakeup_signal_length;
                        } else {
                            edError = null;
                            ed_camera_wakeup_signal_length.setError(null);
                        }
                    }
                    break;
                case R.id.ed_camera_wakeup_delay:
                    if (!ed_camera_wakeup_delay.getText().toString().isEmpty()) {
                        long value = Long.parseLong(ed_camera_wakeup_delay.getText().toString());
                        if (value < 100 || value > 5000) {
                            ed_camera_wakeup_delay.setError("Invalid input");
                            //   ed_camera_wakeup_delay.requestFocus();
                            edError = ed_camera_wakeup_delay;
                        } else {
                            edError = null;
                            ed_camera_wakeup_delay.setError(null);
                        }
                    }

                    break;


            }
        }//else{
        //   if(edError != null){
        //       hideSoftKeyboard(SingleRowAddUpdateProfile.this, view);
        //       edError.requestFocus();
        //    }
        // }

    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
