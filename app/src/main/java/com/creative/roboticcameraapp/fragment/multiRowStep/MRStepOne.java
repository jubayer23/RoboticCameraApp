package com.creative.roboticcameraapp.fragment.multiRowStep;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.creative.roboticcameraapp.MultiRowProfileList;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.SingleRowProfileList;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.MultiRow;

/**
 * Created by comsol on 06-Jun-16.
 */
public class MRStepOne extends Fragment implements View.OnClickListener {

    private EditText ed_profile_name, ed_num_of_rows;

    private Button btn_next, btn_cancel;

    private OnDataPass dataPasser;

    private boolean shouldUpdate;

    private MultiRow multiRow;

    //lifeCycle->
    //onCreate->onCreateView->onActivityCreated


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shouldUpdate = getActivity().getIntent().getBooleanExtra(MultiRowProfileList.KEY_SHOULD_UPDATE, false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mr_stepone, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        if (SavedInstanceState == null) {
            init();

            if(shouldUpdate){

                int id = getActivity().getIntent().getIntExtra(MultiRowProfileList.KEY_UPDATE_ID, 0);
                multiRow = AppController.getInstance().getsqliteDbInstance().getMultiRow(id);

                ed_profile_name.setText(multiRow.getMultiRowName());
                ed_num_of_rows.setText(String.valueOf(multiRow.getNum_of_rows()));

            }else{

            }

        } else {

        }


    }

    private void init() {

        ed_profile_name = (EditText) getActivity().findViewById(R.id.ed_multirow_name);
        ed_num_of_rows = (EditText) getActivity().findViewById(R.id.ed_num_of_rows);


        btn_next = (Button) getActivity().findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_cancel = (Button) getActivity().findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
            dataPasser = (OnDataPass) a;
        }

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_next) {

            if (showWarningDialog()) {
                dataPasser.onDataPass(ed_profile_name.getText().toString(), Integer.parseInt(ed_num_of_rows.getText().toString()));
            }

        }

        if (id == R.id.btn_cancel) {

            dataPasser.onBackPressedAtStepOne();

        }

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (ed_num_of_rows.getText().toString().isEmpty()) {
            ed_num_of_rows.setError("Required");
            ed_num_of_rows.requestFocus();
            valid = false;
        } else {
            ed_num_of_rows.setError(null);
        }

        if (ed_profile_name.getText().toString().isEmpty()) {
            ed_profile_name.setError("Required");
            ed_profile_name.requestFocus();
            valid = false;
        } else {
            ed_profile_name.setError(null);
        }

        return valid;
    }

    public interface OnDataPass {
        public void onDataPass(String profileName, int numOfRows);
        public void onBackPressedAtStepOne();
    }
}
