package com.example.androidmessenger.modelClass;
/**
 * Модель для домашней страницы.
 * @author Иван Минаев
 * @version 1.0
 */
public class HomeModel {
    /** Поле заголовок */
    String title;

    /**
     * Конструктор без параметров.
     */
    public HomeModel(){

    }
    /**
     * Конструктор с параметром.
     *
     * @param title Заголовок.
     */
    public HomeModel(String title){
        this.title = title;
    }
    /**
     * Получить заголовок.
     *
     * @return Заголовок.
     */
    public String getTitle(){
        return title;
    }
    /**
     * Установить заголовок.
     *
     * @param title Заголовок.
     */
    public void setTitle(String title){
        this.title = title;
    }
}
