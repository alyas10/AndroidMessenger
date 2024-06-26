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
    private int dataDesc;
    /** Поле язык */
    private String dataLang;
    /** Поле изображение */
    private int dataImage;

    /**
     * Функция получения значения поля {@link DataClass#dataTitle}
     *
     * @return возвращает заголовок
     */
    public String getDataTitle() {
        return dataTitle;
    }
    /**
     * Функция получения значения поля {@link DataClass#dataDesc}
     *
     * @return возвращает отображение
     */
    public int getDataDesc() {
        return dataDesc;
    }
    /**
     * Функция получения значения поля {@link DataClass#dataLang}
     *
     * @return возвращает язык.
     */
    public String getDataLang() {
        return dataLang;
    }
    /**
     * Функция получения значения поля {@link DataClass#dataImage}
     *
     * @return возвращает изображение
     */
    public int getDataImage() {
        return dataImage;
    }

    /**
     * Конструктор для создания объекта DataClass.
     *
     * @param dataTitle заголовок
     * @param dataDesc отображение
     * @param dataLang язык
     * @param dataImage изображение
     */
    public DataClass(String dataTitle, int dataDesc, String dataLang, int dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }
}