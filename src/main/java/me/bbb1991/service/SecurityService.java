package me.bbb1991.service;

import org.springframework.stereotype.Service;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
