package com.michalgarnczarski;

public class Lock {
    private String name;
    private LockCassette[] cassettes;

    public Lock(String name, LockCassette[] cassettes) {
        this.name = name;
        this.cassettes = cassettes;
    }

    public String getName() {
        return name;
    }

    public LockCassette[] getCassettes() {
        return cassettes;
    }
}
