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

        // Method finds pull and lock collisions. Collision is returned as two elements double array.
        // Array's first element is lower fixing's minimal offset required to get rid of a collision and array's
        // second element is upper fixing's minimal offset required to get rid of a collision.
        // Tolerance is added to ensure safe distance between lock and fixing's screw.

        double[] result = {0.0, 0.0};

        for (LockCassette cassette : lock.getCassettes()) {

            // Iterate over all lock cassettess and if collision is find assign offset in result.
            // One fixing cannot collide with two cassettes so no need to check which cassette collides "more".

            double lowerBoundary = handleLocation + cassette.getOffset() - cassette.getWidth() / 2.0 - tolerance;
            double upperBoundary = handleLocation + cassette.getOffset() + cassette.getWidth() / 2.0 + tolerance;

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
