package org.example;

import java.util.ArrayList;

public class TextValidator {
    private final TextSeparator textSeparator;
    private ArrayList<String> misspelledWords;

    public TextValidator(ArrayList<String> misspelledWords) {
        this.textSeparator = new TextSeparator();
        this.misspelledWords = misspelledWords;
    }

    private boolean areCharactersSimilar(String w1, String w2) {
        char[] charsArray1 = textSeparator.getWordCharacters(w1);
        char[] charsArray2 = textSeparator.getWordCharacters(w2);

        int charsLength1 = charsArray1.length;
        int charsLength2 = charsArray2.length;

        int[][] dp = new int[charsLength1+1][charsLength2+1];

        for (int i = 1; i <= charsLength1; i++) {
            for (int j = 1; j <= charsLength2; j++) {
                if (charsArray1[i-1] == charsArray2[j-1]) dp[i][j] = dp[i-1][j-1]+1;
                else dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
            }
        }
        double lcs = dp[charsLength1][charsLength2];
        double similarity = lcs/ (Math.max(charsLength1, charsLength2))*100;
        return areSimilarWords(w1,w2,similarity);
    }

    private boolean areSimilarWords(String w1, String w2, double similarity) {
        boolean areSimilar = similarity >= 55;
        if (areSimilar && w1.toLowerCase().charAt(0) == w2.toLowerCase().charAt(0)) {
            misspelledWords.add(w1);
            misspelledWords.add(w2);
        } return areSimilar;
    }
    public double compareSentences(String s1, String s2) {
        String[] wordsArray1 = textSeparator.getSentenceWords(s1);
        String[] wordsArray2 = textSeparator.getSentenceWords(s2);

        int wordsLength1 = wordsArray1.length, wordsLength2 = wordsArray2.length;
        int[][] dp = new int[wordsLength1+1][wordsLength2+1];

        for (int i = 1; i <= wordsLength1; i++) {
            for (int j = 1; j <= wordsLength2; j++) {
                if (wordsArray1[i-1].equalsIgnoreCase(wordsArray2[j-1])
                        || areCharactersSimilar(wordsArray1[i-1],wordsArray2[j-1])) {
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                else dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
            }
        }
        return dp[wordsLength1][wordsLength2];
    }

    public double getSentencesLcs(ArrayList<String> sentences1, ArrayList<String> sentences2) {
        double maxLcsSentences = 0;
        for (int i = 0; i < sentences2.size(); i++) {
            double maxLcs = 0;
            for (int j = 0; j < sentences1.size(); j++) {
                double actualLcs = compareSentences(sentences2.get(i), sentences1.get(j));
                if (actualLcs > maxLcs) maxLcs = actualLcs;
            } maxLcsSentences += maxLcs;
        }
        return maxLcsSentences;
    }

    public String printMisspelledWords() {
        String misspelledView = "";
        for (int i = 0; i < misspelledWords.size();) {
            misspelledView += misspelledWords.get(i)+" - "+misspelledWords.get(i+1)+
                    ((i == misspelledWords.size()-2)?"":", ");
            i+=2;
        } return misspelledView;
    }

    public double getPlagiarismPercentage(String path1, String path2) {
        ArrayList<String> sentences1 = textSeparator.getTextSentences(path1);
        ArrayList<String> sentences2 = textSeparator.getTextSentences(path2);
        int length1 = textSeparator.countWords(path1);
        int length2 = textSeparator.countWords(path2);
        return (double) Math.round(getSentencesLcs(sentences1, sentences2)
                / Math.min(length1, length2) * 10000) /100;
    }
}
