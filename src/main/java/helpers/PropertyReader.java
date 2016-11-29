package helpers;

import dbService.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;

/**
 * Created by bbb1991 on 11/20/16.
 * Класс для чтения *.property файла
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class PropertyReader {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    private PropertyReader() {}

    /**
     * метод, который считывет файл натсроек
     *
     * @param file имя файла, которую необходимо считать
     * @return класс Properties содержащий настройки из файла
     * @throws CustomException IO Exception пробрасывается наверх, чтобы компоненты были в курсе что что-то случилось
     */
    public static Properties readProperty(final String file) throws CustomException {

        // Пустой инстанс настроек
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = loader.getResourceAsStream(file)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException("IO Exception occurred!", e);
        }

        logger.info("Settings read success. Settings is: ");
        for (Map.Entry property : properties.entrySet())
            logger.info("\t" + property.getKey() + ": " + property.getValue());
        return properties;
    }
}
