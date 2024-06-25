package Chats;

/**
 * Класс для идентификации чатов со свойством <b>id</b>
 *
 * @author Алевтина Ильина
 * @version 1.0
 */
public class Chatlist {
    /**
     * Поле идентификатор чата
     */
    public String id;

    /**
     * Конструктор для создания объекта ChatList.
     *
     * @param id идентификатор
     */
    public Chatlist(String id) {
        this.id = id;
    }

    /**
     * Пустой конструктор для создания объекта ChatList без параметров.
     */
    public Chatlist() {
    }

    /**
     * Функция получения значения поля {@link Chatlist#id}
     *
     * @return возвращает идентификатор чата
     */
    public String getId() {
        return id;
    }

    /**
     * Процедура определения идентификатора чата {@link Chatlist#id}
     *
     * @param id - идентификатор
     */
    public void setId(String id) {
        this.id = id;
    }
}