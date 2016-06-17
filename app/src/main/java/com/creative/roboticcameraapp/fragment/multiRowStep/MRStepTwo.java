package com.creative.roboticcameraapp.fragment.multiRowStep;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.creative.roboticcameraapp.MultiRowAddUpdateProfile;
import com.creative.roboticcameraapp.MultiRowProfileList;
import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.appdata.AppConstant;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.MultiRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comsol on 06-Jun-16.
 */
public class MRStepTwo extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {


    private Button btn_next, btn_cancel;

    private int num_of_rows;

    private String profile_name;

    private boolean shouldUpdate;

    private LinearLayout ll_container;

    private List<EditText> ed_list_elevation, ed_list_position;

    private List<Spinner> sp_list_direction;

    private ArrayAdapter<String> dataAdapter;
    private List<String> list_direction;

    private OnDataPassStepTow dataPasser;

    private MultiRow multiRow;

    //lifeCycle->
    //onCreate->onCreateView->onActivityCreated
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profile_name = getArguments().getString(MultiRowAddUpdateProfile.KEY_PROFILE_NAME);
        num_of_rows = getArguments().getInt(MultiRowAddUpdateProfile.KEY_NUM_OF_ROWS);
        shouldUpdate = getActivity().getIntent().getBooleanExtra(MultiRowProfileList.KEY_SHOULD_UPDATE, false);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mr_steptwo, container,
                false);

    }

    public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);

        if (SavedInstanceState == null) {
            init();


            makeRow();

            for (int i = 0; i < AppConstant.direction.length; i++) {

                list_direction.add(AppConstant.direction[i]);

            }
            dataAdapter.notifyDataSetChanged();


            if (shouldUpdate) {

                int id = getActivity().getIntent().getIntExtra(MultiRowProfileList.KEY_UPDATE_ID, 0);
                multiRow = AppController.getInstance().getsqliteDbInstance().getMultiRow(id);

                String elevationArray[] = multiRow.getElevation().split(" ");
                String positionArray[] = multiRow.getPosition().split(" ");
                String directionArray[] = multiRow.getDirection().split(" ");


                for (int i = 0; ((i < elevationArray.length) && (i < ed_list_elevation.size())); i++) {
                    ed_list_elevation.get(i).setText(elevationArray[i]);
                    ed_list_position.get(i).setText(positionArray[i]);


                    List<String> list = new ArrayList<>();
                    list.add(directionArray[i]);
                    for (int j = 0; j < AppConstant.direction.length; j++) {

                        if (list.contains(AppConstant.direction[j])) continue;

                        list.add(AppConstant.direction[j]);

                    }
                    ArrayAdapter dataAdapter_2 = new ArrayAdapter<String>
                            (getActivity(), R.layout.spinner_item, list);
                    sp_list_direction.get(i).setAdapter(dataAdapter_2);
                }

            }

        } else {

        }


    }


    private void init() {


        ed_list_elevation = new ArrayList<>();
        ed_list_position = new ArrayList<>();
        sp_list_direction = new ArrayList<>();

        list_direction = new ArrayList<>();

        dataAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, list_direction);


        ll_container = (LinearLayout) getActivity().findViewById(R.id.ll_container);


        btn_next = (Button) getActivity().findViewById(R.id.btn_next_2);
        btn_next.setOnClickListener(this);
        btn_cancel = (Button) getActivity().findViewById(R.id.btn_cancel_2);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
            dataPasser = (OnDataPassStepTow) a;
        }

    }

    private void makeRow() {

        int height = AppController.getInstance().getPixelValue(50);
        int margin = AppController.getInstance().getPixelValue(5);


        if (ll_container.getChildCount() > 0)
            ll_container.removeAllViews();
        // removing child blank diamond

        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams EDParams = new LinearLayout.LayoutParams(0, height, 1f);
        EDParams.setMargins(margin, margin, margin, margin);

        LinearLayout.LayoutParams RLParams = new LinearLayout.LayoutParams(0, height, 1f);
        RLParams.setMargins(margin, margin, margin, margin);
        RLParams.gravity = Gravity.CENTER;

        RelativeLayout.LayoutParams SPParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        SPParams.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout.LayoutParams IMGParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        IMGParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        IMGParams.addRule(RelativeLayout.CENTER_VERTICAL);


        for (int i = 0; i < num_of_rows; i++) {

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(LLParams);


            EditText ed = new EditText(getActivity());
            ed.setLayoutParams(EDParams);
            ed.setPadding(margin, 0, 0, 0);
            ed.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            ed.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            ed.setBackgroundResource(R.drawable.rounded_edittext);
            ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        if (!((EditText) view).getText().toString().isEmpty()) {
                            long value = Long.parseLong(((EditText) view).getText().toString());
                            if (value < -90 || value > 90) {
                                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });

            ed_list_elevation.add(ed);

            EditText ed_2 = new EditText(getActivity());
            ed_2.setPadding(margin, 0, 0, 0);
            ed_2.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            ed_2.setLayoutParams(EDParams);
            ed_2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            ed_2.setBackgroundResource(R.drawable.rounded_edittext);
            ed_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {


                        if (!((EditText) view).getText().toString().isEmpty()) {
                            long value = Long.parseLong(((EditText) view).getText().toString());
                            if (value < 1 || value > 4000) {
                                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_LONG).show();
                            }
                        }


                    }
                }
            });

            ed_list_position.add(ed_2);


            RelativeLayout rl_layout = new RelativeLayout(getActivity());
            rl_layout.setLayoutParams(RLParams);
            rl_layout.setBackgroundResource(R.drawable.rounded_edittext);

            Spinner sp = new Spinner(getActivity());
            sp.setLayoutParams(SPParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sp.setBackground(null);
                sp.setPopupBackgroundResource(R.color.colorPrimary);
            }

            sp.setAdapter(dataAdapter);

            sp_list_direction.add(sp);

            ImageView img = new ImageView(getActivity());
            img.setLayoutParams(IMGParams);
            img.setPadding(margin, margin, margin, margin);
            img.setImageResource(R.drawable.spinner_arrow);

            rl_layout.addView(sp);
            rl_layout.addView(img);


            layout.addView(ed);
            layout.addView(ed_2);
            layout.addView(rl_layout);

            ll_container.addView(layout);
        }


    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_next_2) {
            // Log.d("DEBUG", "YES");

            if (showWarningDialog()) {

                //Log.d("DEBUG", "YES");

                String elevation = ed_list_elevation.get(0).getText().toString();

                String position = ed_list_position.get(0).getText().toString();

                String direction = sp_list_direction.get(0).getSelectedItem().toString();

                for (int i = 1; i < ed_list_elevation.size(); i++) {
                    elevation = elevation + " " + ed_list_elevation.get(i).getText().toString();
                }
                for (int i = 1; i < ed_list_position.size(); i++) {
                    position = position + " " + ed_list_position.get(i).getText().toString();
                }
                for (int i = 1; i < sp_list_direction.size(); i++) {
                    direction = direction + " " + sp_list_direction.get(i).getSelectedItem().toString();
                }

                dataPasser.onDataPassStepTow(profile_name, num_of_rows, elevation, position, direction);


            } else {
                Toast.makeText(getActivity(), "Please fill all field OR give valid input", Toast.LENGTH_LONG).show();
            }

        }

        if (id == R.id.btn_cancel_2) {
            dataPasser.onBackPressedAtStepTwo();
        }

    }

    private boolean showWarningDialog() {

        for (int i = 0; i < ed_list_elevation.size(); i++) {
            if (ed_list_elevation.get(i).getText().toString().isEmpty()) return false;
            else {
                long value = Long.parseLong(ed_list_elevation.get(i).getText().toString());
                if (value < -90 || value > 90) return false;
            }
        }
        for (int i = 0; i < ed_list_position.size(); i++) {
            if (ed_list_position.get(i).getText().toString().isEmpty()) return false;
            else {
                long value = Long.parseLong(ed_list_position.get(i).getText().toString());
                if (value < 1 || value > 4000) return false;
            }
        }
        return true;

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    public interface OnDataPassStepTow {
        public void onDataPassStepTow(String profileName, int numOfRows, String elevation, String position, String direction);

        public void onBackPressedAtStepTwo();

    }

}
