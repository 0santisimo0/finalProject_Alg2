package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextSeparator {

    public ArrayList<String> getTextSentences(String path) {
        ArrayList<String> sentences = new ArrayList<>();
        String sentence = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    if (words[i].endsWith(".")) {
                        sentence += words[i].substring(0, words[i].length() - 1);
                        sentences.add(sentence);
                        sentence = "";
                    } else sentence += words[i]+" ";
                }
            }
        } catch (IOException e) {System.err.println(e.getMessage());}
        return sentences;
    }

    public int countWords(String filename) {
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                counter += words.length;
            }
        } catch (IOException e) {System.err.println(e.getMessage());}
        return counter;
    }

    public String[] getSentenceWords(String sentence) {
        return sentence.split("\\s+");
    }

    public char[] getWordCharacters(String word) {
        return word.toLowerCase().toCharArray();
    }
}
