package com.creative.roboticcameraapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.creative.roboticcameraapp.R;

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
public class Setting extends Fragment implements View.OnClickListener{

    private Button btn_camera, btn_lense, btn_shoting_profile;


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

        btn_camera = (Button)getActivity().findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);

        btn_lense = (Button)getActivity().findViewById(R.id.btn_lense);
        btn_lense.setOnClickListener(this);

        btn_shoting_profile = (Button)getActivity().findViewById(R.id.btn_shooting_profiles);
        btn_shoting_profile.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn_camera){

        }
        if(id == R.id.btn_lense){

        }
        if(id == R.id.btn_shooting_profiles){

        }
    }
}