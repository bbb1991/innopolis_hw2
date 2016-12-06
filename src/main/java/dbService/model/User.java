package dbService.model;

import javax.persistence.*;

/**
 * Created by bbb1991 on 11/20/16.
 * модель, представление пользователя в БД
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * ID пользователя
     */
    @Idzx
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Логин пользователя
     */
//    @Column(name = "username")
    @Column
    private String username;

    /**
     * Хэш пароля в БД
     */
//    @Column(name = "password")
    @Column
    private String passwordHash;

    /**
     * Является ли админом данный пользователь
     */
    @Column
//    @Column(name = "is_admin")
    private boolean isAdmin;

    /**
     * Заблокирован ли данный пользователь
     */
//    @Column(name="is_blocked")
    @Column
    private boolean isBlocked;

    public User() {
    }

    public User(long id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isAdmin=" + isAdmin +
                ", isBlocked=" + isBlocked +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


}
