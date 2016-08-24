package com.creative.roboticcameraapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.creative.roboticcameraapp.CameraProfileList;
import com.creative.roboticcameraapp.LensProfileList;
import com.creative.roboticcameraapp.MainActivity;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.ShootingProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by comsol on 01-Jun-16.
 */
public class Setting extends Fragment implements View.OnClickListener {

    private Button btn_camera, btn_lense, btn_shoting_profile, btn_shut_down;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        if (SavedInstanceState == null) {
            init();

        } else {

        }


    }

    private void init() {

        btn_camera = (Button) getActivity().findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);

        btn_lense = (Button) getActivity().findViewById(R.id.btn_lense);
        btn_lense.setOnClickListener(this);

        btn_shoting_profile = (Button) getActivity().findViewById(R.id.btn_shooting_profiles);
        btn_shoting_profile.setOnClickListener(this);

        btn_shut_down = (Button) getActivity().findViewById(R.id.btn_shut_down);
        btn_shut_down.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_camera) {

            Intent intent = new Intent(getActivity(), CameraProfileList.class);
            startActivity(intent);

        }
        if (id == R.id.btn_lense) {

            Intent intent = new Intent(getActivity(), LensProfileList.class);
            startActivity(intent);

        }
        if (id == R.id.btn_shooting_profiles) {

            Intent intent = new Intent(getActivity(), ShootingProfile.class);
            startActivity(intent);
        }

        if (id == R.id.btn_shut_down) {
            if (MainActivity.isConnected()) {
                // if (AppConstant.mSmoothBluetooth.isConnected()) {

                // } else {
                showDialogWarning("shutdown");
                // }
            } else {
                showDialogWarning("bluetooth");
            }
        }
    }

    private void showDialogWarning(String TAG) {
        final Dialog dialog = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bluetooth_not_connected);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);


        if(TAG.equalsIgnoreCase("shutdown")){
            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

            tv_warning.setText("Prepare for Shutdown");

            btn_ok.setVisibility(View.VISIBLE);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.sendCommand("dataStart|700|dataEnd");
                    dialog.dismiss();
                }
            });
        }




        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}