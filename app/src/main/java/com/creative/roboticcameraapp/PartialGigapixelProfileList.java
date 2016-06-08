package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.creative.roboticcameraapp.adapter.PartialGigapixelAdapter;
import com.creative.roboticcameraapp.adapter.SingleRowAdapter;
import com.creative.roboticcameraapp.appdata.AppController;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        init();

        partialGigapixelAdapter = new PartialGigapixelAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllPartialGigapixel());
        partialGigapixelAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_partial_gigapixel.setAdapter(partialGigapixelAdapter);
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
        partialGigapixelAdapter = new PartialGigapixelAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllPartialGigapixel());
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
}
