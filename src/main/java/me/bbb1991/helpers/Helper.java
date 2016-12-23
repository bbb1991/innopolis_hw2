package me.bbb1991.helpers;

import me.bbb1991.model.User;
import me.bbb1991.service.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by bbb1991 on 12/24/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Component
public class Helper {

    private  BCryptPasswordEncoder passwordEncoder;

    public  User getCurrentUser() {
        // получаем параметры безорасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // берем информацию о залогиненном пользователе
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        return user.getUser();
    }

    public  String getPasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
