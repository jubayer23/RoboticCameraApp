package com.creative.roboticcameraapp.model;

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
    String backeting_style;
    int after_shot_delay;
    int startup_delay;

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

    public String getBacketing_style() {
        return backeting_style;
    }

    public void setBacketing_style(String backeting_style) {
        this.backeting_style = backeting_style;
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
}
