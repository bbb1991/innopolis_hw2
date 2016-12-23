package me.bbb1991.helpers;

/**
 * Created by bbb1991 on 11/20/16.
 * Класс с константами
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public final class Constants {

    /**
     * Общая ошибка что что-то случилось
     */
    public static final String ERROR_MESSAGE_GENERAL = "Something terrible happened!";

    /**
     * Ошибка при работе с запросом в БД
     */
    public static final String ERROR_MESSAGE_STATEMENT = "Error occurred while working with SQL statement!";

    /**
     * Имя файла настроек
     */
    public static final String SETTINGS_FILE = "application.properties";

    public static final int MIN_PASSWORD_LENGTH = 1;

    public static final int MAX_PASSWORD_LENGTH = 32;

    public static final int MIN_USERNAME_LENGTH = 1;

    public static final int MAX_USERNAME_LENGTH = 32;

    private Constants() {}
}
