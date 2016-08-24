package com.creative.roboticcameraapp.model;

import com.creative.roboticcameraapp.appdata.AppConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;

public class MultiRow
{
    int acceleration;
    int after_shot_delay;
    int before_shot_delay;
    String bracketed_style;
    int camera_wakeup;
    int camera_wakeup_delay;
    int camera_wakeup_signal_length;
    String continuosRotationShutter;
    int continuous_rotation;
    int delay_between_panoramas;
    String direction;
    String elevation;
    int focus_delay;
    int focus_signal_length;
    int id;
    float max_frame_rate;
    String multiRowName;
    int num_of_bracketed_shot;
    int num_of_panoramas;
    int num_of_rows;
    int panorama_width;
    String position;
    String return_to_start;
    int shutter_signal_length;
    int speed;
    int speed_divider;
    int startup_delay;
    String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public int getAcceleration()
    {
        return this.acceleration;
    }

    public int getAfter_shot_delay()
    {
        return this.after_shot_delay;
    }

    public int getBefore_shot_delay()
    {
        return this.before_shot_delay;
    }

    public String getBracketed_style()
    {
        return this.bracketed_style;
    }

    public int getCamera_wakeup()
    {
        return this.camera_wakeup;
    }

    public int getCamera_wakeup_delay()
    {
        return this.camera_wakeup_delay;
    }

    public int getCamera_wakeup_signal_length()
    {
        return this.camera_wakeup_signal_length;
    }

    public String getContinuosRotationShutter()
    {
        return this.continuosRotationShutter;
    }

    public int getContinuous_rotation()
    {
        return this.continuous_rotation;
    }

    public int getDelay_between_panoramas()
    {
        return this.delay_between_panoramas;
    }

    public String getDirection()
    {
        return this.direction;
    }

    public String getElevation()
    {
        return this.elevation;
    }

    public int getFocus_delay()
    {
        return this.focus_delay;
    }

    public int getFocus_signal_length()
    {
        return this.focus_signal_length;
    }

    public int getId()
    {
        return this.id;
    }

    public float getMax_frame_rate()
    {
        return this.max_frame_rate;
    }

    public String getMultiRowName()
    {
        return this.multiRowName;
    }

    public int getNum_of_bracketed_shot()
    {
        return this.num_of_bracketed_shot;
    }

    public int getNum_of_panoramas()
    {
        return this.num_of_panoramas;
    }

    public int getNum_of_rows()
    {
        return this.num_of_rows;
    }

    public int getPanorama_width()
    {
        return this.panorama_width;
    }

    public String getPosition()
    {
        return this.position;
    }

    public String getReturn_to_start()
    {
        return this.return_to_start;
    }

    public String getSendString()
    {
        return ("dataStart|200|"
                + getMultiRowName().replaceAll("\\s+", "_") + "|"
                + getPanorama_width() + "|"
                + getNum_of_bracketed_shot() + "|"
                + AppConstant.bracketing_style_map.get(getBracketed_style()) + "|"
                + getStartup_delay() + "|"
                + getFocus_delay() + "|"
                + getBefore_shot_delay() + "|"
                + getAfter_shot_delay() + "|"
                + getSpeed() + "|"
                + getAcceleration() + "|"
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
                + getMax_frame_rate() + "|dataEnd").replaceAll("\\s+", "");
    }

    public String getSendStringRowInformation()
    {
        String[] arrayOfString1 = getElevation().split(" ");
        String[] arrayOfString2 = getPosition().split(" ");
        String[] arrayOfString3 = getDirection().split(" ");
        String str = "";
        for (int i = 0; i < arrayOfString1.length; i++) {
            str = str + arrayOfString1[i] + "|" + arrayOfString2[i] + "|" + AppConstant.direction_map.get(arrayOfString3[i]) + "|";
        }
        return ("dataStart|201|" + getNum_of_rows() + "|" + str + "dataEnd").replaceAll("\\s+", "");
    }

    public int getShutter_signal_length()
    {
        return this.shutter_signal_length;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public int getSpeed_divider()
    {
        return this.speed_divider;
    }

    public int getStartup_delay()
    {
        return this.startup_delay;
    }

    public void setAcceleration(int paramInt)
    {
        this.acceleration = paramInt;
    }

    public void setAfter_shot_delay(int paramInt)
    {
        this.after_shot_delay = paramInt;
    }

    public void setBefore_shot_delay(int paramInt)
    {
        this.before_shot_delay = paramInt;
    }

    public void setBracketed_style(String paramString)
    {
        this.bracketed_style = paramString;
    }

    public void setCamera_wakeup(int paramInt)
    {
        this.camera_wakeup = paramInt;
    }

    public void setCamera_wakeup_delay(int paramInt)
    {
        this.camera_wakeup_delay = paramInt;
    }

    public void setCamera_wakeup_signal_length(int paramInt)
    {
        this.camera_wakeup_signal_length = paramInt;
    }

    public void setContinuosRotationShutter(String paramString)
    {
        this.continuosRotationShutter = paramString;
    }

    public void setContinuous_rotation(int paramInt)
    {
        this.continuous_rotation = paramInt;
    }

    public void setDelay_between_panoramas(int paramInt)
    {
        this.delay_between_panoramas = paramInt;
    }

    public void setDirection(String paramString)
    {
        this.direction = paramString;
    }

    public void setElevation(String paramString)
    {
        this.elevation = paramString;
    }

    public void setFocus_delay(int paramInt)
    {
        this.focus_delay = paramInt;
    }

    public void setFocus_signal_length(int paramInt)
    {
        this.focus_signal_length = paramInt;
    }

    public void setId(int paramInt)
    {
        this.id = paramInt;
    }

    public void setMax_frame_rate(float paramFloat)
    {
        this.max_frame_rate = paramFloat;
    }

    public void setMultiRowName(String paramString)
    {
        this.multiRowName = paramString;
    }

    public void setNum_of_bracketed_shot(int paramInt)
    {
        this.num_of_bracketed_shot = paramInt;
    }

    public void setNum_of_panoramas(int paramInt)
    {
        this.num_of_panoramas = paramInt;
    }

    public void setNum_of_rows(int paramInt)
    {
        this.num_of_rows = paramInt;
    }

    public void setPanorama_width(int paramInt)
    {
        this.panorama_width = paramInt;
    }

    public void setPosition(String paramString)
    {
        this.position = paramString;
    }

    public void setReturn_to_start(String paramInt)
    {
        this.return_to_start = paramInt;
    }

    public void setShutter_signal_length(int paramInt)
    {
        this.shutter_signal_length = paramInt;
    }

    public void setSpeed(int paramInt)
    {
        this.speed = paramInt;
    }

    public void setSpeed_divider(int paramInt)
    {
        this.speed_divider = paramInt;
    }

    public void setStartup_delay(int paramInt)
    {
        this.startup_delay = paramInt;
    }

    public static class NameComparatorAsending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof MultiRow) || !(o2 instanceof MultiRow))
                throw new ClassCastException();

            MultiRow e1 = (MultiRow) o1;
            MultiRow e2 = (MultiRow) o2;

            return e1.getMultiRowName().compareTo(e2.getMultiRowName());

        }
    }

    public static class NameComparatorDesending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof MultiRow) || !(o2 instanceof MultiRow))
                throw new ClassCastException();

            MultiRow e1 = (MultiRow) o1;
            MultiRow e2 = (MultiRow) o2;

            return e2.getMultiRowName().compareTo(e1.getMultiRowName());

        }
    }

    public static class DateComparatorAsending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof MultiRow) || !(o2 instanceof MultiRow))
                throw new ClassCastException();

            MultiRow e1 = (MultiRow) o1;
            MultiRow e2 = (MultiRow) o2;

            try
            {
                DateFormat format = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
                return format.parse(e1.getCreated_at()).compareTo(format.parse(e2.getCreated_at()));
            }
            catch (Exception e)
            {
                return 0;
            }

        }
    }
}
