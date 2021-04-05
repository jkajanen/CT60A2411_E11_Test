package com.example.ct60a2411_e11_test;

import android.graphics.Color;
import java.util.ArrayList;

import static android.graphics.Color.WHITE;

public class MySetting {

    private boolean editable;
    private int viewFontsize;
    private int viewWidth;
    private int viewHeight;
    private int viewColor;
    private String viewText = "";
    private String viewLanguage = "";



    public MySetting(){
        editable = false;
        viewFontsize = 12;
        viewWidth = 320;
        viewHeight = 200;
        viewColor = WHITE;
        viewText = "Still empty";
        viewLanguage = "Suomi";
    }

    public static MySetting setting = new MySetting(); // Singleton!!!

    public static MySetting getInstance() {
        return setting;
    } // Singleton!!!

    public void setEditable(boolean set) {
        if (set) editable = true;
        else editable = false;
        System.out.println(set);
    }
    public boolean getEditable() { return editable; }

    public void setViewFontsize(int size) { viewFontsize = size; }
    public int getViewFontsize() { return viewFontsize; }

    public void setViewWidth(int width) { viewWidth = width; }
    public int getViewWidth() { return viewWidth; }

    public void setViewHeight(int height) { viewHeight = height; }
    public int getViewHeight() { return viewHeight; }

    public void setViewColor(int color) {viewColor = color;}
    public int getViewColor() { return viewColor; }

    public void setViewLanguage(String language) {viewLanguage = language;}
    public String getViewlanguage() { return viewLanguage; }

}
