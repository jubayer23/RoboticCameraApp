package com.creative.roboticcameraapp.model;

/**
 * Created by comsol on 05-Jun-16.
 */
public class SingleRow {


    int id;
    String singleRowName;
    int number_of_shooting_position;
    int number_of_bracketed_shot;
    String bracketed_style;

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

    public int getNumber_of_bracketed_shot() {
        return number_of_bracketed_shot;
    }

    public void setNumber_of_bracketed_shot(int number_of_bracketed_shot) {
        this.number_of_bracketed_shot = number_of_bracketed_shot;
    }

    public String getBracketed_style() {
        return bracketed_style;
    }

    public void setBracketed_style(String bracketed_style) {
        this.bracketed_style = bracketed_style;
    }
}
