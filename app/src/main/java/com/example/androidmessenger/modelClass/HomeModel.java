package com.example.androidmessenger.modelClass;

public class HomeModel {
    String title;

    public HomeModel(){

    }
    public HomeModel(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
}
