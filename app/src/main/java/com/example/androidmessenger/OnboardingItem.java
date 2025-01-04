package com.example.androidmessenger;
public class OnboardingItem {
    private String title;
    private int image;
    private String description;

    public OnboardingItem(String title, int image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public OnboardingItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public OnboardingItem(String title, int image) {
        this.title = title;
        this.image = image;
        this.description = "";
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}