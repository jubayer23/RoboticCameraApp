package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.creative.roboticcameraapp.adapter.SingleRowAdapter;
import com.creative.roboticcameraapp.appdata.AppController;

/**
 * Created by comsol on 02-Jun-16.
 */
public class SingleRowProfileList extends AppCompatActivity implements SingleRowAdapter.OnEditActionListener {

    private FloatingActionButton addSingleRowProfile;

    private ListView list_single_row;

    private SingleRowAdapter singleRowAdapter;

    public static final String KEY_SHOULD_UPDATE = "shouldUpdate";
    public static final String KEY_UPDATE_ID = "updateId";
    public static final String KEY_UPDATE_POSITION = "updatePosition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        init();

        singleRowAdapter = new SingleRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllSingleRow());
        singleRowAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_single_row.setAdapter(singleRowAdapter);
    }

    private void init() {

        list_single_row = (ListView) findViewById(R.id.list);


        addSingleRowProfile = (FloatingActionButton) findViewById(R.id.add_profile);
        addSingleRowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleRowProfileList.this, SingleRowAddUpdateProfile.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public void onEdit(int id, int position) {

        Intent intent = new Intent(SingleRowProfileList.this, SingleRowAddUpdateProfile.class);
        intent.putExtra(KEY_SHOULD_UPDATE, true);
        intent.putExtra(KEY_UPDATE_ID, id);
        intent.putExtra(KEY_UPDATE_POSITION, position);
        startActivityForResult(intent, 1);

    }

    private void updateData() {
        singleRowAdapter = new SingleRowAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllSingleRow());
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
}
