package com.creative.roboticcameraapp.model;

import com.creative.roboticcameraapp.appdata.AppConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by comsol on 05-Jun-16.
 */
public class SingleRow {


    int id;
    String singleRowName;
    int number_of_shooting_position;
    int panorama_width;
    int continuous_rotation;
    int num_of_bracketed_shot;
    String bracketed_style;
    int after_shot_delay;
    int startup_delay;
    int focus_delay;
    int before_shot_delay;
    String direction;
    int speed;
    int acceleration;
    float max_frame_rate;
    int num_of_panoramas;
    int delay_between_panoramas;
    int shutter_signal_length;
    int focus_signal_length;
    int camera_wakeup;
    int camera_wakeup_signal_length;
    int camera_wakeup_delay;
    int speed_divider;
    float tilt_arm_elevation;
    String return_to_start;
    String continuosRotationShutter;
    String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPanorama_width() {
        return panorama_width;
    }

    public void setPanorama_width(int panorama_width) {
        this.panorama_width = panorama_width;
    }

    public float getTilt_arm_elevation() {
        return tilt_arm_elevation;
    }

    public void setTilt_arm_elevation(float tilt_arm_elevation) {
        this.tilt_arm_elevation = tilt_arm_elevation;
    }

    public String getReturn_to_start() {
        return return_to_start;
    }

    public void setReturn_to_start(String return_to_start) {
        this.return_to_start = return_to_start;
    }

    public String getContinuosRotationShutter() {
        return continuosRotationShutter;
    }

    public void setContinuosRotationShutter(String continuosRotationShutter) {
        this.continuosRotationShutter = continuosRotationShutter;
    }

    public SingleRow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingleRowName() {
        return singleRowName;
    }

    public void setSigleRowName(String name) {
        this.singleRowName = name;
    }

    public int getNumber_of_shooting_position() {
        return number_of_shooting_position;
    }

    public void setNumber_of_shooting_position(int number_of_shooting_position) {
        this.number_of_shooting_position = number_of_shooting_position;
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

    public void setSingleRowName(String singleRowName) {
        this.singleRowName = singleRowName;
    }

    public int getContinuous_rotation() {
        return continuous_rotation;
    }

    public void setContinuous_rotation(int continuous_rotation) {
        this.continuous_rotation = continuous_rotation;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public float getMax_frame_rate() {
        return max_frame_rate;
    }

    public void setMax_frame_rate(float max_frame_rate) {
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

    public String getSendString() {
        String string = "dataStart|100|"
                + getSingleRowName().replaceAll("\\s+", "_") + "|"
                + getPanorama_width() + "|"
                + getNumber_of_shooting_position() + "|"
                + getTilt_arm_elevation() + "|"

                + getNum_of_bracketed_shot() + "|"
                + AppConstant.bracketing_style_map.get(getBracketed_style()) + "|"
                + getStartup_delay() + "|"
                + getFocus_delay() + "|"
                + getBefore_shot_delay() + "|"
                + getAfter_shot_delay() + "|"
                + getSpeed() + "|"
                + getAcceleration() + "|"
                + AppConstant.direction_map.get(getDirection()) + "|"
                + getContinuous_rotation() + "|"
                + getContinuosRotationShutter() + "|"
                + getNum_of_panoramas() + "|"
                + getDelay_between_panoramas() + "|"
                + AppConstant.return_to_start_map.get(getReturn_to_start()) + "|"
                + getShutter_signal_length() + "|"
                + getFocus_signal_length() + "|"
                + getCamera_wakeup() + "|"
                + getCamera_wakeup_signal_length() + "|"
                + getCamera_wakeup_delay() + "|"
                + getMax_frame_rate()
                + "|dataEnd";
        return string.replaceAll("\\s+", "");
    }



   public static class NameComparatorAsending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof SingleRow) || !(o2 instanceof SingleRow))
                throw new ClassCastException();

            SingleRow e1 = (SingleRow) o1;
            SingleRow e2 = (SingleRow) o2;

            return e1.getSingleRowName().compareTo(e2.getSingleRowName());

        }
    }

    public static class NameComparatorDesending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof SingleRow) || !(o2 instanceof SingleRow))
                throw new ClassCastException();

            SingleRow e1 = (SingleRow) o1;
            SingleRow e2 = (SingleRow) o2;

            return e2.getSingleRowName().compareTo(e1.getSingleRowName());

        }
    }

    public static class DateComparatorAsending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof SingleRow) || !(o2 instanceof SingleRow))
                throw new ClassCastException();

            SingleRow e1 = (SingleRow) o1;
            SingleRow e2 = (SingleRow) o2;

            try
            {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return format.parse(e1.getCreated_at()).compareTo(format.parse(e2.getCreated_at()));
            }
            catch (Exception e)
            {
                return 0;
            }

        }
    }

}
