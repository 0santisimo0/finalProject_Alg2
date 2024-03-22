package org.example.project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtReader {
    public ArrayList<String[]> getSeparatedWords(String path) {
        ArrayList<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                String[] lineStr = new String[words.length];
                System.arraycopy(words, 0, lineStr, 0, words.length);
                lines.add(lineStr);
            }
        } catch (IOException e) {System.err.println(e.getMessage());}
        return lines;
    }
}
