package com.creative.roboticcameraapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creative.roboticcameraapp.appdata.AppConstant;

/**
 * Created by comsol on 20-Jun-16.
 */
public class AdhocInitialize extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String ERROR_BLUETOOTH = "bluetooth_error";
    private static final String SUCCESS_INITIALIZE = "initilize_success";
    private static final String SUCCESS_SHOOT = "shoot_success";

    private LinearLayout btn_shoot, btn_top, btn_right, btn_bottom, btn_left;

    private Button btn_initialize;


    private static boolean isSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc_initialize);

        init();
    }

    private void init() {

        btn_initialize = (Button) findViewById(R.id.btn_initialize);
        btn_initialize.setOnClickListener(this);
        // btn_cancel.OnTouchListener(this);

        btn_shoot = (LinearLayout) findViewById(R.id.btn_shoot);
        btn_shoot.setOnClickListener(this);


        btn_top = (LinearLayout) findViewById(R.id.btn_top);
        btn_top.setOnTouchListener(this);
        btn_right = (LinearLayout) findViewById(R.id.btn_right);
        btn_right.setOnTouchListener(this);
        btn_bottom = (LinearLayout) findViewById(R.id.btn_bottom);
        btn_bottom.setOnTouchListener(this);
        btn_left = (LinearLayout) findViewById(R.id.btn_left);
        btn_left.setOnTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                onBackPressed();
                break;

        }

        return true;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_shoot) {
            if (MainActivity.isConnected()) {

                MainActivity.sendCommand(getShootButtonString());

                // showDialogWarning(SUCCESS_SHOOT);

            } else {
                showDialogWarning(ERROR_BLUETOOTH);
            }
        }

        if (id == R.id.btn_initialize) {
            if (MainActivity.isConnected()) {

                showDialogWarning(SUCCESS_INITIALIZE);

            } else {
                showDialogWarning(ERROR_BLUETOOTH);
            }
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (MainActivity.isConnected()) {
                    if (id == R.id.btn_top) {

                        MainActivity.sendCommand(arrowClickString(AppConstant.TOP, AppConstant.ONCLICK));
                        // Log.d("DEBUG", "press");
                    }

                    if (id == R.id.btn_bottom) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.BOTTOM, AppConstant.ONCLICK));
                    }

                    if (id == R.id.btn_right) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.RIGHT, AppConstant.ONCLICK));
                    }

                    if (id == R.id.btn_left) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.LEFT, AppConstant.ONCLICK));

                    }
                } else {
                    //showDialogWarning(ERROR_BLUETOOTH);
                }


                // PRESSED
                return false; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:

                if (MainActivity.isConnected()) {
                    if (id == R.id.btn_top) {

                        MainActivity.sendCommand(arrowClickString(AppConstant.TOP, AppConstant.ONRELEASE));
                        // Log.d("DEBUG", "release");
                    }

                    if (id == R.id.btn_bottom) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.BOTTOM, AppConstant.ONRELEASE));
                    }

                    if (id == R.id.btn_right) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.RIGHT, AppConstant.ONRELEASE));
                    }

                    if (id == R.id.btn_left) {
                        MainActivity.sendCommand(arrowClickString(AppConstant.LEFT, AppConstant.ONRELEASE));

                    }
                } else {
                    showDialogWarning(ERROR_BLUETOOTH);
                }


                // RELEASED
                return false; // if you want to handle the touch event
        }
        return false;
    }

    private void showDialogWarning(String warning_for) {
        final Dialog dialog = new Dialog(AdhocInitialize.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_bluetooth_not_connected);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);


        if (warning_for.equalsIgnoreCase(SUCCESS_INITIALIZE)) {

            btn_ok.setVisibility(View.VISIBLE);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.sendCommand(getInitializeButtonString());
                    dialog.dismiss();
                }
            });


            tv_warning.setText("Initialize tilt arm!");
           // btn_cancel.setBackgroundResource(R.drawable.btn_save_selector);

        } else if (warning_for.equalsIgnoreCase(SUCCESS_SHOOT)) {

            btn_cancel.setText("OK");
            tv_warning.setText("Shoot success!");
            btn_cancel.setBackgroundResource(R.drawable.btn_save_selector);

        } else if (warning_for.equalsIgnoreCase(ERROR_BLUETOOTH)) {

            // DONT NEED TO DO ANYTHING

        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    private String arrowClickString(int position, int clickEvent) {


        String temp = "";

        if (position == AppConstant.TOP && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|tcw|dataEnd";
        } else if (position == AppConstant.TOP && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|pcw|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|tccw|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|pccw|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|dataEnd";
        }
        return temp;
    }

    private String getShootButtonString() {
        return "dataStart|400|shoot|dataEnd";
    }

    private String getInitializeButtonString() {
        return "dataStart|500|init|dataEnd";
    }
}
