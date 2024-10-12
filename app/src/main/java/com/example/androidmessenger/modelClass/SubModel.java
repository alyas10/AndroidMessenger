package com.example.androidmessenger.modelClass;

/**
 * Модель для дополнительного перехода.
 *
 * @author Иван Минаев
 * @version 1.0
 */
public class SubModel {
    String title, category;

    /**
     * Конструктор без параметров.
     */
    public SubModel(){

    }

    /**
     * Конструктор с параметрами.
     *
     * @param title Заголовок.
     * @param category Категория.
     */
    public SubModel(String title, String category) {
        this.title = title;
        this.category = category;
    }

    /**
     * Получить заголовок.
     *
     * @return Заголовок.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Установить заголовок.
     *
     * @param title Заголовок.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получить категорию.
     *
     * @return Категория.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Установить категорию.
     *
     * @param category Категория.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    private String dataTitle;
    private String dataDesc;


    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }
}

