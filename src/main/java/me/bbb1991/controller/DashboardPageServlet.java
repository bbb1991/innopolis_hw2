package me.bbb1991.controller;

import me.bbb1991.model.Book;
import me.bbb1991.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by bbb1991 on 12/8/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class DashboardPageServlet {

    private DBService dbService;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String getPage(Model model) {

        List<Book> books = dbService.getAllBooks();

        model.addAttribute("title", "Main page");
        model.addAttribute("books", books);

        return "dashboard";
    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}
