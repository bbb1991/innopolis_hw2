package controllers;

import dbService.CustomException;
import dbService.DBService;
import dbService.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by bbb1991 on 12/2/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class IndexPageController {

    private DBService dbService;

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getPage(Model model) throws CustomException {

        if (!model.containsAttribute("books")) {
            model.addAttribute("title", "Index page");
            List<Book> books = dbService.getAllBooks();
            model.addAttribute("books", books);
        }

        return "index";
    }
}
