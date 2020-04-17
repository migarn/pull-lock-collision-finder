package com.michalgarnczarski;

public class PullLocationCalculator {
    private int sashHeight;
    private Pull pull;
    private int locationMode;
    private int lowerFixingLocation;
    private int upperFixingLocation;

    public PullLocationCalculator(int sashHeight, Pull pull) {
        this.sashHeight = sashHeight;
        this.pull = pull;
        if ((sashHeight - pull.getFixingsSpacing()) / 2.0 > 860.0) {
            // 1 means 'standard' mode
            this.locationMode = 1;
            this.lowerFixingLocation = 860;
        } else {
            // 0 means 'symmetrical' mode
            this.locationMode = 0;
            this.lowerFixingLocation = (int) ((sashHeight - pull.getFixingsSpacing()) / 2.0);
        }
        this.upperFixingLocation = this.lowerFixingLocation + pull.getFixingsSpacing();
    }

    public int getLocationMode() {
        return locationMode;
    }

    public int getLowerFixingLocation() {
        return lowerFixingLocation;
    }

    public int getUpperFixingLocation() {
        return upperFixingLocation;
    }
}
