package com.dynamicminds.ffwsafetyalert;

public class Problem {

    private int problemId;
    private String type;
    private double longitude;
    private double latitude;

    public Problem(int problemId, String type, double longitude, double latitude){
        this.problemId = problemId;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }
}
