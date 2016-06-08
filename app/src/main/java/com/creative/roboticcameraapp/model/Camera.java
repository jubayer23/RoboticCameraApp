package com.creative.roboticcameraapp.model;


/**
 * Created by Sajid Ali on 5/5/2016.
 */

public class Camera {

    public int id;

    public String cameraName;
    public double sensorWidth;
    public double sensorHeight;

    public Camera(){

    }

    public Camera(int id, String cameraName, double sensorWidth, double sensorHeight) {
        this.id = id;
        this.cameraName = cameraName;
        this.sensorWidth = sensorWidth;
        this.sensorHeight = sensorHeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public double getSensorWidth() {
        return sensorWidth;
    }

    public void setSensorWidth(double sensorWidth) {
        this.sensorWidth = sensorWidth;
    }

    public double getSensorHeight() {
        return sensorHeight;
    }

    public void setSensorHeight(double sensorHeight) {
        this.sensorHeight = sensorHeight;
    }

}
