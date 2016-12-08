package me.bbb1991.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bbb1991 on 12/8/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class AdminPageController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getPage(Model model) {
        return "admin";
    }
}