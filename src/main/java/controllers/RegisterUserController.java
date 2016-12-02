package controllers;

import dbService.CustomException;
import dbService.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Objects;

/**
 * Created by bbb1991 on 12/2/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class RegisterUserController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("title", "Register page");
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username,
                           @RequestParam("password1") String password1,
                           @RequestParam("password2") String password2) {

        logger.info("Registering new user. Username is: {}", username);

        if (!Objects.equals(password1, password2)) {
            logger.warn("Passwords not matched!");
            throw new HttpClientErrorException(HttpStatus.I_AM_A_TEAPOT, "tea time! Nah, just kidding. Passwords not match, sorry.");
        }

        try {
            DBService.getInstance().insertUser(username, password1);
        } catch (CustomException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while generating password hash!");
        }

        return "redirect:/";
    }
}
