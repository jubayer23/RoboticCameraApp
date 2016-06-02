package com.creative.roboticcameraapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.roboticcameraapp.R;

/**
 * Created by comsol on 01-Jun-16.
 */
public class Home extends Fragment {





    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        if(SavedInstanceState == null)
        {
            init();

        }else
        {

        }





    }

    private void init() {

    }


}