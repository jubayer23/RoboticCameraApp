package com.creative.roboticcameraapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.creative.roboticcameraapp.R;
import com.creative.roboticcameraapp.appdata.AppController;
import com.creative.roboticcameraapp.model.Camera;

import java.util.List;


@SuppressLint("DefaultLocale")
public class CameraListAdapter extends BaseAdapter {

    private List<Camera> Displayedplaces;
    private List<Camera> Originalplaces;
    private LayoutInflater inflater;
    @SuppressWarnings("unused")
    private Activity activity;

    private OnEditActionListener listener;


    public CameraListAdapter(Activity activity, List<Camera> histories) {
        this.activity = activity;
        this.Displayedplaces = histories;
        this.Originalplaces = histories;


        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Displayedplaces.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_profile, parent, false);

            viewHolder = new ViewHolder();


            viewHolder.camera_name = (TextView) convertView
                    .findViewById(R.id.tv_profile_name);

            viewHolder.btn_edit = (ImageView) convertView
                    .findViewById(R.id.btn_edit);


            viewHolder.btn_delete = (ImageView) convertView
                    .findViewById(R.id.btn_delete);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Camera camera = Displayedplaces.get(position);


        viewHolder.camera_name.setText(camera.getCameraName());

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogShowWarning(camera.getId(),position);

            }
        });

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onEdit(camera.getId(),position);
                }
            }
        });


        return convertView;
    }

    public void addMore() {
        //this.Displayedplaces.addAll(places);
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        private TextView camera_name;
        private ImageView btn_edit;
        private ImageView btn_delete;

    }

    public OnEditActionListener getListener() {
        return listener;
    }

    public void setListener(OnEditActionListener listener) {
        this.listener = listener;
    }

    public interface OnEditActionListener{
        void onEdit(int id,int position);
    }
    private void dialogShowWarning(final int id, final int position) {

        final Dialog dialog = new Dialog(activity,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_setting);

        Button btn_delete = (Button)dialog.findViewById(R.id.btn_delete);

        Button btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppController.getInstance().getsqliteDbInstance().deleteCamera(id);
                Displayedplaces.remove(position);
                notifyDataSetChanged();
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


}