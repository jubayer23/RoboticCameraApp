package com.creative.roboticcameraapp.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.roboticcameraapp.AdhocInitialize;
import com.creative.roboticcameraapp.MainActivity;
import com.creative.roboticcameraapp.MultiRowProfileList;
import com.creative.roboticcameraapp.PartialGigapixelProfileList;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.SingleRowProfileList;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.database.DbConfig;
import com.creative.roboticcameraapp.database.DbExportImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


/**
 * Created by comsol on 01-Jun-16.
 */
public class Home extends Fragment {

    public static final String KEY_IS_FROM_HOME = "is_from_home";
    private LinearLayout btn_single_row, btn_multi_row, btn_partial_row, btn_connect, btn_adhoc;
    public static TextView tv_connect;


    private ProgressDialog pDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        init();

        btn_single_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), SingleRowProfileList.class);
                intent.putExtra(KEY_IS_FROM_HOME, true);
                startActivity(intent);

            }
        });

        btn_multi_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), MultiRowProfileList.class);
                intent.putExtra(KEY_IS_FROM_HOME, true);
                startActivity(intent);

            }
        });

        btn_partial_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), PartialGigapixelProfileList.class);
                intent.putExtra(KEY_IS_FROM_HOME, true);
                startActivity(intent);

            }
        });

        btn_adhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), AdhocInitialize.class);
                //intent.putExtra(KEY_IS_FROM_HOME, true);
                startActivity(intent);

            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).MakeConnection();


            }
        });




    }


    private void init() {

        btn_single_row = (LinearLayout) getActivity().findViewById(R.id.btn_single_row);
        btn_multi_row = (LinearLayout) getActivity().findViewById(R.id.btn_multi_row);
        btn_partial_row = (LinearLayout) getActivity().findViewById(R.id.btn_partial_row);
        btn_connect = (LinearLayout) getActivity().findViewById(R.id.btn_connect);
        btn_adhoc = (LinearLayout) getActivity().findViewById(R.id.btn_adhoc);
        tv_connect = (TextView) getActivity().findViewById(R.id.tv_connect);

    }

    public static void changeStatus(String status) {
        tv_connect.setText(status);
    }


}