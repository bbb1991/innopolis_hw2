package helpers;

import dbService.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;

/**
 * Created by bbb1991 on 11/19/16.
 * Класс для работы с паролями
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class PasswordHelper {

    /**
     * Логгер уровня класса
     */
    private static final Logger logger = LoggerFactory.getLogger(PasswordHelper.class);

    /**
     * Метод, который возвращает хэш от пароля
     * @param passwordToHash пароль, которую нужно захешировать
     * @return хэш от пароля
     * @throws CustomException ошибка при попытке создания инстанса
     */
    public static String getHash(String passwordToHash) throws CustomException {
        String generatedPassword = null;
        try {
            byte[] salt = getSalt();
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) { // this block won't execute never ever. Maybe.
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException("Error occurred while generating password hash", e);
        }
        return generatedPassword;
    }

    /**
     * Метод для получение соли для хэширования
     * @return соль для хэша
     */
    private static byte[] getSalt() {
//        //Always use a SecureRandom generator
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        //Create array for salt
//        byte[] salt = new byte[16];
//        //Get a random salt
//        sr.nextBytes(salt);
//        //return salt
//        return salt;

        return "belial".getBytes();
    }
}
