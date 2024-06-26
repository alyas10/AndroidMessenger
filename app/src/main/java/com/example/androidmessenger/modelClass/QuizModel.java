package com.example.androidmessenger.modelClass;
/**
 * Модель для тестов.
 * @author Иван Минаев
 * @version 1.0
 */
public class QuizModel {
    /** Поля вопросов, ответов и корректных ответов */
    String question, op1, op2, op3, op4, correctsAns;

    /**
     * Конструктор без параметров.
     */
    public QuizModel(){

    }
    /**
     * Конструктор с параметрами.
     *
     * @param question Вопрос.
     * @param op1 Вариант ответа 1.
     * @param op2 Вариант ответа 2.
     * @param op3 Вариант ответа 3.
     * @param op4 Вариант ответа 4.
     * @param correctsAns Правильный ответ.
     */
    public QuizModel(String question, String op1, String op2, String op3, String op4, String correctsAns) {
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.correctsAns = correctsAns;
    }
    /**
     * Получить вопрос.
     *
     * @return Вопрос.
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Установить вопрос.
     *
     * @param question Вопрос.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * Получить вариант ответа 1.
     *
     * @return Вариант ответа 1.
     */
    public String getOp1() {
        return op1;
    }
    /**
     * Установить вариант ответа 1.
     *
     * @param op1 Вариант ответа 1.
     */
    public void setOp1(String op1) {
        this.op1 = op1;
    }
    /**
     * Получить вариант ответа 2.
     *
     * @return Вариант ответа 2.
     */
    public String getOp2() {
        return op2;
    }
    /**
     * Установить вариант ответа 2.
     *
     * @param op2 Вариант ответа 2.
     */
    public void setOp2(String op2) {
        this.op2 = op2;
    }
    /**
     * Получить вариант ответа 3.
     *
     * @return Вариант ответа 3.
     */
    public String getOp3() {
        return op3;
    }
    /**
     * Установить вариант ответа 3.
     *
     * @param op3 Вариант ответа 3.
     */
    public void setOp3(String op3) {
        this.op3 = op3;
    }
    /**
     * Получить вариант ответа 4.
     *
     * @return Вариант ответа 4.
     */
    public String getOp4() {
        return op4;
    }
    /**
     * Установить вариант ответа 4.
     *
     * @param op4 Вариант ответа 4.
     */
    public void setOp4(String op4) {
        this.op4 = op4;
    }
    /**
     * Получить правильный ответ.
     *
     * @return Правильный ответ.
     */
    public String getCorrectsAns() {
        return correctsAns;
    }
    /**
     * Установить правильный ответ.
     *
     * @param correctsAns Правильный ответ.
     */
    public void setCorrectsAns(String correctsAns) {
        this.correctsAns = correctsAns;
    }
}
