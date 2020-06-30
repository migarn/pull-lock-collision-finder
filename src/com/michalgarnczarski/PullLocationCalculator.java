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

        // Pull location mode is being calculated based on the sash and pull dimensions ratio.
        // Pull can be located symmetrical on the sash when the pull's lowest point is located beneath 760mm from the
        // sash lowest level. Otherwise, pull is located with 'standard' mode (pull's lowest point is located on the
        //  level of 760mm from the sash lowest level).

        if ((sashHeight - pull.getLength()) / 2.0 > 760.0) {
            this.locationMode = 1;      // 1 means 'standard' mode
            this.lowerFixingLocation = 760 + (pull.getLength() - pull.getFixingsSpacing()) / 2;
        } else {
            this.locationMode = 0;      // 0 means 'symmetrical' mode
            this.lowerFixingLocation = (int) ((sashHeight - pull.getFixingsSpacing()) / 2.0);
        }

        // Regardless of location mode upper fixing location is calculated based on lower fixing location
        // and fixings spacing.

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

    public int getSashHeight() {
        return sashHeight;
    }

    public Pull getPull() {
        return pull;
    }
}
