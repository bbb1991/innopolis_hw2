package me.bbb1991.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by bbb1991 on 12/8/16.
 * Модель пользователя
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 * @version 1.0
 */

@Entity
@Table(name = "users")
public class User {

    /**
     * ID по которому хранится пользователь в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Логин пользователя
     */
    @Column
    private String username;

    /**
     * Хэш пароля пользователя
     */
    @Column
    private String password;

    /**
     * Временное поле для хранение повтора пароля
     */
    @Transient
    private String confirmPassword;

    /**
     * Поле которая показывает, заблокирован ли пользователь
     */
    @Column
    private boolean blocked;

    @Column
    private String avatar;

    /**
     * Поле, по которому маппится роли и пользователи
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}