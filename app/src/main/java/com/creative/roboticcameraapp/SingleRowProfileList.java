package com.creative.roboticcameraapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.creative.roboticcameraapp.adapter.SingleRowAdapter;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class SingleRowProfileList extends AppCompatActivity implements SingleRowAdapter.OnEditActionListener {

    private FloatingActionButton addSingleRowProfile, sortProfile;

    private ListView list_single_row;

    private SingleRowAdapter singleRowAdapter;

    public static final String KEY_SHOULD_UPDATE = "shouldUpdate";
    public static final String KEY_UPDATE_ID = "updateId";
    public static final String KEY_UPDATE_POSITION = "updatePosition";

    private static boolean IS_FROM_HOME = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);


        IS_FROM_HOME = getIntent().getBooleanExtra(Home.KEY_IS_FROM_HOME, false);

        init();

        if (IS_FROM_HOME) {
            final List<SingleRow> singleRows = AppController.getInstance().getsqliteDbInstance().getAllSingleRow();

            singleRowAdapter = new SingleRowAdapter(this, singleRows, IS_FROM_HOME);
            //singleRowAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_single_row.setAdapter(singleRowAdapter);

            list_single_row.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    if (MainActivity.isConnected()) {
                        showSendDataDialog(singleRows.get(i));
                    } else {
                        showDialogWarning();
                    }


                }
            });

            addSingleRowProfile.setBackgroundTintList(this.getResources().getColorStateList(R.color.red));
            sortProfile.setVisibility(View.GONE);

        } else {
            singleRowAdapter = new SingleRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllSingleRow(), IS_FROM_HOME);
            singleRowAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_single_row.setAdapter(singleRowAdapter);
        }


        sortProfile.setBackgroundTintList(this.getResources().getColorStateList(R.color.blue_initializze));
        singleRowAdapter.sort(AppController.getInstance().getPrefManger().getSortOrderForSingleRow());

    }

    private void init() {

        list_single_row = (ListView) findViewById(R.id.list);


        addSingleRowProfile = (FloatingActionButton) findViewById(R.id.add_profile);


        addSingleRowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (IS_FROM_HOME) {

                    showPauseResumeDialog();

                } else {
                    Intent intent = new Intent(SingleRowProfileList.this, SingleRowAddUpdateProfile.class);
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

        Log.d("DEBUG", String.valueOf(id));
        Intent intent = new Intent(SingleRowProfileList.this, SingleRowAddUpdateProfile.class);
        intent.putExtra(KEY_SHOULD_UPDATE, true);
        intent.putExtra(KEY_UPDATE_ID, id);
        intent.putExtra(KEY_UPDATE_POSITION, position);
        startActivityForResult(intent, 1);

    }

    private void updateData() {
        singleRowAdapter = new SingleRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllSingleRow(), IS_FROM_HOME);
        singleRowAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_single_row.setAdapter(singleRowAdapter);
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


    private void showSendDataDialog(final SingleRow singleRow) {
        final Dialog dialog = new Dialog(SingleRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_send_data);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        tv_warning.setText("Execute " + singleRow.getSingleRowName());

        Button btn_start = (Button) dialog.findViewById(R.id.btn_start);

        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //singleRow.getSendString();

                MainActivity.sendCommand(singleRow.getSendString());

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
        final Dialog dialog = new Dialog(SingleRowProfileList.this,
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

    private void showDialogForSort() {
        final Dialog dialog = new Dialog(SingleRowProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_for_sort);


        Button btn_aes = (Button) dialog.findViewById(R.id.btn_aes);

        Button btn_des = (Button) dialog.findViewById(R.id.btn_disending);

        Button btn_date = (Button) dialog.findViewById(R.id.btn_date);

        btn_aes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                singleRowAdapter.sort(AppConstant.ASENDING);
                AppController.getInstance().getPrefManger().setSortOrderForSingleRow(AppConstant.ASENDING);

                dialog.dismiss();
            }
        });

        btn_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                singleRowAdapter.sort(AppConstant.DESENDING);
                AppController.getInstance().getPrefManger().setSortOrderForSingleRow(AppConstant.DESENDING);

                dialog.dismiss();
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                singleRowAdapter.sort(AppConstant.DATE);
                AppController.getInstance().getPrefManger().setSortOrderForSingleRow(AppConstant.DATE);

                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void showPauseResumeDialog() {
        final Dialog dialog = new Dialog(SingleRowProfileList.this,
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
}
