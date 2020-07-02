package com.michalgarnczarski;

import com.sun.media.sound.InvalidDataException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocksParser {

    public LocksList parseLocksFromFile(String filename) throws IOException {

        // Method parses locks list (LocksList object) from file. File should look like:
        //
        //      lock name;cassette width,cassette offset;cassette width,cassette offset
        //      lock name;cassette width,cassette offset
        //      lock name;cassette width,cassette offset;cassette width,cassette offset;cassette width,cassette offset
        //
        // Each cassette should be separated by ";" symbol.
        // Method handles basic problems with file.

        LocksList locksList = new LocksList();
        List<String> fileAsStringsList = readLines(filename);

        for (String line : fileAsStringsList) {
            Lock lock = parseLock(line);

            if (lock != null )

            locksList.addLock(lock);
        }

        return locksList;
    }

    private List<String> readLines(String filename) throws IOException {

        // Method reads file and returns it as list.

        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        bufferedReader.close();
        return lines;
    }

    private Lock parseLock(String line) {

        // Method parses locks from file lines. If line is empty or set incorrectly, method returns null.

        try {
            String[] lockParameters = line.split(";");

            if (lockParameters.length == 1) {
                throw new InvalidDataException();
            }

            String lockName = lockParameters[0];
            LockCassette[] cassettes = new LockCassette[lockParameters.length - 1];

            for (int i = 1; i < lockParameters.length; i++) {
                String[] cassetteParameters = lockParameters[i].split(",");
                int width = Integer.parseInt(cassetteParameters[0]);
                int offset = Integer.parseInt(cassetteParameters[1]);

                cassettes[i - 1] = new LockCassette(width, offset);
            }

            return new Lock(lockName, cassettes);
        } catch (Exception e) {
            System.out.println("Wrong line found.");
            return null;
        }
    }
}
