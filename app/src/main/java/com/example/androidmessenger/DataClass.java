package com.example.androidmessenger;

/**
 * Класс данных для вкладки: "Теория" со свойствами <b>dataTitle</b>, <b>dataDesc</b>, <b>dataLang</b>,<b>dataImage</b>
 *
 * @author Иван Минаев
 * @version 1.0
 */
public class DataClass {
    /** Поле заголовок */
    private String dataTitle;
    /** Поле отображение */
    private int starCount; // Добавлено новое свойство для количества звездочек
    private int dataImage;

    /**
     * Функция получения значения поля {@link DataClass#dataTitle}
     *
     * @return возвращает заголовок
     */
    public String getDataTitle() {
        return dataTitle;
    }

    public int getStarCount() { // Добавлен новый метод для получения количества звездочек
        return starCount;
    }
    public int getDataImage() {
        return dataImage;
    }

    /**
     * Конструктор для создания объекта DataClass.
     *
     * @param dataTitle заголовок
     * @param dataImage изображение
     */
    public DataClass(String dataTitle, int dataImage, int starCount) {
        this.dataTitle = dataTitle;
        this.dataImage = dataImage;
        this.starCount = starCount;
    }
}