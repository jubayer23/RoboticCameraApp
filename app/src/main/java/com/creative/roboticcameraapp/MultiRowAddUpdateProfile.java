package com.creative.roboticcameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.creative.roboticcameraapp.fragment.Home;
import com.creative.roboticcameraapp.fragment.Setting;
import com.creative.roboticcameraapp.fragment.multiRowStep.MRStepOne;
import com.creative.roboticcameraapp.fragment.multiRowStep.MRStepThree;
import com.creative.roboticcameraapp.fragment.multiRowStep.MRStepTwo;

/**
 * Created by comsol on 06-Jun-16.
 */
public class MultiRowAddUpdateProfile extends AppCompatActivity implements MRStepOne.OnDataPass, MRStepTwo.OnDataPassStepTow, MRStepThree.OnDataPassStepThree {


    public static final String KEY_PROFILE_NAME = "profile_name";
    public static final String KEY_NUM_OF_ROWS = "num_of_rows";
    public static final String KEY_ELEVATION = "elevation";
    public static final String KEY_POSITION = "position";
    public static final String KEY_DIRECTION = "direction";
    public static final String KEY_PANORAMA_WIDTH = "panorama_width";


    private FragmentTransaction transaction;
    private Fragment fragment_step_one, fragment_step_two, fragment_step_three;
    private FragmentManager fragmentManager;

    private boolean shouldUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multirow_add_update);

        shouldUpdate = getIntent().getBooleanExtra(MultiRowProfileList.KEY_SHOULD_UPDATE, false);


        if (shouldUpdate) {
            getSupportActionBar().setTitle(R.string.multiRowUpdate);
        }

        init();


        fragment_step_one = new MRStepOne();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager
                .beginTransaction();
        transaction.add(R.id.container, fragment_step_one, "first");
        transaction.commit();

    }

    private void init() {
    }

    @Override
    public void onDataPass(String profileName, int numOfRows, int panoramaWidth) {


        Bundle bundle = new Bundle();
        bundle.putString(MultiRowAddUpdateProfile.KEY_PROFILE_NAME, profileName);
        bundle.putInt(MultiRowAddUpdateProfile.KEY_NUM_OF_ROWS, numOfRows);
        bundle.putInt(MultiRowAddUpdateProfile.KEY_PANORAMA_WIDTH, panoramaWidth);

        fragment_step_two = new MRStepTwo();
        fragment_step_two.setArguments(bundle);

        transaction = fragmentManager
                .beginTransaction();
        transaction.add(R.id.container, fragment_step_two, "second");


        // Hide fragment B
        if (fragment_step_one.isAdded()) {
            transaction.hide(fragment_step_one);
        }


        transaction.commit();

    }

    @Override
    public void onBackPressedAtStepOne() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onDataPassStepTow(String profileName, int numOfRows,int panoramaWidth, String elevation, String position, String direction) {
        Bundle bundle = new Bundle();
        bundle.putString(MultiRowAddUpdateProfile.KEY_PROFILE_NAME, profileName);
        bundle.putInt(MultiRowAddUpdateProfile.KEY_NUM_OF_ROWS, numOfRows);
        bundle.putInt(MultiRowAddUpdateProfile.KEY_PANORAMA_WIDTH, panoramaWidth);
        bundle.putString(MultiRowAddUpdateProfile.KEY_ELEVATION, elevation);
        bundle.putString(MultiRowAddUpdateProfile.KEY_POSITION, position);
        bundle.putString(MultiRowAddUpdateProfile.KEY_DIRECTION, direction);

        fragment_step_three = new MRStepThree();
        fragment_step_three.setArguments(bundle);

        transaction = fragmentManager
                .beginTransaction();
        transaction.add(R.id.container, fragment_step_three, "three");

        // Hide fragment B
        if (fragment_step_one.isAdded()) {
            transaction.hide(fragment_step_one);
        }
        if (fragment_step_two.isAdded()) {
            transaction.hide(fragment_step_two);
        }


        transaction.commit();
    }

    @Override
    public void onBackPressedAtStepTwo() {

        transaction = getSupportFragmentManager()
                .beginTransaction();

        if (fragment_step_one.isAdded()) { // if the fragment is already in container
            transaction.show(fragment_step_one);
        } else { // fragment needs to be added to frame container
            transaction.add(R.id.container, fragment_step_one, "first");
        }
        // Hide fragment B
        if (fragment_step_two.isAdded()) {
            transaction.remove(fragment_step_two);
        }
        if (fragment_step_three != null) {
            if (fragment_step_three.isAdded()) {
                transaction.remove(fragment_step_three);
            }
        }

        // Hide fragment C
        // Commit changes
        transaction.commit();

    }

    @Override
    public void onDataPassStepThree() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressedAtStepThree() {
        transaction = getSupportFragmentManager()
                .beginTransaction();

        if (fragment_step_two.isAdded()) { // if the fragment is already in container
            transaction.show(fragment_step_two);
        } else { // fragment needs to be added to frame container
            transaction.add(R.id.container, fragment_step_two, "second");
        }

        if (fragment_step_one.isAdded()) {
            transaction.hide(fragment_step_one);
        }
        if (fragment_step_three.isAdded()) {
            transaction.remove(fragment_step_three);
        }


        // Hide fragment C
        // Commit changes
        transaction.commit();
    }
}
