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


            Lock lock = locksList.getLocks().get(0);

            CollisionFinder collisionFinder = new CollisionFinder(lock,1000,500,1300);
            double[] collision = collisionFinder.findCollision(20);
            System.out.println("\nDolna nóżka koliduje na " + collision[0] + "mm\nGórna nóżka koliduje na " + collision[1] + "mm");


        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
