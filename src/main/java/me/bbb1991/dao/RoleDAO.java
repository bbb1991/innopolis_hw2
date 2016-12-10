package me.bbb1991.dao;

import me.bbb1991.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bbb1991 on 12/9/16.
 * Работа с ролями пользователей
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
}