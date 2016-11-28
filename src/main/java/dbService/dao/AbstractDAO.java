package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by bbb1991 on 11/28/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public abstract class AbstractDAO<T> {
    protected Logger logger = LoggerFactory.getLogger(AbstractDAO.class);
    protected DBService dbService;

    public AbstractDAO(DBService dbService) {
        this.dbService = dbService;
    }

    public abstract void insert(T t) throws CustomException;

    public abstract void update(T t) throws CustomException;

    public abstract void delete(T t) throws CustomException;

    public abstract T getById(long id);

    public abstract T getByName(String name) throws CustomException;

    public abstract void createTable() throws CustomException;

    public abstract void dropTable() throws CustomException;

    public abstract <X extends Collection<T>> X getAll() throws CustomException;

}
