package com.michalgarnczarski;

public class CollisionFinder {
    private Lock lock;
    double handleLocation;
    double lowerFixingLocation;
    double upperFixingLocation;

    public CollisionFinder(Lock lock, double handleLocation, double lowerFixingLocation, double upperFixingLocation) {
        this.lock = lock;
        this.handleLocation = handleLocation;
        this.lowerFixingLocation = lowerFixingLocation;
        this.upperFixingLocation = upperFixingLocation;
    }

    public double[] findCollision(double tolerance) {
        double[] result = {0.0, 0.0}; // {lower fixing range, upper fixing range}

        //boolean isCollision = false;

        for (LockCassette cassette : lock.getCassettes()) {
            double lowerBoundary = handleLocation + cassette.getOffset() - cassette.getWidth() / 2.0 - tolerance;
            double upperBoundary = handleLocation + cassette.getOffset() + cassette.getWidth() / 2.0 + tolerance;

//            double collisionRange = 0.0;
//            double collidingfixing = 0.0; // 1.0 - lower, 2.0 - upper



            // jedna nóżka nie może kolidować z dwoma kasetamio jednocześńie

            if (lowerFixingLocation > lowerBoundary && lowerFixingLocation < upperBoundary) {
                result[0] = lowerFixingLocation - lowerBoundary < upperBoundary - lowerFixingLocation ?
                        lowerBoundary - lowerFixingLocation : upperBoundary - lowerFixingLocation;
            }

            if (upperFixingLocation > lowerBoundary && upperFixingLocation < upperBoundary) {
                result[1] = upperFixingLocation - lowerBoundary < upperBoundary - upperFixingLocation ?
                        lowerBoundary - upperFixingLocation : upperBoundary - upperFixingLocation;
            }

        }



        return result;
    }
}
