package com.michalgarnczarski;

import java.util.ArrayList;
import java.util.List;

public class LocksList {
    private List<Lock> locks;

    public LocksList() {
        this.locks = new ArrayList<>();
    }

    public void addLock(Lock lock) {
        this.locks.add(lock);
    }

    public List<Lock> getLocks() {
        return this.locks;
    }
}
