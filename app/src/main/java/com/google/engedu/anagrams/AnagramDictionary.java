package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet words=new HashSet();
    private ArrayList listOfWords=new ArrayList();
    private String element;
    private HashMap conversions=new HashMap();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            words.add(word);
            listOfWords.add(word);
            ArrayList arr=new ArrayList();
            element=order(word);
            if(!conversions.containsKey(element)){
                arr.add(word);
                conversions.put(element,arr);
            }
            else{
                arr=(ArrayList)conversions.get(element);
                arr.add(word);
                conversions.put(element,arr);
            }
        }
    }


    public boolean isGoodWord(String word, String base) {

        boolean result=true;
        if(words.contains(word)&& !base.contains(word))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();
            String newkey=order(targetWord);
            if (conversions.containsKey(newkey)) {
                resultant = new ArrayList();
                resultant = (ArrayList) conversions.get(newkey);
                for (int a = 0; a < resultant.size(); a++)
                    result.add(String.valueOf(resultant.get(a)));

            }



        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> resultant;
        String newkey;
        ArrayList<String> result = new ArrayList<String>();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            String newword = word + letter;
            newkey = order(newword);
            if (conversions.containsKey(newkey)) {
                resultant = (ArrayList) conversions.get(newkey);
                for (int a = 0; a < resultant.size(); a++) {
                    result.add(String.valueOf(resultant.get(a)));
                }
            }

        }

        for (int i = 0; i < result.size(); i++) {
            if ((result.get(i).substring(0, result.get(i).length() - 1).equals(word)) || (result.get(i).substring(1)).equals(word)) {
                result.remove(i);
            }
        }
            return result;

    }



    public String pickGoodStarterWord() {
        int n;
        String randomWord=null;
        ArrayList res;
        boolean trueOrFalse=true;
        while(trueOrFalse){
           n=random.nextInt(62500)+1;
            randomWord=(String)listOfWords.get(n);
            res=new ArrayList();
            res=(ArrayList)conversions.get(order(randomWord));
            if(res.size()>=MIN_NUM_ANAGRAMS){
                trueOrFalse=false;
            }
        }
    return randomWord;
    }
    public String order(String word){
        char[] letters=word.toCharArray();
        Arrays.sort(letters);
        String element=new String(letters);
        return element;
    }
}
