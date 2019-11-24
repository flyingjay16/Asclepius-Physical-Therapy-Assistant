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
    private String timeDate;

    private ArrayList<Integer> facePullReps;
    private ArrayList<Integer> lowerTrapReps;
    private ArrayList<Integer> rotatorCuffReps;
    private ArrayList<Integer> swimmersReps;


    public RepLog(int facePullCReps, int lowerTrapsCReps, int rotatorCuffCReps, int swimmersCReps, int facePullTReps, int lowerTrapsTReps, int rotatorCuffTReps, int swimmersTReps) {
        timeDate = new SimpleDateFormat("MM dd yy HH:mm:ss").format(new Date());

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
        timeDate = new SimpleDateFormat("MM dd yy HH:mm:ss").format(new Date());

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
