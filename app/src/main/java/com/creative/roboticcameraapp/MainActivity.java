package com.creative.roboticcameraapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.fragment.Setting;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String HOME_TAG = "home",SETTING_TAG = "setting";
    private LinearLayout btn_home,btn_setting;

    private FragmentTransaction transaction;
    private Fragment fragment_1, fragment_2;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        fragment_1 = new Home();
        fragment_2 = new Setting();

        btnToggleColor(HOME_TAG);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager
                .beginTransaction();
        transaction.add(R.id.container, fragment_1, "first");
        transaction.commit();
    }

    private void init() {

        btn_home = (LinearLayout)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(this);
        btn_setting = (LinearLayout)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn_home){


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
        if(id == R.id.btn_setting){

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
}
