package com.creative.roboticcameraapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.roboticcameraapp.adapter.PartialGigapixelAdapter;
import com.creative.roboticcameraapp.adapter.SingleRowAdapter;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.model.Partial;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.List;

/**
 * Created by comsol on 02-Jun-16.
 */
public class PartialGigapixelProfileList extends AppCompatActivity implements PartialGigapixelAdapter.OnEditActionListener {

    private FloatingActionButton addPartialGigapixel;

    private ListView list_partial_gigapixel;

    private PartialGigapixelAdapter partialGigapixelAdapter;

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
            final List<Partial> partials = AppController.getInstance().getsqliteDbInstance().getAllPartialGigapixel();

            partialGigapixelAdapter = new PartialGigapixelAdapter(this, partials, IS_FROM_HOME);
            //singleRowAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_partial_gigapixel.setAdapter(partialGigapixelAdapter);

            list_partial_gigapixel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (AppConstant.mSmoothBluetooth != null && AppConstant.mSmoothBluetooth.isConnected()) {

                        showSendDataDialog(partials.get(i));

                    } else {
                        showDialogWarning();
                    }


                }
            });

            addPartialGigapixel.setVisibility(View.GONE);

        } else {
            partialGigapixelAdapter = new PartialGigapixelAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllPartialGigapixel(), IS_FROM_HOME);
            partialGigapixelAdapter.setListener(this);
            // recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list_partial_gigapixel.setAdapter(partialGigapixelAdapter);
        }


    }

    private void init() {

        list_partial_gigapixel = (ListView) findViewById(R.id.list);
        addPartialGigapixel = (FloatingActionButton) findViewById(R.id.add_profile);
        addPartialGigapixel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartialGigapixelProfileList.this, PartialGigapixelAddUpdateProfile.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public void onEdit(int id, int position) {

        Intent intent = new Intent(PartialGigapixelProfileList.this, PartialGigapixelAddUpdateProfile.class);
        intent.putExtra(KEY_SHOULD_UPDATE, true);
        intent.putExtra(KEY_UPDATE_ID, id);
        intent.putExtra(KEY_UPDATE_POSITION, position);
        startActivityForResult(intent, 1);

    }

    private void updateData() {
        partialGigapixelAdapter = new PartialGigapixelAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllPartialGigapixel(), IS_FROM_HOME);
        partialGigapixelAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_partial_gigapixel.setAdapter(partialGigapixelAdapter);
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

    private void showSendDataDialog(final Partial partial) {
        final Dialog dialog = new Dialog(PartialGigapixelProfileList.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_send_data);

        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        tv_warning.setText("Execute " + partial.getPartialName());

        Button btn_start = (Button) dialog.findViewById(R.id.btn_start);

        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //singleRow.getSendString();

                AppConstant.mSmoothBluetooth.send(partial.getSendString());

                //TODO
                dialog.dismiss();

                Intent intent = new Intent(PartialGigapixelProfileList.this, PartialGigapixelSteps.class);
                startActivity(intent);

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
        final Dialog dialog = new Dialog(PartialGigapixelProfileList.this,
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
}
