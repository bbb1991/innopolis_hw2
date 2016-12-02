package controllers;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Created by bbb1991 on 12/3/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class ViewBookController {

    @RequestMapping("/view_result/{id}")
    public String getBook(Model model, @PathVariable("id") int id) {

        BookDataSet book;
        try {
            book = DBService.getInstance().findBookById(id);
        } catch (CustomException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting book");
        }

        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        return "view_result";
    }
}
