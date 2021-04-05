package com.example.ct60a2411_e11_test;

import java.util.ArrayList;

public class MyLanguage {

    private int myLanguageCount = 3;

    public ArrayList<String> languageAList = new ArrayList<>();

    public MyLanguage(){
        String lang1 = "Suomi";
        String lang2 = "Ruotsi";
        String lang3 = "Englanti";

        languageAList.add(lang1);
        System.out.println(lang1);
        languageAList.add(lang2);
        System.out.println(lang2);
        languageAList.add(lang3);
        System.out.println(lang3);
    }

    public static MyLanguage language = new MyLanguage(); // Singleton!!!

    public static MyLanguage getInstance() {
        return language;
    } // Singleton!!!

    public int getLanguageCount(){return myLanguageCount;}

    public String getLanguage(int order) {
        return languageAList.get(order).toString();
    }
}
