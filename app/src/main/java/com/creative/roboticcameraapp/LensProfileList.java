package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.creative.roboticcameraapp.adapter.CameraListAdapter;
import com.creative.roboticcameraapp.adapter.LensListAdapter;
import com.creative.roboticcameraapp.appdata.AppController;

/**
 * Created by comsol on 02-Jun-16.
 */
public class LensProfileList extends AppCompatActivity implements LensListAdapter.OnEditActionListener {

    private FloatingActionButton addLensProfile;

    private ListView list_lens;

    private LensListAdapter lensListAdapter;

    public static final String KEY_SHOULD_UPDATE = "shouldUpdate";
    public static final String KEY_UPDATE_ID = "updateId";
    public static final String KEY_UPDATE_POSITION = "updatePosition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        init();

        lensListAdapter = new LensListAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllLenses());
        lensListAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_lens.setAdapter(lensListAdapter);
    }

    private void init() {

        list_lens = (ListView) findViewById(R.id.list);


        addLensProfile = (FloatingActionButton) findViewById(R.id.add_profile);
        addLensProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LensProfileList.this, LensAddUpdateProfile.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public void onEdit(int id, int position) {

        Intent intent = new Intent(LensProfileList.this, LensAddUpdateProfile.class);
        intent.putExtra(KEY_SHOULD_UPDATE, true);
        intent.putExtra(KEY_UPDATE_ID, id);
        intent.putExtra(KEY_UPDATE_POSITION, position);
        startActivityForResult(intent, 1);

    }

    private void updateData() {
        lensListAdapter = new LensListAdapter(this, AppController.getInstance().getsqliteDbInstance().getAllLenses());
        lensListAdapter.setListener(this);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_lens.setAdapter(lensListAdapter);
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
