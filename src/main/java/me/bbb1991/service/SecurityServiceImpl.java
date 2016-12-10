package me.bbb1991.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by bbb1991 on 12/9/16.
 * Реализация интерфейса {@link SecurityService }
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * Логгер уровня класса
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    /**
     * Менеджер аутентификаций
     */
    private AuthenticationManager authenticationManager;

    /**
     * Сервис по работе с пользователем
     */
    private CustomUserDetailService customUserDetailService;

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }

    /**
     * Метод для входа в систему
     * @param username имя пользователя
     * @param password пароль
     */
    @Override
    public void autoLogin(String username, String password) {

        logger.info("Trying to log in with username: {}", username);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        logger.info("BEFORE AUTHENTICATE");
        authenticationManager.authenticate(authenticationToken);
        logger.info("AFTER AUTHENTICATE");

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            logger.info(String.format("Successfully %s auto logged in", username));
        } else {
            logger.warn("Not logged in: {}" , authenticationToken.isAuthenticated());
        }
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }
}
