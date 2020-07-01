package com.michalgarnczarski;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) {
        LocksParser locksParser = new LocksParser();

        try {
            LocksList locksList = locksParser.parseLocksFromFile("locks.dat");

            for (Lock lock : locksList.getLocks()) {
                System.out.println("Name: " + lock.getName() + ", cassettes number: " + lock.getCassettes().length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
