package com.example.androidmessenger;

public class DataClass {
    private String dataTitle;
    private int dataDesc;
    private String dataLang;
    private int dataImage;
    private int dataImageShifr;

    public String getDataTitle() {
        return dataTitle;
    }
    public int getDataDesc() {
        return dataDesc;
    }
    public String getDataLang() {
        return dataLang;
    }
    public int getDataImage() {
        return dataImage;
    }
    public int getDataImageShifr() {
        return dataImageShifr;
    }

    public DataClass(String dataTitle, int dataDesc, String dataLang, int dataImage, int dataImageShifr) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.dataImageShifr=dataImageShifr;
    }
}