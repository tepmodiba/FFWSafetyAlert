package com.dynamicminds.ffwsafetyalert;

public class Problem {

    private int problemId;
    private String type;
    private float longitude;
    private float latitude;

    public Problem(int problemId, String type, float longitude, float latitude){
        this.problemId = problemId;
        this.type = type;
        this.longitude = longitude;
        this.latitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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
