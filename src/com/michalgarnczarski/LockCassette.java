package com.michalgarnczarski;

public class LockCassette {
    private int width;
    private int offset;

    public LockCassette(int width, int offset) {
        this.width = width;
        this.offset = offset;
    }

    public int getWidth() {
        return width;
    }

    public int getOffset() {
        return offset;
    }
}
