package me.bbb1991.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by bbb1991 on 12/9/16.
 * Сущность ролей пользователя в системе
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Entity
@Table(name = "roles")
public class Role {

    /**
     * ID, по которому будет хранится роль
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название роля пользователя
     */
    @Column
    private String name;

    /**
     * Связка с пользователями
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> users;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
