package com.creative.roboticcameraapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.roboticcameraapp.adapter.MultiRowAdapter;
import com.creative.roboticcameraapp.adapter.SingleRowAdapter;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.model.MultiRow;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class MultiRowProfileList extends AppCompatActivity implements MultiRowAdapter.OnEditActionListener {

    private FloatingActionButton addMultiRowProfile, sortProfile;

    private ListView list_multi_row;

    private MultiRowAdapter multiRowAdapter;

    public static final String KEY_SHOULD_UPDATE = "shouldUpdate";
    public static final String KEY_UPDATE_ID = "updateId";
    public static final String KEY_UPDATE_POSITION = "updatePosition";

    public static boolean IS_FROM_HOME = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        IS_FROM_HOME = getIntent().getBooleanExtra(Home.KEY_IS_FROM_HOME, false);

        init();

        if (IS_FROM_HOME) {
            final List<MultiRow> multiRows = AppController.getInstance().getsqliteDbInstance().getAllMultiRow();

            multiRowAdapter = new MultiRowAdapter(this, multiRows, IS_FROM_HOME);
            //singleRowAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_multi_row.setAdapter(multiRowAdapter);

            list_multi_row.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    if (MainActivity.isConnected()) {
                        showSendDataDialog(multiRows.get(i));
                    } else {
                        showDialogWarning();
                    }


                }
            });

            addMultiRowProfile.setBackgroundTintList(this.getResources().getColorStateList(R.color.red));

        } else {
            multiRowAdapter = new MultiRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllMultiRow(), IS_FROM_HOME);
            multiRowAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_multi_row.setAdapter(multiRowAdapter);
        }

        multiRowAdapter.sort(AppController.getInstance().getPrefManger().getSortOrderMultiRow());
    }

    private void init() {

        list_multi_row = (ListView) findViewById(R.id.list);


        addMultiRowProfile = (FloatingActionButton) findViewById(R.id.add_profile);
        addMultiRowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_FROM_HOME) {

                    showPauseResumeDialog();

                } else {
                    Intent intent = new Intent(MultiRowProfileList.this, MultiRowAddUpdateProfile.class);
                    startActivityForResult(intent, 0);
                }

            }
        });

        sortProfile = (FloatingActionButton) findViewById(R.id.sort_profile);


        sortProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogForSort();

            }
        });
    }


    @Override
    public void onEdit(int id, int position) {

        Intent intent = new Intent(MultiRowProfileList.this, MultiRowAddUpdateProfile.class);
        intent.putExtra(KEY_SHOULD_UPDATE, true);
        intent.putExtra(KEY_UPDATE_ID, id);
        intent.putExtra(KEY_UPDATE_POSITION, position);
        startActivityForResult(intent, 1);

    }

    private void updateData() {
        multiRowAdapter = new MultiRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllMultiRow(), IS_FROM_HOME);
        multiRowAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_multi_row.setAdapter(multiRowAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateData();
            }
        }
    }


    private void showSendDataDialog(final MultiRow multiRow) {
        final Dialog dialog = new Dialog(MultiRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_send_data);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        tv_warning.setText("Execute " + multiRow.getMultiRowName());

        Button btn_start = (Button) dialog.findViewById(R.id.btn_start);

        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //singleRow.getSendString();

                MainActivity.sendCommand(multiRow.getSendString());


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.sendCommand(multiRow.getSendStringRowInformation());
                    }
                }, 1000);

                //TODO
                dialog.dismiss();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showDialogWarning() {
        final Dialog dialog = new Dialog(MultiRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bluetooth_not_connected);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);


        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showPauseResumeDialog() {
        final Dialog dialog = new Dialog(MultiRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_pause_resume);

        LinearLayout btn_pause_resume = (LinearLayout) dialog.findViewById(R.id.btn_pause_resume);


        LinearLayout btn_cancel = (LinearLayout) dialog.findViewById(R.id.btn_cancel);


        btn_pause_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.isConnected()) {
                    // if (AppConstant.mSmoothBluetooth.isConnected()) {
                    MainActivity.sendCommand("dataStart|610|dataEnd");
                    // } else {
                    //     showDialogWarning();
                    // }
                } else {
                    showDialogWarning();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.isConnected()) {
                    // if (AppConstant.mSmoothBluetooth.isConnected()) {
                    MainActivity.sendCommand("dataStart|620|dataEnd");
                    // } else {
                    //     showDialogWarning();
                    // }
                } else {
                    showDialogWarning();
                }


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showDialogForSort() {
        final Dialog dialog = new Dialog(MultiRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_for_sort);


        Button btn_aes = (Button) dialog.findViewById(R.id.btn_aes);

        Button btn_des = (Button) dialog.findViewById(R.id.btn_disending);

        Button btn_date = (Button) dialog.findViewById(R.id.btn_date);

        btn_aes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                multiRowAdapter.sort(AppConstant.ASENDING);
                AppController.getInstance().getPrefManger().setSortOrderForMultiRow(AppConstant.ASENDING);

                dialog.dismiss();
            }
        });

        btn_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                multiRowAdapter.sort(AppConstant.DESENDING);
                AppController.getInstance().getPrefManger().setSortOrderForMultiRow(AppConstant.DESENDING);

                dialog.dismiss();
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                multiRowAdapter.sort(AppConstant.DATE);
                AppController.getInstance().getPrefManger().setSortOrderForMultiRow(AppConstant.DATE);

                dialog.dismiss();
            }
        });



        dialog.show();
    }
}
