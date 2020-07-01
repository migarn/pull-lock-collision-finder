package com.michalgarnczarski;

public class LockCassette {
    private int width;

    // Offset is meant to be a distance between handle location and axis of the specific casette
    private int offset;

    public LockCassette(int width, int offset) {
        this.width = width;
        this.offset = offset;
    }

    public int getWidth() {
        return this.width;
    }

    public int getOffset() {
        return this.offset;
    }
}
