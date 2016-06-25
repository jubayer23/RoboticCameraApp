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
public class PartialGigapixelSteps extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String ERROR_BLUETOOTH = "bluetooth_error";
    private static final String ERROR_SET = "set_error";
    private static final String SUCCESSFULLY_SET = "set_successfully";
    private TextView tv_title;

    private RelativeLayout btn_set, btn_top, btn_right, btn_bottom, btn_left;

    private Button btn_submit, btn_cancel;


    private static boolean isSet = false;

    private static boolean isStepOne = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partial_steps);

        init();
    }


    private void init() {

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        // btn_cancel.OnTouchListener(this);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);

        btn_set = (RelativeLayout) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(this);


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
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_set) {

            if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                if (!isSet) {
                    isSet = true;
                    AppConstant.mSmoothBluetooth.send(getSetButtonString(isStepOne));
                    showDialogWarning(SUCCESSFULLY_SET);
                }


            } else {
                showDialogWarning(ERROR_BLUETOOTH);
            }

        }

        if (id == R.id.btn_submit) {

            if (isSet) {

                if (isStepOne) {
                    isStepOne = false;
                    isSet = false;
                    tv_title.setText("Step 2 of 2 : set lower right corner of frame.");
                    btn_submit.setText("Start");
                } else {

                    if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                        isSet = true;
                        AppConstant.mSmoothBluetooth.send(getStartButtonString());

                    } else {
                        showDialogWarning(ERROR_BLUETOOTH);
                    }
                }


            } else {
                showDialogWarning(ERROR_SET);
            }

        }
        if (id == R.id.btn_cancel) {
            if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                AppConstant.mSmoothBluetooth.send(getCancelButtonString());

                finish();

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
        final Dialog dialog = new Dialog(PartialGigapixelSteps.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_bluetooth_not_connected);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        if (warning_for.equalsIgnoreCase(SUCCESSFULLY_SET)) {
            if (isStepOne) {
                tv_warning.setText("Upper left corner has been set");
            } else {
                tv_warning.setText("Lower right corner has been set");
            }
            btn_cancel.setText("OK");
            btn_cancel.setBackgroundResource(R.drawable.btn_save_selector);

        } else if (warning_for.equalsIgnoreCase(ERROR_SET)) {

            tv_warning.setText("You must first set the upper left corner of the frame");
            btn_cancel.setText("OK");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private String arrowClickString(int position, int clickEvent) {

        String temp = "";

        if (position == AppConstant.TOP && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|310|up|dataEnd";
        } else if (position == AppConstant.TOP && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|320|up|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|310|right|dataEnd";
        } else if (position == AppConstant.RIGHT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|320|right|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|310|down|dataEnd";
        } else if (position == AppConstant.BOTTOM && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|320|down|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONCLICK) {
            temp = "dataStart|310|left|dataEnd";
        } else if (position == AppConstant.LEFT && clickEvent == AppConstant.ONRELEASE) {
            temp = "dataStart|310|left|dataEnd";
        }
        return temp;
    }

    private String getSetButtonString(boolean isStepOne) {
        String temp = "";
        if (isStepOne) {
            temp = "dataStart|330|setUL|dataEnd";
        } else {
            temp = "dataStart|340|setUL|dataEnd";
        }
        return temp;
    }

    private String getCancelButtonString() {

        return "dataStart|999|cancel|dataEnd";
    }

    private String getStartButtonString() {

        return "dataStart|350|panoStart|dataEnd";
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
}
