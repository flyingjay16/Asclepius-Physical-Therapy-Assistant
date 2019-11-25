package com.example.physicaltherapyassistant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RepLog {
    private int facePullCReps;
    private int facePullTReps;
    private int lowerTrapsCReps;
    private int lowerTrapsTReps;
    private int rotatorCuffCReps;
    private int rotatorCuffTReps;
    private int swimmersCReps;
    private int swimmersTReps;

    private int percentFacePullReps;

    public int getPercentFacePullReps() {
        return percentFacePullReps;
    }

    public int getPercentLowerTrapsReps() {
        return percentLowerTrapsReps;
    }

    public int getPercentRotatorCuffReps() {
        return percentRotatorCuffReps;
    }

    public int getPercentSwimmersReps() {
        return percentSwimmersReps;
    }

    private int percentLowerTrapsReps;
    private int percentRotatorCuffReps;
    private int percentSwimmersReps;

    private String timeDate;

    private ArrayList<Integer> facePullReps;
    private ArrayList<Integer> lowerTrapReps;
    private ArrayList<Integer> rotatorCuffReps;
    private ArrayList<Integer> swimmersReps;

    //private static

    public RepLog() {

    }


    public RepLog(int facePullCReps, int lowerTrapsCReps, int rotatorCuffCReps, int swimmersCReps, int facePullTReps, int lowerTrapsTReps, int rotatorCuffTReps, int swimmersTReps) {
        timeDate = new SimpleDateFormat("MMM dd, yyyy | HH:mm").format(new Date());

        this.facePullCReps = facePullCReps;
        this.lowerTrapsCReps = lowerTrapsCReps;
        this.rotatorCuffCReps = rotatorCuffCReps;
        this.swimmersCReps = swimmersCReps;

        this.facePullTReps = facePullTReps;
        this.lowerTrapsTReps = lowerTrapsTReps;
        this.rotatorCuffTReps = rotatorCuffTReps;
        this.swimmersTReps = swimmersTReps;
    }

    public RepLog(ArrayList<Integer> facePullReps, ArrayList<Integer> lowerTrapReps, ArrayList<Integer> rotatorCuffReps, ArrayList<Integer> swimmersReps) {
        timeDate = new SimpleDateFormat("MMM dd, yyyy | HH:mm").format(new Date());

        this.facePullReps = facePullReps;
        facePullCReps = facePullReps.get(0);
        facePullTReps = facePullReps.get(1);

        this.lowerTrapReps = facePullReps;
        lowerTrapsCReps = facePullReps.get(0);
        lowerTrapsTReps = facePullReps.get(1);

        this.rotatorCuffReps = rotatorCuffReps;
        rotatorCuffCReps = rotatorCuffReps.get(0);
        rotatorCuffTReps = rotatorCuffReps.get(1);

        this.swimmersReps = swimmersReps;
        swimmersCReps = swimmersReps.get(0);
        swimmersTReps = swimmersReps.get(1);
    }

    public void convertPercentages() {
        this.percentFacePullReps = (int)(((double)this.facePullCReps / (double)this.facePullTReps)*100);
        this.percentLowerTrapsReps = (int)(((double)this.lowerTrapsCReps / (double)this.lowerTrapsTReps)*100);
        this.percentRotatorCuffReps = (int)(((double)this.rotatorCuffCReps / (double)this.rotatorCuffTReps)*100);
        this.percentSwimmersReps = (int)(((double)this.swimmersCReps / (double)this.swimmersTReps)*100);
    }

    public int getFacePullCReps() {
        return facePullCReps;
    }

    public void setFacePullCReps(int facePullReps) {
        this.facePullCReps = facePullReps;
    }

    public int getLowerTrapsCReps() {
        return lowerTrapsCReps;
    }

    public void setLowerTrapsCReps(int lowerTrapsReps) {
        this.lowerTrapsCReps = lowerTrapsReps;
    }

    public int getRotatorCuffCReps() {
        return rotatorCuffCReps;
    }

    public void setRotatorCuffCReps(int rotatorCuffReps) {
        this.rotatorCuffCReps = rotatorCuffReps;
    }

    public int getSwimmersCReps() {
        return swimmersCReps;
    }

    public void setSwimmersCReps(int swimmersReps) {
        this.swimmersCReps = swimmersReps;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public int getFacePullTReps() {
        return facePullTReps;
    }

    public void setFacePullTReps(int facePullTReps) {
        this.facePullTReps = facePullTReps;
    }

    public int getLowerTrapsTReps() {
        return lowerTrapsTReps;
    }

    public void setLowerTrapsTReps(int lowerTrapsTReps) {
        this.lowerTrapsTReps = lowerTrapsTReps;
    }

    public int getRotatorCuffTReps() {
        return rotatorCuffTReps;
    }

    public void setRotatorCuffTReps(int rotatorCuffTReps) {
        this.rotatorCuffTReps = rotatorCuffTReps;
    }

    public int getSwimmersTReps() {
        return swimmersTReps;
    }

    public void setSwimmersTReps(int swimmersTReps) {
        this.swimmersTReps = swimmersTReps;
    }

    public String toString() {
        String str = "Log: \nFace Pull Correct Reps: " + facePullCReps + "\nFace Pull Total Reps: " + facePullTReps;
        str += "\nLower Trap Correct Reps: " + lowerTrapsCReps + "\nLower Trap Total Reps: " + lowerTrapsTReps;
        str += "\nRotator Cuff Correct Reps: " + rotatorCuffCReps + "\nRotator Cuff Correct Reps: " + rotatorCuffTReps;
        str += "\nSwimmers Correct Reps: " + swimmersCReps + "\nSwimmer Total Reps: " + swimmersTReps;

        return str;
    }
}
