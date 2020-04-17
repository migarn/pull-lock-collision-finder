package com.michalgarnczarski;

public class PullLocationCalculator {
    private int sashHeight;
    private Pull pull;

    public PullLocationCalculator(int sashHeight, Pull pull) {
        this.sashHeight = sashHeight;
        this.pull = pull;
    }

    public int defineLocationMode() {
        if ((this.sashHeight - pull.getFixingsSpacing()) / 2.0 > 860.0) {
            // 1 means 'standard' mode
            return 1;
        }
        // 0 means 'symmetrical' mode
        return 0;
    }
}
