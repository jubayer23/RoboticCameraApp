package com.creative.roboticcameraapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.roboticcameraapp.CameraProfileList;
import com.creative.roboticcameraapp.LensProfileList;
import com.creative.roboticcameraapp.MainActivity;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.ShootingProfile;
import com.creative.roboticcameraapp.alertbanner.AlertDialogForAnything;
import com.creative.roboticcameraapp.database.DbExportImport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by comsol on 01-Jun-16.
 */
public class Setting extends Fragment implements View.OnClickListener {

    private Button btn_camera, btn_lense, btn_shoting_profile, btn_shut_down, btn_expot_import;

    private static final int PICKFILE_RESULT_CODE = 501;

    private static final int REQUEST_WRITE_STORAGE = 112;

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

        btn_expot_import = (Button) getActivity().findViewById(R.id.btn_export_import);
        btn_expot_import.setOnClickListener(this);
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

        if (id == R.id.btn_export_import) {
            showExportImportDialog();
        }

    }

    private void showDialogWarning(String TAG) {
        final Dialog dialog = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bluetooth_not_connected);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);


        if (TAG.equalsIgnoreCase("shutdown")) {
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


                boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                } else {
                    //export();
                    // getActivity().deleteDatabase(DbConfig.DB_NAME);
                    if (DbExportImport.exportDb()) {
                        Toast.makeText(getActivity(), "Successfully Exported", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Export Failed", Toast.LENGTH_LONG).show();
                    }
                }


                dialog.dismiss();
            }
        });

        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                    if (isKitKat) {
                        Intent intent = new Intent();
                        intent.setType("*/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PICKFILE_RESULT_CODE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, PICKFILE_RESULT_CODE);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No activity can handle picking a file. Showing alternatives.", Toast.LENGTH_LONG).show();
                }


                //importDB();

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (DbExportImport.exportDb()) {
                        Toast.makeText(getActivity(), "Successfully Exported", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Export Failed", Toast.LENGTH_LONG).show();
                    }
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == getActivity().RESULT_OK) {


                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    // Setting OK Button
                    final String finalDisplayName = displayName;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());

                    // set title
                    alertDialogBuilder.setTitle("Alert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("This database will replace all your previous data! Are you sure you want to import!")
                            .setCancelable(false)
                            .setIcon(R.drawable.fail)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    if (DbExportImport.restoreDb(finalDisplayName, getActivity())) {
                                        AlertDialogForAnything.showAlertDialog(getActivity(), "Success", "Successfully imported", true);

                                    } else {
                                        Toast.makeText(getActivity(), "Import Failed", Toast.LENGTH_LONG).show();
                                    }

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                    //FilePath is your file as a string
                }
        }
    }
}