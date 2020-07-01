package com.michalgarnczarski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocksParser {

    public LocksList parseLocksFromFile(String filename) {

        // Description

        LocksList locksList = new LocksList();




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
}
