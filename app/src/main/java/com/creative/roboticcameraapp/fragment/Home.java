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
    private static final int PICKFILE_RESULT_CODE = 501;
    private LinearLayout btn_single_row, btn_multi_row, btn_partial_row, btn_connect, btn_adhoc, btn_expot_import;
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


        btn_expot_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExportImportDialog();
            }
        });


    }


    private void init() {

        btn_single_row = (LinearLayout) getActivity().findViewById(R.id.btn_single_row);
        btn_multi_row = (LinearLayout) getActivity().findViewById(R.id.btn_multi_row);
        btn_partial_row = (LinearLayout) getActivity().findViewById(R.id.btn_partial_row);
        btn_connect = (LinearLayout) getActivity().findViewById(R.id.btn_connect);
        btn_adhoc = (LinearLayout) getActivity().findViewById(R.id.btn_adhoc);
        btn_expot_import = (LinearLayout) getActivity().findViewById(R.id.btn_export_import);
        tv_connect = (TextView) getActivity().findViewById(R.id.tv_connect);

    }

    public static void changeStatus(String status) {
        tv_connect.setText(status);
    }

    private void showExportImportDialog() {

        final Dialog dialog = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_for_export_import);


        Button btn_export = (Button) dialog.findViewById(R.id.btn_export);

        Button btn_import = (Button) dialog.findViewById(R.id.btn_import);

        Button btn_date = (Button) dialog.findViewById(R.id.btn_date);

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //export();
                // getActivity().deleteDatabase(DbConfig.DB_NAME);
                if(DbExportImport.exportDb()){
                    Toast.makeText(getActivity(), "Successfuly Export", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Failed Export", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("gagt/sdf");
                try {
                    startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No activity can handle picking a file. Showing alternatives.", Toast.LENGTH_LONG).show();
                }

                //importDB();

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    String FilePath = data.getData().getPath();

                    if (DbExportImport.restoreDb(FilePath)) {
                        Toast.makeText(getActivity(), "Successfuly Import", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), "Failed Import", Toast.LENGTH_LONG).show();
                    }

                    //FilePath is your file as a string
                }
        }
    }

    private void export() {

        try {
            // Log.d("DEBUG", "yes1");

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            // Log.d("DEBUG", String.valueOf(sd));
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getActivity().getPackageName() + "//databases//" + DbConfig.DB_NAME;
                String backupDBPath = DbConfig.DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                // Log.d("DEBUG", "yes2");

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getActivity(), "Export Successfully", Toast.LENGTH_LONG).show();

                    //getActivity().deleteDatabase(DbConfig.DB_NAME);
                    // Log.d("DEBUG", backupDB.getAbsolutePath());
                }
            }
        } catch (Exception e) {
        }


    }

    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//" + getActivity().getPackageName() + "//databases//" + DbConfig.DB_NAME;
                String backupDBPath = DbConfig.DB_NAME;
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                AppController.getInstance().getsqliteDbInstance().open();
                Toast.makeText(getActivity(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
}