package com.creative.roboticcameraapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creative.roboticcameraapp.appdata.AppConstant;

/**
 * Created by comsol on 20-Jun-16.
 */
public class AdhocInitialize extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    private static final String ERROR_BLUETOOTH = "bluetooth_error";
    private static final String SUCCESS_INITIALIZE = "initilize_success";
    private static final String SUCCESS_SHOOT = "shoot_success";

    private RelativeLayout btn_shoot, btn_top, btn_right, btn_bottom, btn_left;

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

        btn_shoot = (RelativeLayout) findViewById(R.id.btn_shoot);
        btn_shoot.setOnClickListener(this);


        btn_top = (RelativeLayout) findViewById(R.id.btn_top);
        btn_top.setOnTouchListener(this);
        btn_right = (RelativeLayout) findViewById(R.id.btn_right);
        btn_right.setOnTouchListener(this);
        btn_bottom = (RelativeLayout) findViewById(R.id.btn_bottom);
        btn_bottom.setOnTouchListener(this);
        btn_left = (RelativeLayout) findViewById(R.id.btn_left);
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

        if(id == R.id.btn_shoot){
            if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                AppConstant.mSmoothBluetooth.send(getShootButtonString());

               // showDialogWarning(SUCCESS_SHOOT);

            } else {
                showDialogWarning(ERROR_BLUETOOTH);
            }
        }

        if(id == R.id.btn_initialize){
            if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                AppConstant.mSmoothBluetooth.send(getInitializeButtonString());

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

                if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {
                    if (id == R.id.btn_top) {

                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.TOP, AppConstant.ONCLICK));
                        // Log.d("DEBUG", "press");
                    }

                    if (id == R.id.btn_bottom) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.BOTTOM, AppConstant.ONCLICK));
                    }

                    if (id == R.id.btn_right) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.RIGHT, AppConstant.ONCLICK));
                    }

                    if (id == R.id.btn_left) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.LEFT, AppConstant.ONCLICK));

                    }
                } else {
                    //showDialogWarning(ERROR_BLUETOOTH);
                }


                // PRESSED
                return false; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:

                if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {
                    if (id == R.id.btn_top) {

                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.TOP, AppConstant.ONRELEASE));
                        // Log.d("DEBUG", "release");
                    }

                    if (id == R.id.btn_bottom) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.BOTTOM, AppConstant.ONRELEASE));
                    }

                    if (id == R.id.btn_right) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.RIGHT, AppConstant.ONRELEASE));
                    }

                    if (id == R.id.btn_left) {
                        AppConstant.mSmoothBluetooth.send(arrowClickString(AppConstant.LEFT, AppConstant.ONRELEASE));

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


        if (warning_for.equalsIgnoreCase(SUCCESS_INITIALIZE)) {


            btn_cancel.setText("OK");
            tv_warning.setText("Initialized!");
            btn_cancel.setBackgroundResource(R.drawable.btn_save_selector);

        } else if (warning_for.equalsIgnoreCase(SUCCESS_SHOOT)) {

            btn_cancel.setText("OK");
            tv_warning.setText("Shoot success!");
            btn_cancel.setBackgroundResource(R.drawable.btn_save_selector);

        }else if (warning_for.equalsIgnoreCase(ERROR_BLUETOOTH)) {

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
            temp = "dataStart|410|up|dataEnd";
        } else if (position == AppConstant.TOP && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|up|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|right|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|right|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|down|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|420|down|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|410|left|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|410|left|dataEnd";
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
