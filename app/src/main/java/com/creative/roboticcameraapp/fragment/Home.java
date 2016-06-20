package com.creative.roboticcameraapp.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.roboticcameraapp.AdhocInitialize;
import com.creative.roboticcameraapp.MultiRowProfileList;
import com.creative.roboticcameraapp.PartialGigapixelProfileList;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.SingleRowProfileList;
import com.creative.roboticcameraapp.adapter.DevicesAdapter;
import com.creative.roboticcameraapp.appdata.AppConstant;

import org.w3c.dom.Text;

import java.util.List;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

/**
 * Created by comsol on 01-Jun-16.
 */
public class Home extends Fragment {

    public static final String KEY_IS_FROM_HOME = "is_from_home";
    private LinearLayout btn_single_row, btn_multi_row, btn_partial_row, btn_connect,btn_adhoc;
    private TextView tv_connect,tv_connected_device;


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
                if (tv_connect.getText().toString().equalsIgnoreCase("Connect")) {



                    startBlueToothConnectingDialog();

                } else {
                    tv_connect.setText("Connect");

                    tv_connected_device.setText("No device connected");

                    AppConstant.mSmoothBluetooth.disconnect();

                }
            }
        });


    }

    private void startBlueToothConnectingDialog() {

        final Dialog dialog = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bluetooth_device);

        //TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        // tv_warning.setText("Execute " + singleRow.getSingleRowName());

        final ListView listView_blueTooth_devices = (ListView) dialog.findViewById(R.id.responses);

        AppConstant.mSmoothBluetooth = new SmoothBluetooth(getActivity(), SmoothBluetooth.ConnectionTo.OTHER_DEVICE, SmoothBluetooth.Connection.INSECURE, new SmoothBluetooth.Listener() {
            @Override
            public void onBluetoothNotSupported() {

            }

            @Override
            public void onBluetoothNotEnabled() {

            }

            @Override
            public void onConnecting(Device device) {

            }

            @Override
            public void onConnected(Device device) {
                if(pDialog!=null && pDialog.isShowing()) pDialog.dismiss();

                Toast.makeText(getActivity(), "Connected successfully", Toast.LENGTH_SHORT).show();
                tv_connect.setText("Disconnect");

                tv_connected_device.setText("Connected to "+ device.getName());
                dialog.dismiss();

            }

            @Override
            public void onDisconnected() {
                if(pDialog!=null && pDialog.isShowing()) pDialog.dismiss();

            }

            @Override
            public void onConnectionFailed(Device device) {
               if(pDialog!=null && pDialog.isShowing()) pDialog.dismiss();

                Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
                // dialog.dismiss();

            }

            @Override
            public void onDiscoveryStarted() {

            }

            @Override
            public void onDiscoveryFinished() {

                if(pDialog!=null && pDialog.isShowing()) pDialog.dismiss();

            }

            @Override
            public void onNoDevicesFound() {

                if(pDialog!=null && pDialog.isShowing()) pDialog.dismiss();

            }

            @Override
            public void onDevicesFound(final List<Device> deviceList, final SmoothBluetooth.ConnectionCallback connectionCallback) {
                DevicesAdapter devicesAdapter = new DevicesAdapter(getActivity(), deviceList);
                listView_blueTooth_devices.setAdapter(devicesAdapter);

                listView_blueTooth_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Progress Dialog

                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setMessage("Connecting.... Please wait...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();


                        connectionCallback.connectTo(deviceList.get(i));
                    }
                });
            }

            @Override
            public void onDataReceived(int data) {

            }
        });

        AppConstant.mSmoothBluetooth.tryConnection();

        //AppConstant.mSmoothBluetooth.isConnected()


        dialog.show();
    }

    private void init() {

        btn_single_row = (LinearLayout) getActivity().findViewById(R.id.btn_single_row);
        btn_multi_row = (LinearLayout) getActivity().findViewById(R.id.btn_multi_row);
        btn_partial_row = (LinearLayout) getActivity().findViewById(R.id.btn_partial_row);
        btn_connect = (LinearLayout) getActivity().findViewById(R.id.btn_connect);
        btn_adhoc = (LinearLayout) getActivity().findViewById(R.id.btn_adhoc);
        tv_connect = (TextView) getActivity().findViewById(R.id.tv_connect);
        tv_connected_device = (TextView) getActivity().findViewById(R.id.tv_connected_device);

    }


}