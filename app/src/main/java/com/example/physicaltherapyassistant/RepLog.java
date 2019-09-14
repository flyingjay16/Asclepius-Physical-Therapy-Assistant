package com.example.physicaltherapyassistant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RepLog {
    private int facePullReps;
    private int lowerTrapsReps;
    private int rotatorCuffReps;
    private int swimmersReps;
    private String timeDate;

    public RepLog(int facePullReps, int lowerTrapsReps, int rotatorCuffReps, int swimmersReps) {
        timeDate = new SimpleDateFormat("MM dd yy HH:mm:ss").format(new Date());
        this.facePullReps = facePullReps;
        this.lowerTrapsReps = lowerTrapsReps;
        this.rotatorCuffReps = rotatorCuffReps;
        this.swimmersReps = swimmersReps;
    }

    public int getFacePullReps() {
        return facePullReps;
    }

    public void setFacePullReps(int facePullReps) {
        this.facePullReps = facePullReps;
    }

    public int getLowerTrapsReps() {
        return lowerTrapsReps;
    }

    public void setLowerTrapsReps(int lowerTrapsReps) {
        this.lowerTrapsReps = lowerTrapsReps;
    }

    public int getRotatorCuffReps() {
        return rotatorCuffReps;
    }

    public void setRotatorCuffReps(int rotatorCuffReps) {
        this.rotatorCuffReps = rotatorCuffReps;
    }

    public int getSwimmersReps() {
        return swimmersReps;
    }

    public void setSwimmersReps(int swimmersReps) {
        this.swimmersReps = swimmersReps;
    }

    public String getTimeDate() {
        return timeDate;
    }
}
