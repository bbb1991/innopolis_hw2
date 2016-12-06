package dbService.dao;

import dbService.CustomException;
import dbService.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class UsersDAO extends AbstractDAO<User> {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(UsersDAO.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insert(User model) throws CustomException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(model);

        logger.info("Inserted user: {}", model);
    }

    @Override
    public void update(User model) throws CustomException {

        Session session = sessionFactory.getCurrentSession();
        session.persist(model);

        logger.info("Updated user: {}", model);
    }

    @Override
    public void delete(User model) throws CustomException {

        Session session = sessionFactory.getCurrentSession();

        session.delete(model);

        logger.info("deleted user: {}", model);
    }

    @Override
    public User getById(long id) throws CustomException {
        Session session = sessionFactory.getCurrentSession();

        User user = session.load(User.class, id);

        logger.info("Get user by id: {}", user);

        return user;

    }

    @Override
    public User getByName(String name) throws CustomException {
        return null; //todo add logic herer

    }

    @Override
    public void createTable() throws CustomException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropTable() throws CustomException  {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<User> getAll() throws CustomException {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("from User").getResultList();

        logger.info("Got users: {}", users.size());
        return users;
    }


}
