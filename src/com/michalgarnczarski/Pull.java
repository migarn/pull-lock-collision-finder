package com.michalgarnczarski;

public class Pull {
    private int length;
    private int fixingsSpacing;

    public Pull(int length, int fixingsSpacing) {
        this.length = length;
        this.fixingsSpacing = fixingsSpacing;
    }

    public int getLength() {
        return length;
    }

    public int getFixingsSpacing() {
        return fixingsSpacing;
    }
}
