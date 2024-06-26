package com.example.androidmessenger;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
/**
 * Класс для результатов теста.
 *
 * Хранит количество правильных и неправильных ответов.
 * @author Иван Минаев
 * @version 1.0
 */
public class TestResult {
    /** Поле с числом правильных ответов */
    private int correct;
    /** Поле с числом неправильных ответов */
    private int incorrect;


    /**
     * Пустой конструктор, необходимый для Firebase.
     *
     * Позволяет Firebase создавать экземпляры класса при чтении данных из базы данных.
     */
    public TestResult() {
    }

    /**
     * Конструктор для создания объекта TestResult с заданными значениями.
     *
     * @param correct   Количество правильных ответов.
     * @param incorrect Количество неправильных ответов.
     */
    public TestResult(int correct, int incorrect) {
        this.correct = correct;
        this.incorrect = incorrect;
    }

    /**
     * Возвращает количество правильных ответов.
     *
     * @return Количество правильных ответов.
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * Устанавливает количество правильных ответов.
     *
     * @param correct количество правильных ответов.
     */
    public void setCorrect(int correct) {
        this.correct = correct;
    }

    /**
     * Возвращает количество неправильных ответов.
     *
     * @return Количество неправильных ответов.
     */
    public int getIncorrect() {
        return incorrect;
    }

    /**
     * Устанавливает количество неправильных ответов.
     *
     * @param incorrect количество неправильных ответов.
     */
    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }
}