package com.michalgarnczarski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocksParser {

    public LocksList parseLocksFromFile(String filename) throws IOException {

        // Description

        LocksList locksList = new LocksList();
        List<String> fileAsStringsList = readLines(filename);

        for (String line : fileAsStringsList) {

            // Tu docelowo obsłużyć błędną linię. Try parseLock?

            Lock lock = parseLock(line);
            locksList.addLock(lock);
        }




        return locksList;
    }

    private List<String> readLines(String filename) throws IOException {

        // Method reads file and returns it as list

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

        // Description

        String[] lockParameters = line.split(";");

        String lockName = lockParameters[0];
        LockCassette[] cassettes = new LockCassette[lockParameters.length - 1];

        for (int i = 1; i < lockParameters.length; i++) {
            String[] cassetteParameters = lockParameters[i].split(",");
            int width = Integer.parseInt(cassetteParameters[0]);
            int offset = Integer.parseInt(cassetteParameters[1]);

            cassettes[i - 1] = new LockCassette(width, offset);
        }

        return new Lock(lockName, cassettes);
    }
}
