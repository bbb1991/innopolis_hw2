package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by bbb1991 on 11/28/16.
 * Абстрактный класс, предназначен чтобы стандартизировать название и кол-во методов
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public abstract class AbstractDAO<T> {

    /**
     * БД сервис
     */
    protected DBService dbService;

    public AbstractDAO(DBService dbService) {
        this.dbService = dbService;
    }

    /**
     * Вставка модели
     * @param model модель, которую нужно вставить в БД
     * @throws CustomException ошибка при работе с БД
     */
    public abstract void insert(T model) throws CustomException;

    /**
     * Обновление существующей модели в БД
     * @param model модель, которую необходимо обновить
     * @throws CustomException ошибка при работе с БД
     */
    public abstract void update(T model) throws CustomException;

    /**
     * Удаление модели из БД
     * @param model модель, которую необходимо удалить
     * @throws CustomException ошибка при работе с БД
     */
    public abstract void delete(T model) throws CustomException;

    /**
     * Получение модели из БД по ID
     * @param id ID по которому необходимо вытащить модель
     * @return модель
     * @throws CustomException ошибка при работе с БД
     */
    public abstract T getById(long id) throws CustomException;

    /**
     * Получение модели по имени (название)
     * @param name имя (название, тема)
     * @return модель
     * @throws CustomException ошибка при работе с БД
     */
    public abstract T getByName(String name) throws CustomException;

    /**
     * Создание таблицы в БД
     * @throws CustomException ошибка при работе с БД
     */
    public abstract void createTable() throws CustomException;

    /**
     * Удаление таблицы в БД
     * @throws CustomException ошибка при работе с БД
     */
    public abstract void dropTable() throws CustomException;

    /**
     * Получение всей модели из БД
     * @return список или множество модели
     * @throws CustomException ошибка при работе с БД
     */
    public abstract Collection<T> getAll() throws CustomException;

}
