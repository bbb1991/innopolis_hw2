package me.bbb1991.service;

import me.bbb1991.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Created by bbb1991 on 12/10/16.
 * Класс обертка для текущего пользователя, реализация интерфейса {@link UserDetails}
 * Создан для того, чтобы при добавлений новой книги в поле автор поодставлять всю информацию о текущем авторе,
 * беря его на основе userService, хранящегося в Spring Security. Так как Spring Security в своей реализаций хранит
 * только поля username, password, authorities.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class CustomUserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * Пользователь, который зашел в систему
     */
    private User user;

    /**
     * Роль пользователя в системе.
     *
     * @see me.bbb1991.model.Role
     */
    private Set<GrantedAuthority> authorities = null;

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь, зашедший в систему
     */
    public User getUser() {
        return user;
    }

    /**
     * Вставка пользователя в обертку
     *
     * @param user пользовтель, с которым работаем в текущем моменте
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Получение списка прав пользователя
     *
     * @return список ролей пользователя
     * @see me.bbb1991.model.Role
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Вставка ролей пользователя в системе
     *
     * @param authorities список ролей в системе
     * @see me.bbb1991.model.Role
     */
    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Получение хэша пароля
     *
     * @return хэш пароля пользователя
     */
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Получение логина пользователя
     *
     * @return логин пользователя
     */
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Истек ли срок аккаунта (заблокирован ли по причине долгое время без использования
     *
     * @return <true>false</true> - если аккаунт активный <code>true</code> если аккаунт заблокирован
     */
    public boolean isAccountNonExpired() {
        return !user.isBlocked();
    }

    /**
     * Заблокирован ли аккаунт
     *
     * @return <true>false</true> - если аккаунт активный <code>true</code> если аккаунт заблокирован
     */
    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    /**
     * Истек ли срок аккаунта
     *
     * @return <true>false</true> - если аккаунт активный <code>true</code> если аккаунт заблокирован
     */
    public boolean isCredentialsNonExpired() {
        return !user.isBlocked();
    }

    /**
     * Активен ли аккаунт
     *
     * @return <true>true</true> - если аккаунт активный <code>false</code> если аккаунт заблокирован
     */
    public boolean isEnabled() {
        return !user.isBlocked();
    }

}