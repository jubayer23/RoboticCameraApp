package com.creative.roboticcameraapp.model;

public class Lens {
    public int id;

    public String lensName;
    public double focalLength;
    public boolean fishEyeStatus;

    public Lens(int id, String lensName, double focalLength, boolean fishEyeStatus) {
        this.id = id;
        this.lensName = lensName;
        this.focalLength = focalLength;
        this.fishEyeStatus = fishEyeStatus;
    }

    public Lens(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLensName() {
        return lensName;
    }

    public void setLensName(String lensName) {
        this.lensName = lensName;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    public boolean isFishEyeStatus() {
        return fishEyeStatus;
    }

    public void setFishEyeStatus(boolean fishEyeStatus) {
        this.fishEyeStatus = fishEyeStatus;
    }
}
