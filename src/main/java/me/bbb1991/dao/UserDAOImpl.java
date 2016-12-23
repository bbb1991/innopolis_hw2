package me.bbb1991.dao;

import me.bbb1991.model.User;
import me.bbb1991.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * Реализация интерфейса {@link UserDAO }
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Repository
public class UserDAOImpl implements UserDAO {

    /**
     * Сервис для работы с БД
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Сохранение пользователя в БД
     *
     * @param user пользователь, которую необходимо сохранить
     * @return ID, по которому сохранен пользователь
     */
    @Override
    public Long save(User user) {

        logger.info("Saving new user...");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(user);

        transaction.commit();

        entityManager.close();

        logger.info("User saved! Info is: {}", user);

        return user.getId();
    }

    /**
     * Обновление пользователя в БД
     *
     * @param user пользователь, которую необходимо сохранить
     */
    @Override
    public void update(User user) {

        logger.info("Updating info about user. Before: {}", user);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.merge(user);
        entityManager.close();

        logger.info("User updated! After update: {}", user);
    }

    /**
     * Удаление пользовталея по ID
     *
     * @param id ID пользователя, которую необходимо удалить
     */
    @Override
    public void delete(Long id) {

        logger.info("Deleting user by ID: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = getById(id);

        entityManager.remove(user);

        entityManager.close();

        logger.info("User deleted!");
    }

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя, которую необходимо получить
     * @return пользователь, найденный по ID, или <code>null</code>
     */
    @Override
    public User getById(Long id) {

        logger.info("Getting user bty id: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();


        User user = entityManager.find(User.class, id);

        entityManager.close();

        logger.info("Got user: {}", user);

        return user;
    }

    /**
     * Получение всех пользователей в системе
     *
     * @return список пользователей
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {

        logger.info("Getting all users...");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<User> users = entityManager.createQuery("from User").getResultList();

        entityManager.close();

        logger.info("Get user list. Size is: {}", users.size());

        return users;
    }

    /**
     * Получение пользователя по имени пользователяа
     *
     * @param username имя пользователей, по которому ведется поиск
     * @return пользователь, найденный по имени пользователя
     */
    @Override
    public User findByUsername(String username) {
        logger.info("Getting user by username: {}", username);

        EntityManager entityManager = entityManagerFactory.createEntityManager();


        Query query = entityManager.createQuery("select u from User u where username=:username");
        query.setParameter("username", username).setMaxResults(1);

        User user = (User) query.getResultList().get(0); // TODO: 12/10/16 fix this mess

        entityManager.close();

        logger.info("Got user: {}", user);

        return user;
    }

    /**
     * Блокирование пользователя по ID
     *
     * @param id ID пользователя, которого необходимо блокировать
     */
    @Override
    public void blockUser(Long id) {
        blockUnblockUser(id, true);
    }

    /**
     * Разблокирование пользователя по ID
     *
     * @param id ID пользователя
     */
    @Override
    public void unblockUser(Long id) {
        blockUnblockUser(id, false);
    }

    private void blockUnblockUser(Long id, boolean status) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        manager.createQuery("update User u set u.blocked=:status where u.id=:id")
                .setParameter("id", id)
                .setParameter("status", status)
                .executeUpdate();

        manager.getTransaction().commit();

        manager.close();
    }
}