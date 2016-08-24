package com.creative.roboticcameraapp.fragment.multiRowStep;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.creative.roboticcameraapp.MultiRowAddUpdateProfile;
import com.creative.roboticcameraapp.MultiRowProfileList;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.MultiRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comsol on 06-Jun-16.
 */
public class MRStepThree extends Fragment implements View.OnClickListener,View.OnFocusChangeListener{

    private EditText ed_num_of_bracketed_shot, ed_after_shot_delay, ed_startup_delay, ed_focus_delay, ed_before_shot_delay, ed_speed, ed_acceleration, ed_max_frame_rate,
            ed_num_of_panoramas, ed_delay_between_panoramas, ed_shutter_length, ed_focus_signal_length, ed_camera_wakeup_signal_length, ed_camera_wakeup_delay;

    private Switch sw_camera_wakeup, sw_continuous_rotation;

    private Spinner sp_bracketing_style,sp_continuos_rotation_shutter_release,sp_return_to_start;

    private Button btn_save, btn_cancel;

    private OnDataPassStepThree dataPasser;

    //lifeCycle->
    //onCreate->onCreateView->onActivityCreated


    private List<String> list_bracketing_style,list_continuos_rotation_shutter_release,list_return_to_start;

    private ArrayAdapter<String> dataAdapterBracketingStyle,dataAdapterContinuosRotationShutter,dataAdapterReturnToStart;

    private boolean shouldUpdate;

    private int num_of_rows,panoramaWidth, num_of_bracketed_shot, after_shot_delay, startup_delay;

    private String profile_name, elevation, position, direction;

    private MultiRow multiRow; //Only be available if update is triggered

    private EditText edError;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profile_name = getArguments().getString(MultiRowAddUpdateProfile.KEY_PROFILE_NAME);
        num_of_rows = getArguments().getInt(MultiRowAddUpdateProfile.KEY_NUM_OF_ROWS);
        panoramaWidth = getArguments().getInt(MultiRowAddUpdateProfile.KEY_PANORAMA_WIDTH);
        elevation = getArguments().getString(MultiRowAddUpdateProfile.KEY_ELEVATION);
        position = getArguments().getString(MultiRowAddUpdateProfile.KEY_POSITION);
        direction = getArguments().getString(MultiRowAddUpdateProfile.KEY_DIRECTION);
        shouldUpdate = getActivity().getIntent().getBooleanExtra(MultiRowProfileList.KEY_SHOULD_UPDATE, false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mr_stepthree, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        if (SavedInstanceState == null) {
            init();

            if (shouldUpdate) {
                int id = getActivity().getIntent().getIntExtra(MultiRowProfileList.KEY_UPDATE_ID, 0);
                multiRow = AppController.getInstance().getsqliteDbInstance().getMultiRow(id);

                ed_num_of_bracketed_shot.setText(String.valueOf(multiRow.getNum_of_bracketed_shot()));
                sw_continuous_rotation.setChecked(multiRow.getContinuous_rotation() == 0 ? false : true);

                list_bracketing_style.add(multiRow.getBracketed_style());
                for (int i = 0; i < AppConstant.bracketing_style.length; i++) {
                    if (list_bracketing_style.contains(AppConstant.bracketing_style[i])) continue;
                    list_bracketing_style.add(AppConstant.bracketing_style[i]);
                }
                dataAdapterBracketingStyle.notifyDataSetChanged();

                list_continuos_rotation_shutter_release.add(multiRow.getContinuosRotationShutter());
                for (int i = 0; i < AppConstant.contuNiousRotationShutterRelease.length; i++) {
                    if (list_continuos_rotation_shutter_release.contains(AppConstant.contuNiousRotationShutterRelease[i])) continue;
                    list_continuos_rotation_shutter_release.add(AppConstant.contuNiousRotationShutterRelease[i]);
                }
                dataAdapterContinuosRotationShutter.notifyDataSetChanged();

                list_return_to_start.add(multiRow.getReturn_to_start());
                for (int i = 0; i < AppConstant.return_to_start.length; i++) {
                    if (list_return_to_start.contains(AppConstant.return_to_start[i])) continue;
                    list_return_to_start.add(AppConstant.return_to_start[i]);
                }
                dataAdapterReturnToStart.notifyDataSetChanged();

                ed_after_shot_delay.setText(String.valueOf(multiRow.getAfter_shot_delay()));
                ed_startup_delay.setText(String.valueOf(multiRow.getStartup_delay()));
                ed_focus_delay.setText(multiRow.getFocus_delay() + "");
                ed_before_shot_delay.setText(multiRow.getBefore_shot_delay() + "");
                ed_speed.setText(multiRow.getSpeed() + "");
                ed_acceleration.setText(multiRow.getAcceleration() + "");
                ed_max_frame_rate.setText(multiRow.getMax_frame_rate() + "");
                ed_num_of_panoramas.setText(multiRow.getNum_of_panoramas() + "");
                ed_delay_between_panoramas.setText(multiRow.getDelay_between_panoramas() + "");
                ed_shutter_length.setText(multiRow.getShutter_signal_length() + "");
                ed_focus_signal_length.setText(multiRow.getFocus_signal_length() + "");
                sw_camera_wakeup.setChecked(multiRow.getCamera_wakeup() == 0 ? false : true);
                ed_camera_wakeup_signal_length.setText(multiRow.getCamera_wakeup_signal_length() + "");
                ed_camera_wakeup_delay.setText(multiRow.getCamera_wakeup_delay() + "");


            } else {
                for (int i = 0; i < AppConstant.bracketing_style.length; i++) {
                    list_bracketing_style.add(AppConstant.bracketing_style[i]);
                }
                dataAdapterBracketingStyle.notifyDataSetChanged();

                for (int i = 0; i < AppConstant.contuNiousRotationShutterRelease.length; i++) {
                    list_continuos_rotation_shutter_release.add(AppConstant.contuNiousRotationShutterRelease[i]);
                }
                dataAdapterContinuosRotationShutter.notifyDataSetChanged();

                for (int i = 0; i < AppConstant.return_to_start.length; i++) {
                    list_return_to_start.add(AppConstant.return_to_start[i]);
                }
                dataAdapterReturnToStart.notifyDataSetChanged();

                multiRow = new MultiRow();
            }


        } else {

        }


    }

    private void init() {

        ed_num_of_bracketed_shot = (EditText) getActivity().findViewById(R.id.ed_num_of_bracketed_shot);
        ed_num_of_bracketed_shot.setOnFocusChangeListener(this);
        ed_after_shot_delay = (EditText) getActivity().findViewById(R.id.ed_after_delay_shot);
        ed_after_shot_delay.setOnFocusChangeListener(this);
        ed_startup_delay = (EditText) getActivity().findViewById(R.id.ed_startup_delay);
        ed_startup_delay.setOnFocusChangeListener(this);
        ed_focus_delay = (EditText) getActivity().findViewById(R.id.ed_focus_delay);
        ed_focus_delay.setOnFocusChangeListener(this);
        ed_before_shot_delay = (EditText) getActivity().findViewById(R.id.ed_before_shot_delay);
        ed_before_shot_delay.setOnFocusChangeListener(this);
        ed_speed = (EditText) getActivity().findViewById(R.id.ed_speed);
        ed_speed.setOnFocusChangeListener(this);
        ed_acceleration = (EditText) getActivity().findViewById(R.id.ed_acceleration);
        ed_acceleration.setOnFocusChangeListener(this);
        ed_max_frame_rate = (EditText) getActivity().findViewById(R.id.ed_max_frame_rate);
        ed_max_frame_rate.setOnFocusChangeListener(this);
        ed_num_of_panoramas = (EditText) getActivity().findViewById(R.id.ed_num_of_panoramas);
        ed_num_of_panoramas.setOnFocusChangeListener(this);
        ed_delay_between_panoramas = (EditText) getActivity().findViewById(R.id.ed_delay_between_panoramas);
        ed_delay_between_panoramas.setOnFocusChangeListener(this);
        ed_shutter_length = (EditText) getActivity().findViewById(R.id.ed_shutter_signal_length);
        ed_shutter_length.setOnFocusChangeListener(this);
        ed_focus_signal_length = (EditText) getActivity().findViewById(R.id.ed_focus_signal_length);
        ed_focus_signal_length.setOnFocusChangeListener(this);
        ed_camera_wakeup_signal_length = (EditText) getActivity().findViewById(R.id.ed_camera_wakeup_signal_length);
        ed_camera_wakeup_signal_length.setOnFocusChangeListener(this);
        ed_camera_wakeup_delay = (EditText) getActivity().findViewById(R.id.ed_camera_wakeup_delay);
        ed_camera_wakeup_delay.setOnFocusChangeListener(this);


        sw_continuous_rotation = (Switch) getActivity().findViewById(R.id.sw_continious_rotation);
        if (AppConstant.DEFAULT_CONTINUOUS_ROTATION) sw_continuous_rotation.setChecked(true);
        sw_camera_wakeup = (Switch) getActivity().findViewById(R.id.sw_camera_wakeup);
        if (AppConstant.DEFAULT_CAMERA_WAKEUP) sw_camera_wakeup.setChecked(true);


        btn_save = (Button) getActivity().findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_cancel = (Button) getActivity().findViewById(R.id.btn_cancel_3);
        btn_cancel.setOnClickListener(this);


        sp_bracketing_style = (Spinner) getActivity().findViewById(R.id.spinner_bracketing_style);
        list_bracketing_style = new ArrayList<>();
        dataAdapterBracketingStyle = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, list_bracketing_style);
        sp_bracketing_style.setAdapter(dataAdapterBracketingStyle);

        sp_continuos_rotation_shutter_release = (Spinner) getActivity().findViewById(R.id.sp_continuos_rotation_shutter_release);
        list_continuos_rotation_shutter_release = new ArrayList<>();
        dataAdapterContinuosRotationShutter = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, list_continuos_rotation_shutter_release);

        sp_continuos_rotation_shutter_release.setAdapter(dataAdapterContinuosRotationShutter);

        sp_return_to_start = (Spinner) getActivity().findViewById(R.id.sp_return_to_start);
        list_return_to_start = new ArrayList<>();
        dataAdapterReturnToStart = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, list_return_to_start);

        sp_return_to_start.setAdapter(dataAdapterReturnToStart);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
            dataPasser = (OnDataPassStepThree) a;
        }

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_save) {

            if (showWarningDialog()) {
                multiRow.setMultiRowName(profile_name);
                multiRow.setNum_of_rows(num_of_rows);
                multiRow.setElevation(elevation);
                multiRow.setPosition(position);
                multiRow.setDirection(direction);
                multiRow.setPanorama_width(panoramaWidth);

                multiRow.setContinuous_rotation(sw_continuous_rotation.isChecked() ? 1 : 0);
                multiRow.setReturn_to_start(sp_return_to_start.getSelectedItem().toString());

                multiRow.setBracketed_style(sp_bracketing_style.getSelectedItem().toString());
                multiRow.setContinuosRotationShutter(sp_continuos_rotation_shutter_release.getSelectedItem().toString());

                if (ed_num_of_bracketed_shot.getText().toString().isEmpty()) {
                    multiRow.setNum_of_bracketed_shot(AppConstant.DEFAULT_NUM_OF_BRACKETED_SHOTS);
                } else {
                    multiRow.setNum_of_bracketed_shot(Integer.parseInt(ed_num_of_bracketed_shot.getText().toString()));
                }
                if (ed_after_shot_delay.getText().toString().isEmpty()) {
                    multiRow.setAfter_shot_delay(AppConstant.DEFAULT_AFTER_SHOT_DELAY);
                } else {
                    multiRow.setAfter_shot_delay(Integer.parseInt(ed_after_shot_delay.getText().toString()));
                }
                if (ed_startup_delay.getText().toString().isEmpty()) {
                    multiRow.setStartup_delay(AppConstant.DEFAULT_STARTUP_DELAY);
                } else {
                    multiRow.setStartup_delay(Integer.parseInt(ed_startup_delay.getText().toString()));
                }
                if (ed_focus_delay.getText().toString().isEmpty()) {
                    multiRow.setFocus_delay(AppConstant.DEFAULT_FOCUS_DELAY);
                } else {
                    multiRow.setFocus_delay(Integer.parseInt(ed_focus_delay.getText().toString()));
                }
                if (ed_before_shot_delay.getText().toString().isEmpty()) {
                    multiRow.setBefore_shot_delay(AppConstant.DEFAULT_BEFORE_SHOT_DELAY);
                } else {
                    multiRow.setBefore_shot_delay(Integer.parseInt(ed_before_shot_delay.getText().toString()));
                }


                if (ed_speed.getText().toString().isEmpty()) {
                    multiRow.setSpeed(AppConstant.DEFAULT_SPEED);
                } else {
                    multiRow.setSpeed(Integer.parseInt(ed_speed.getText().toString()));
                }
                if (ed_acceleration.getText().toString().isEmpty()) {
                    multiRow.setAcceleration(AppConstant.DEFAULT_ACCELERATION);
                } else {
                    multiRow.setAcceleration(Integer.parseInt(ed_acceleration.getText().toString()));
                }
                if (ed_max_frame_rate.getText().toString().isEmpty()) {
                    multiRow.setMax_frame_rate(AppConstant.DEFAULT_MAX_FRAME_RATE);
                } else {
                    multiRow.setMax_frame_rate(Float.parseFloat(ed_max_frame_rate.getText().toString()));
                }
                if (ed_num_of_panoramas.getText().toString().isEmpty()) {
                    multiRow.setNum_of_panoramas(AppConstant.DEFAULT_NUM_OF_PANORAMAS);
                } else {
                    multiRow.setNum_of_panoramas(Integer.parseInt(ed_num_of_panoramas.getText().toString()));
                }
                if (ed_delay_between_panoramas.getText().toString().isEmpty()) {
                    multiRow.setDelay_between_panoramas(AppConstant.DEFAULT_DELAY_BETWEEN_PANORAMAS);
                } else {
                    multiRow.setDelay_between_panoramas(Integer.parseInt(ed_delay_between_panoramas.getText().toString()));
                }
                if (ed_shutter_length.getText().toString().isEmpty()) {
                    multiRow.setShutter_signal_length(AppConstant.DEFAULT_SHUTTER_SIGNAL_LENGTH);
                } else {
                    multiRow.setShutter_signal_length(Integer.parseInt(ed_shutter_length.getText().toString()));
                }

                if (ed_focus_signal_length.getText().toString().isEmpty()) {
                    multiRow.setFocus_signal_length(AppConstant.DEFAULT_FOCUS_SIGNAL_LENGTH);
                } else {
                    multiRow.setFocus_signal_length(Integer.parseInt(ed_focus_signal_length.getText().toString()));
                }

                multiRow.setCamera_wakeup(sw_camera_wakeup.isChecked() ? 1 : 0);

                if (ed_camera_wakeup_signal_length.getText().toString().isEmpty()) {
                    multiRow.setCamera_wakeup_signal_length(AppConstant.DEFAULT_CAMERA_WAKEUP_SIGNAL_LENGTH);
                } else {
                    multiRow.setCamera_wakeup_signal_length(Integer.parseInt(ed_camera_wakeup_signal_length.getText().toString()));
                }
                if (ed_camera_wakeup_delay.getText().toString().isEmpty()) {
                    multiRow.setCamera_wakeup_delay(AppConstant.DEFAULT_CAMERA_WAKEUP_DELAY);
                } else {
                    multiRow.setCamera_wakeup_delay(Integer.parseInt(ed_camera_wakeup_delay.getText().toString()));
                }

                if (shouldUpdate) {

                    AppController.getInstance().getsqliteDbInstance().updateMultiRow(multiRow);
                    Toast.makeText(getActivity(), "MultiRow updated successfully", Toast.LENGTH_SHORT).show();

                } else {
                    AppController.getInstance().getsqliteDbInstance().addMultiRow(multiRow);
                    Toast.makeText(getActivity(), "MultiRow added successfully", Toast.LENGTH_SHORT).show();

                }


                dataPasser.onDataPassStepThree();

            }

        }

        if (id == R.id.btn_cancel_3) {
            dataPasser.onBackPressedAtStepThree();

        }

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
            double value = Double.parseDouble(ed_max_frame_rate.getText().toString());
            String temp = "12";
            if (String.valueOf(value).contains(".")) {
                temp = String.valueOf(value).substring(String.valueOf(value).indexOf(".") + 1);
            }
            if (temp.length() > 2) {
                ed_max_frame_rate.setError("Invalid input");
                ed_max_frame_rate.requestFocus();
                valid = false;
            } else {
                if (value < 0 || value > 20) {
                    ed_max_frame_rate.setError("Invalid input");
                    ed_max_frame_rate.requestFocus();
                    valid = false;
                } else {
                    ed_max_frame_rate.setError(null);
                }
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


        return valid;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            int id = view.getId();
            //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();

            switch (id) {

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
                            edError =ed_acceleration;
                        } else {
                            edError = null;
                            ed_acceleration.setError(null);
                        }
                    }
                    break;
                case R.id.ed_max_frame_rate:
                    if (!ed_max_frame_rate.getText().toString().isEmpty()) {
                        double value = Double.parseDouble(ed_max_frame_rate.getText().toString());
                        String temp = "12";
                        if (String.valueOf(value).contains(".")) {
                            temp = String.valueOf(value).substring(String.valueOf(value).indexOf(".") + 1);
                        }
                        if (temp.length() > 2) {
                            ed_max_frame_rate.setError("Invalid input");
                            //  ed_max_frame_rate.requestFocus();
                            edError = ed_max_frame_rate;
                        } else {
                            if (value < 0 || value > 20) {
                                ed_max_frame_rate.setError("Invalid input");
                                //  ed_max_frame_rate.requestFocus();
                                edError = ed_max_frame_rate;
                            } else {
                                edError = null;
                                ed_max_frame_rate.setError(null);
                            }
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

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public interface OnDataPassStepThree {
        public void onDataPassStepThree();

        public void onBackPressedAtStepThree();
    }
}
