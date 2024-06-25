package Chats;
/**
 * Класс чата со свойствами <b>id</b>, <b>username</b>, <b>imageURL</b>,
 * <b>status</b>,<b>search</b>
 *
 * @author Алевтина Ильина
 * @version 1.0
 */
public class User {
    private String id;
    private String username;
    private String imageURL;

    private String status;
    private String search;

    /**
     * Пустой конструктор для создания объекта User без параметров.
     */
    public User() {

    }

    /**
     * Конструктор для создания объекта User.
     *
     * @param id   Идентификатор пользователя
     * @param username Имя пользователя
     * @param imageURL  Изображение профиля пользователя
     * @param status   Статус в сети пользователя
     * @param search   Запись для поиска пользователя в БД
     */
    public User(String id, String username, String imageURL, String status,String search) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
    }

    /**
     * Функция получения значения поля {@link User#id}
     *
     * @return возвращает идентификатор пользователя
     */
    public String getId() {
        return id;
    }

    /**
     * Процедура определения идентификатора пользователя {@link User#id}
     *
     * @param id - идентификатор
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link User#username}
     *
     * @return возвращает имя пользователя
     */
    public String getUsername() {
        return username;
    }


    /**
     * Процедура определения имени пользователя {@link User#username}
     *
     * @param username - имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Функция получения значения поля {@link User#imageURL}
     *
     * @return возвращает изображение профиля пользователя
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Процедура определения изображения профиля пользователя {@link User#imageURL}
     *
     * @param imageURL - ссылка на изображение пользователя в Firebase Storage
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Функция получения значения поля {@link User#status}
     *
     * @return возвращает статус в сети пользователя
     */
    public String getStatus() {
        return status;
    }

    /**
     * Процедура определения статуса в сети пользователя {@link User#status}
     *
     * @param status - cтатус в сети пользователя
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Функция получения значения поля {@link User#search}
     *
     * @return возвращает запись пользователя для его поиска в БД
     */
    public String getSearch() {
        return search;
    }

    /**
     * Процедура определения записи пользователя для поиска в БД {@link User#search}
     *
     * @param search - запись пользователя
     */
    public void setSearch(String search) {
        this.search = search;
    }
}
