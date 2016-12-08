package me.bbb1991.dao;

import me.bbb1991.model.User;
import me.bbb1991.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Repository
public class UserDAOImpl implements UserDAO {

    private DBService dbService;

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }


    @Override
    public Long save(User user) {

        logger.info("Saving new user...");

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        entityManager.persist(user);
        entityManager.close();

        logger.info("User saved! Info is: {}", user);

        return user.getId();
    }

    @Override
    public void update(User user) {

        logger.info("Updating info about user. Before: {}", user);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        entityManager.merge(user);
        entityManager.close();

        logger.info("User updated! After update: {}", user);
    }

    @Override
    public void delete(Long id) {

        logger.info("Deleting user by ID: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        User user = getById(id);

        entityManager.remove(user);

        entityManager.close();

        logger.info("User deleted!");
    }

    @Override
    public User getById(Long id) {

        logger.info("Getting user bty id: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();


        User user = entityManager.find(User.class, id);

        entityManager.close();

        logger.info("Got user: {}", user);

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        logger.info("Getting all users...");

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        List<User> users = entityManager.createQuery("from User").getResultList();

        entityManager.close();

        logger.info("Get user list. Size is: {}", users.size());

        return users;
    }
}