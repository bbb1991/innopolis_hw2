package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static helpers.Constants.ERROR_MESSAGE;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class PropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    private PropertyReader() {}

    public static Properties readProperty(String file) {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(file)) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("File {} not found!", file, e);
        } catch (IOException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        for (Map.Entry property : properties.entrySet())
            logger.info(property.getKey() + " : " + property.getValue());
        return properties;
    }
}
