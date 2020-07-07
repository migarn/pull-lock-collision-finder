package com.michalgarnczarski;

public class CollisionFinder {
    private Lock lock;
    private double handleLocation;
    private double lowerFixingLocation;
    private double upperFixingLocation;

    public CollisionFinder(Lock lock, double handleLocation, double lowerFixingLocation, double upperFixingLocation) {
        this.lock = lock;
        this.handleLocation = handleLocation;
        this.lowerFixingLocation = lowerFixingLocation;
        this.upperFixingLocation = upperFixingLocation;
    }

    public double[] findCollision(double tolerance) {
        double[] result = {0.0, 0.0}; // {lower fixing range, upper fixing range}

        for (LockCassette cassette : lock.getCassettes()) {
            double lowerBoundary = handleLocation + cassette.getOffset() - cassette.getWidth() / 2.0 - tolerance;
            double upperBoundary = handleLocation + cassette.getOffset() + cassette.getWidth() / 2.0 + tolerance;

            System.out.println("lowerBoundary " + lowerBoundary); //wyw
            System.out.println("upperBoundary " + upperBoundary);   //wyw


            // jedna nóżka nie może kolidować z dwoma kasetamio jednocześńie

            if (lowerFixingLocation > lowerBoundary && lowerFixingLocation < upperBoundary) {
                result[0] = lowerFixingLocation - lowerBoundary < upperBoundary - lowerFixingLocation ?
                        lowerBoundary - lowerFixingLocation : upperBoundary - lowerFixingLocation;

                System.out.println("dolna koliduje");
            }

            if (upperFixingLocation > lowerBoundary && upperFixingLocation < upperBoundary) {
                result[1] = upperFixingLocation - lowerBoundary < upperBoundary - upperFixingLocation ?
                        lowerBoundary - upperFixingLocation : upperBoundary - upperFixingLocation;

                System.out.println("górna koliduje");
            }

        }

        return result;
    }
}
