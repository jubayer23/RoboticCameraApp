package com.creative.roboticcameraapp.fragment.multiRowStep;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class MRStepThree extends Fragment implements View.OnClickListener {

    private EditText ed_num_of_bracketed_shot, ed_after_shot_delay, ed_startup_delay;

    private Spinner sp_bracketing_style;

    private Button btn_save, btn_cancel;

    private OnDataPassStepThree dataPasser;

    //lifeCycle->
    //onCreate->onCreateView->onActivityCreated


    private List<String> list_bracketing_style;

    private ArrayAdapter<String> dataAdapter;

    private boolean shouldUpdate;

    private int num_of_rows, num_of_bracketed_shot, after_shot_delay, startup_delay;

    private String profile_name, elevation, position, direction;

    private MultiRow multiRow; //Only be available if update is triggered


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profile_name = getArguments().getString(MultiRowAddUpdateProfile.KEY_PROFILE_NAME);
        num_of_rows = getArguments().getInt(MultiRowAddUpdateProfile.KEY_NUM_OF_ROWS);
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
                ed_after_shot_delay.setText(String.valueOf(multiRow.getAfter_shot_delay()));
                ed_startup_delay.setText(String.valueOf(multiRow.getStartup_delay()));

                list_bracketing_style.add(multiRow.getBacketing_style());

                for (int i = 0; i < AppConstant.bracketing_style.length; i++) {

                    if (list_bracketing_style.contains(AppConstant.bracketing_style[i])) continue;

                    list_bracketing_style.add(AppConstant.bracketing_style[i]);

                }

                dataAdapter.notifyDataSetChanged();

            } else {
                for (int i = 0; i < AppConstant.bracketing_style.length; i++) {

                    list_bracketing_style.add(AppConstant.bracketing_style[i]);

                }
                dataAdapter.notifyDataSetChanged();

                multiRow = new MultiRow();
            }


        } else {

        }


    }

    private void init() {

        ed_num_of_bracketed_shot = (EditText) getActivity().findViewById(R.id.ed_num_of_bracketed_shot);
        ed_after_shot_delay = (EditText) getActivity().findViewById(R.id.ed_after_delay_shot);
        ed_startup_delay = (EditText) getActivity().findViewById(R.id.ed_startup_delay);
        sp_bracketing_style = (Spinner) getActivity().findViewById(R.id.spinner_bracketing_style);


        btn_save = (Button) getActivity().findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_cancel = (Button) getActivity().findViewById(R.id.btn_cancel_3);
        btn_cancel.setOnClickListener(this);

        list_bracketing_style = new ArrayList<>();
        dataAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, list_bracketing_style);

        sp_bracketing_style.setAdapter(dataAdapter);

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
                multiRow.setNum_of_bracketed_shot(Integer.parseInt(ed_num_of_bracketed_shot.getText().toString()));
                multiRow.setBacketing_style(sp_bracketing_style.getSelectedItem().toString());
                multiRow.setAfter_shot_delay(Integer.parseInt(ed_after_shot_delay.getText().toString()));
                multiRow.setStartup_delay(Integer.parseInt(ed_startup_delay.getText().toString()));

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

        if (ed_startup_delay.getText().toString().isEmpty()) {
            ed_startup_delay.setError("Required");
            ed_startup_delay.requestFocus();
            valid = false;
        } else {
            ed_startup_delay.setError(null);
        }

        if (ed_after_shot_delay.getText().toString().isEmpty()) {
            ed_after_shot_delay.setError("Required");
            ed_after_shot_delay.requestFocus();
            valid = false;
        } else {
            ed_after_shot_delay.setError(null);
        }

        if (ed_num_of_bracketed_shot.getText().toString().isEmpty()) {
            ed_num_of_bracketed_shot.setError("Required");
            ed_num_of_bracketed_shot.requestFocus();
            valid = false;
        } else {
            ed_num_of_bracketed_shot.setError(null);
        }


        return valid;
    }

    public interface OnDataPassStepThree {
        public void onDataPassStepThree();

        public void onBackPressedAtStepThree();
    }
}
