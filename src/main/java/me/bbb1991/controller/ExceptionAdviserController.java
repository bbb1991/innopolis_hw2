package me.bbb1991.controller;

import me.bbb1991.helpers.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by bbb1991 on 12/16/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@ControllerAdvice
public class ExceptionAdviserController {

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, Model model) {

        model.addAttribute("title", ex.getHttpStatus().value());
        model.addAttribute("errorTitle", ex.getHttpStatus().getReasonPhrase());
        model.addAttribute("errorMessage", ex);

        return "errorPage";
    }
}
