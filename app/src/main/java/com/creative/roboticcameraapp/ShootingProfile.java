package com.creative.roboticcameraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ShootingProfile extends AppCompatActivity {

    private LinearLayout btn_single_row, btn_multi_row, btn_partial_row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting_profile);


        init();


        btn_single_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ShootingProfile.this, SingleRowProfileList.class);
                startActivity(intent);

            }
        });
        btn_multi_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShootingProfile.this, MultiRowProfileList.class);
                startActivity(intent);

            }
        });
        btn_partial_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShootingProfile.this, PartialGigapixelProfileList.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        btn_single_row = (LinearLayout) findViewById(R.id.btn_single_row);
        btn_multi_row = (LinearLayout) findViewById(R.id.btn_multi_row);
        btn_partial_row = (LinearLayout) findViewById(R.id.btn_partial_row);
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
