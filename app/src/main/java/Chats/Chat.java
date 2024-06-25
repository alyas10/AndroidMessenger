package Chats;

/**
 * Класс чата со свойствами <b>sender</b>, <b>receiver</b>, <b>message</b>,<b>isseen</b>
 *
 * @author Алевтина Ильина
 * @version 1.0
 */
public class Chat {
    /** Поле отправитель сообщения */
    private String sender;
    /** Поле получатель сообщения */
    private String receiver;
    /** Поле текст сообщения */
    private String message;
    /** Поле флаг, указывающий, прочитано ли сообщение */
    private  boolean isseen;

    /**
     * Конструктор для создания объекта Chat.
     *
     * @param sender   Отправитель сообщения
     * @param receiver Получатель сообщения
     * @param message  Текст сообщения
     * @param isseen   Флаг, указывающий, прочитано ли сообщение
     */
    public Chat(String sender, String receiver, String message, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
    }

    /**
     * Пустой конструктор для создания объекта Chat без параметров.
     */
    public Chat() {
    }

    /**
     * Функция получения значения поля {@link Chat#sender}
     *
     * @return возвращает отправителя сообщения
     */
    public String getSender() {
        return sender;
    }

    /**
     * Процедура определения отправителя сообщения {@link Chat#sender}
     *
     * @param sender - получатель сообщения
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Функция получения значения поля {@link Chat#receiver}
     *
     * @return возвращает получателя сообщения
     */
    public String getReceiver() { // corrected getter method name
        return receiver;
    }

    /**
     * Процедура определения получателя сообщения {@link Chat#receiver}
     *
     * @param receiver - получатель сообщения
     */
    public void setReceiver(String receiver) { // corrected setter method name
        this.receiver = receiver;
    }

    /**
     * Функция получения значения поля {@link Chat#message}
     *
     * @return возвращает Текст сообщения
     */
    public String getMessage() {
        return message;
    }

    /**
     * Процедура определения тексто сообщения {@link Chat#message}
     *
     * @param message - текст сообщения
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Проверить, прочитано ли сообщение.
     *
     * @return true, если сообщение прочитано, иначе false
     */
    public boolean isIsseen() {
        return isseen;
    }

    /**
     * Процедура определения - прочитано ли сообщение {@link Chat#isseen}
     *
     * @param isseen - параметр, который показывает, прочитано ли сообщение
     */
    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
