package com.creative.roboticcameraapp.model;

import com.creative.roboticcameraapp.appdata.AppConstant;

/**
 * Created by comsol on 06-Jun-16.
 */
public class MultiRow {

    int id;
    String multiRowName;
    int num_of_rows;
    String elevation;
    String position;
    String direction;
    int num_of_bracketed_shot;
    String bracketed_style;
    int after_shot_delay;
    int startup_delay;



    int continuous_rotation;
   // int num_of_bracketed_shot;
   // String bracketed_style;
    //int after_shot_delay;
    //int startup_delay;
    int focus_delay;
    int before_shot_delay;
    //String direction;
    int speed;
    int acceleration;
    int max_frame_rate;
    int num_of_panoramas;
    int delay_between_panoramas;
    int shutter_signal_length;
    int focus_signal_length;
    int camera_wakeup;
    int camera_wakeup_signal_length;
    int camera_wakeup_delay;
    int speed_divider;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMultiRowName() {
        return multiRowName;
    }

    public void setMultiRowName(String multiRowName) {
        this.multiRowName = multiRowName;
    }

    public int getNum_of_rows() {
        return num_of_rows;
    }

    public void setNum_of_rows(int num_of_rows) {
        this.num_of_rows = num_of_rows;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getNum_of_bracketed_shot() {
        return num_of_bracketed_shot;
    }

    public void setNum_of_bracketed_shot(int num_of_bracketed_shot) {
        this.num_of_bracketed_shot = num_of_bracketed_shot;
    }

    public String getBracketed_style() {
        return bracketed_style;
    }

    public void setBracketed_style(String bracketed_style) {
        this.bracketed_style = bracketed_style;
    }

    public int getAfter_shot_delay() {
        return after_shot_delay;
    }

    public void setAfter_shot_delay(int after_shot_delay) {
        this.after_shot_delay = after_shot_delay;
    }

    public int getStartup_delay() {
        return startup_delay;
    }

    public void setStartup_delay(int startup_delay) {
        this.startup_delay = startup_delay;
    }


    public int getContinuous_rotation() {
        return continuous_rotation;
    }

    public void setContinuous_rotation(int continuous_rotation) {
        this.continuous_rotation = continuous_rotation;
    }

    public int getFocus_delay() {
        return focus_delay;
    }

    public void setFocus_delay(int focus_delay) {
        this.focus_delay = focus_delay;
    }

    public int getBefore_shot_delay() {
        return before_shot_delay;
    }

    public void setBefore_shot_delay(int before_shot_delay) {
        this.before_shot_delay = before_shot_delay;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public int getMax_frame_rate() {
        return max_frame_rate;
    }

    public void setMax_frame_rate(int max_frame_rate) {
        this.max_frame_rate = max_frame_rate;
    }

    public int getNum_of_panoramas() {
        return num_of_panoramas;
    }

    public void setNum_of_panoramas(int num_of_panoramas) {
        this.num_of_panoramas = num_of_panoramas;
    }

    public int getDelay_between_panoramas() {
        return delay_between_panoramas;
    }

    public void setDelay_between_panoramas(int delay_between_panoramas) {
        this.delay_between_panoramas = delay_between_panoramas;
    }

    public int getShutter_signal_length() {
        return shutter_signal_length;
    }

    public void setShutter_signal_length(int shutter_signal_length) {
        this.shutter_signal_length = shutter_signal_length;
    }

    public int getFocus_signal_length() {
        return focus_signal_length;
    }

    public void setFocus_signal_length(int focus_signal_length) {
        this.focus_signal_length = focus_signal_length;
    }

    public int getCamera_wakeup() {
        return camera_wakeup;
    }

    public void setCamera_wakeup(int camera_wakeup) {
        this.camera_wakeup = camera_wakeup;
    }

    public int getCamera_wakeup_signal_length() {
        return camera_wakeup_signal_length;
    }

    public void setCamera_wakeup_signal_length(int camera_wakeup_signal_length) {
        this.camera_wakeup_signal_length = camera_wakeup_signal_length;
    }

    public int getCamera_wakeup_delay() {
        return camera_wakeup_delay;
    }

    public void setCamera_wakeup_delay(int camera_wakeup_delay) {
        this.camera_wakeup_delay = camera_wakeup_delay;
    }

    public int getSpeed_divider() {
        return speed_divider;
    }

    public void setSpeed_divider(int speed_divider) {
        this.speed_divider = speed_divider;
    }

    public String getSendString(){


        String string = "dataStart|200|"+ getMultiRowName() +
                "|" + getContinuous_rotation() + "|" + getNum_of_bracketed_shot() +
                "|" + AppConstant.bracketing_style_map.get(getBracketed_style())
                + "|" + getAfter_shot_delay() + "|" + getStartup_delay() + "|" + getFocus_delay() + "|" +
                getBefore_shot_delay() + "|"  + getSpeed() + "|" + getAcceleration() + "|" +
                getMax_frame_rate() + "|" + getNum_of_panoramas() + "|" + getDelay_between_panoramas()  + "|" +
                getShutter_signal_length() + "|" + getFocus_signal_length() + "|" + getCamera_wakeup() + "|" +
                getCamera_wakeup_signal_length() + "|" + getCamera_wakeup_delay() + "|" + getSpeed_divider() + "|dataEnd" ;
        return string.replaceAll("\\s+","");
    }

    public String getSendStringRowInformation(){
        String elevationArray[] = getElevation().split(" ");
        String positionArray[] = getPosition().split(" ");
        String directionArray[] = getDirection().split(" ");
        String row_profile = "";

        for(int i=0;i<elevationArray.length;i++){

            row_profile = row_profile + elevationArray[i] + "|" + positionArray[i] + "|" + AppConstant.direction_map.get(directionArray[i]) + "|";
        }

        String string = "dataStart|201|"+  getNum_of_rows() + "|" + row_profile +  "dataEnd" ;
        return string.replaceAll("\\s+","");
    }
}
