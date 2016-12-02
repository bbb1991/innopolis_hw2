package controllers;

import dbService.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

/**
 * Created by bbb1991 on 12/1/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class AddBookController {

    @RequestMapping(value = "/add_book", method = RequestMethod.GET)
    public String getAddBookPage(Model model) {
        model.addAttribute("title", "Add new book");

        return "add_book";
    }

    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public String addNewBook(@RequestParam("title") String title,
                             @RequestParam("content") String content) {
        if (Objects.isNull(title) || Objects.isNull(content)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "One of required field not filled!");
        }

        // todo add book logic here
        // todo redirect to view this book

        return "redirect:/";
    }
}
