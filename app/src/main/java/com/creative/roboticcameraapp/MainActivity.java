package com.creative.roboticcameraapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.bluetooth.BluetoothDeviceConnector;
import com.creative.roboticcameraapp.bluetooth.BluetoothDeviceListActivity;
import com.creative.roboticcameraapp.database.DbConfig;
import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.fragment.Setting;
import com.creative.roboticcameraapp.utils.BluetoothDeviceData;
import com.creative.roboticcameraapp.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends BluetoothBaseActivity implements View.OnClickListener {

    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";

    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");

    public static BluetoothDeviceConnector connector;
    private static BluetoothResponseHandler mHandler;


    // Настройки приложения
    private static boolean hexMode;
    private boolean needClean;
    private boolean show_timings, show_direction;
    private static String command_ending;
    private String deviceName;

    private static final String HOME_TAG = "home", SETTING_TAG = "setting";
    private LinearLayout btn_home, btn_setting;

    private FragmentTransaction transaction;
    private Fragment fragment_1, fragment_2;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mHandler == null) mHandler = new BluetoothResponseHandler(this);
        else mHandler.setTarget(this);


        setContentView(R.layout.activity_main);


        if (isConnected() && (savedInstanceState != null)) {
            setDeviceName(savedInstanceState.getString(DEVICE_NAME));
        } else getSupportActionBar().setSubtitle("Not Connected");

        // Calendar c = Calendar.getInstance();
        // int date = c.get(Calendar.DATE);
        // if(date >= 30){
        //    finish();
        //}

        init();

        fragment_1 = new Home();
        fragment_2 = new Setting();

        btnToggleColor(HOME_TAG);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager
                .beginTransaction();
        transaction.add(R.id.container, fragment_1, "first");
        transaction.commit();


        //export();
    }

    private void export() {

        try {
            Log.d("DEBUG", "yes1");

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d("DEBUG",String.valueOf(sd));
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + this.getPackageName() + "//databases//" + DbConfig.DB_NAME;
                String backupDBPath = DbConfig.DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                Log.d("DEBUG", "yes2");
                Toast.makeText(MainActivity.this,"yes1",Toast.LENGTH_SHORT).show();
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Log.d("DEBUG", backupDB.getAbsolutePath());
                }
            }
        } catch (Exception e) {
        }


    }

    private void init() {

        btn_home = (LinearLayout) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(this);
        btn_setting = (LinearLayout) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_home) {


            btnToggleColor(HOME_TAG);


            transaction = getSupportFragmentManager()
                    .beginTransaction();

            if (fragment_1.isAdded()) { // if the fragment is already in container
                transaction.show(fragment_1);
            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, fragment_1, "first");
            }
            // Hide fragment B
            if (fragment_2.isAdded()) {
                transaction.hide(fragment_2);
            }
            // Hide fragment C
            // Commit changes
            transaction.commit();

        }
        if (id == R.id.btn_setting) {

            btnToggleColor(SETTING_TAG);


            transaction = getSupportFragmentManager()
                    .beginTransaction();

            if (fragment_2.isAdded()) { // if the fragment is already in container
                transaction.show(fragment_2);
            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, fragment_2, "second");
            }
            // Hide fragment B
            if (fragment_1.isAdded()) {
                transaction.hide(fragment_1);
            }
            // Hide fragment C
            // Commit changes
            transaction.commit();


            // this.deleteDatabase(DbConfig.DB_NAME);
            //   try {
            //    AppController.getInstance().getsqliteDbInstance().createDataBase();
            // AppController.getInstance().getsqliteDbInstance().openDataBaseAfterCopy();
            ///      AppController.getInstance().getsqliteDbInstance().open();
            // } catch (IOException e) {
            //     e.printStackTrace();
            //  }

           // export();

        }

    }

    public void btnToggleColor(String btn_name) {
        if (btn_name.equalsIgnoreCase(HOME_TAG)) {
            btn_home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            btn_setting.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (btn_name.equalsIgnoreCase(SETTING_TAG)) {
            btn_home.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn_setting.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if(AppConstant.mSmoothBluetooth != null){
        //     AppConstant.mSmoothBluetooth.stop();
        // }
    }


    ///BLUETOOTH


    public void MakeConnection() {
        if (super.isAdapterReady()) {
            if (isConnected()) stopConnection();
            else startDeviceListActivity();
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DEVICE_NAME, deviceName);
        //if (logTextView != null) {
        //   final String log = logTextView.getText().toString();
        //    outState.putString(LOG, log);
        //}
    }
    // ============================================================================


    /**
     * Проверка готовности соединения
     */
    public static boolean isConnected() {
        return (connector != null) && (connector.getState() == BluetoothDeviceConnector.STATE_CONNECTED);
    }
    // ==========================================================================


    /**
     * Разорвать соединение
     */
    private void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
            deviceName = null;
            Home.changeStatus("Connect");
            //tv_connect.setText("Connect");
        }
    }
    // ==========================================================================


    /**
     * Список устройств для подключения
     */
    private void startDeviceListActivity() {
        stopConnection();
        Intent serverIntent = new Intent(this, BluetoothDeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }
    // ============================================================================


    /**
     * Обработка аппаратной кнопки "Поиск"
     *
     * @return
     */
    @Override
    public boolean onSearchRequested() {
        if (super.isAdapterReady()) startDeviceListActivity();
        return false;
    }
    // ==========================================================================
    // ============================================================================


    // ============================================================================


    // ============================================================================


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When BluetoothDeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getStringExtra(BluetoothDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = btAdapter.getRemoteDevice(address);
                    if (super.isAdapterReady() && (connector == null)) setupConnector(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                super.pendingRequestEnableBt = false;
                if (resultCode != Activity.RESULT_OK) {
                    Utils.log("BT not enabled");
                }
                break;
        }
    }
    // ==========================================================================


    /**
     * Установка соединения с устройством
     */
    private void setupConnector(BluetoothDevice connectedDevice) {
        stopConnection();
        try {
            String emptyName = getString(R.string.empty_device_name);
            BluetoothDeviceData data = new BluetoothDeviceData(connectedDevice, emptyName);
            connector = new BluetoothDeviceConnector(data, mHandler);
            connector.connect();
            // tv_connect.setText("Disconnect");
        } catch (IllegalArgumentException e) {
            Utils.log("setupConnector failed: " + e.getMessage());
        }
    }
    // ==========================================================================


    /**
     * Отправка команды устройству
     */
    public static void sendCommand(String data) {
        //  if (commandEditText != null) {
        String commandString = data;
        if (commandString.isEmpty()) return;


        byte[] command = (hexMode ? Utils.toHex(commandString) : commandString.getBytes());
        if (command_ending != null) command = Utils.concat(command, command_ending.getBytes());
        if (isConnected()) {
            connector.write(command);
            // appendLog(commandString, hexMode, true, needClean);
        }
        // }
    }
    // ==========================================================================


    void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        getSupportActionBar().setSubtitle(deviceName);
    }
    // ==========================================================================

    /**
     * Обработчик приёма данных от bluetooth-потока
     */
    private static class BluetoothResponseHandler extends Handler {
        private WeakReference<MainActivity> mActivity;

        public BluetoothResponseHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        public void setTarget(MainActivity target) {
            mActivity.clear();
            mActivity = new WeakReference<MainActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:

                        Utils.log("MESSAGE_STATE_CHANGE: " + msg.arg1);
                        final ActionBar bar = activity.getSupportActionBar();
                        switch (msg.arg1) {
                            case BluetoothDeviceConnector.STATE_CONNECTED:
                                bar.setSubtitle("Connected");
                                // Toast.makeText(MainActivity.this, "Connected successfully", Toast.LENGTH_SHORT).show();
                                //tv_connect.setText("Disconnect");
                                Home.changeStatus("Disconnect");

                                // tv_connected_device.setText("Connected to " + device.getName());

                                break;
                            case BluetoothDeviceConnector.STATE_CONNECTING:
                                bar.setSubtitle("Connecting");
                                //tv_connect.setText("Connecting");
                                Home.changeStatus("Connecting");
                                break;
                            case BluetoothDeviceConnector.STATE_NONE:
                                bar.setSubtitle("Not Connected");
                                //tv_connect.setText("Connect");
                                Home.changeStatus("Connect");
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        //final String readMessage = (String) msg.obj;
                        //if (readMessage != null) {
                        //    activity.appendLog(readMessage, false, false, activity.needClean);
                        //}
                        break;

                    case MESSAGE_DEVICE_NAME:
                        activity.setDeviceName((String) msg.obj);
                        break;

                    case MESSAGE_WRITE:
                        // stub
                        break;

                    case MESSAGE_TOAST:
                        // stub
                        break;
                }
            }
        }
    }
    // ==========================================================================
}
