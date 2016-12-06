package controllers;

import dbService.CustomException;
import dbService.DBService;
import dbService.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by bbb1991 on 12/2/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class SearchBookController {

    private DBService dbService;

    private static final String SEARCH_BY_TITLE = "search_by_title";
    private static final String SEARCH_BY_AUTHOR = "search_by_author";
    private static final String SEARCH_BY_ID = "search_by_id";

    private static final Logger logger = LoggerFactory.getLogger(SearchBookController.class);

    @RequestMapping(value = "find_book", method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("title", "Search book");

        return "find_book";
    }

    @RequestMapping(value = "find_book", method = RequestMethod.POST)
    public String findBook(Model model, HttpServletRequest request) {
        String requestType = request.getParameter("findBy");
        if (Objects.isNull(requestType)) {
            logger.warn("Request type not filled!");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Request type not filled!");
        }

        List<Book> books = new ArrayList<>();

        try {

            switch (requestType) {
                case SEARCH_BY_TITLE:
                    String query = request.getParameter("title");
                    books.addAll(dbService.getBookByTitle(query));
                    break;
                case SEARCH_BY_AUTHOR: // TODO: 12/2/16 add logic
                    break;
                case SEARCH_BY_ID: // TODO: 12/2/16 add logic
                    break;
                default:
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Search type not recognized!");
            }
        } catch (CustomException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        model.addAttribute("title", "Find result");
        model.addAttribute("books", books);
        model.addAttribute("username", request.getParameter("username"));

        model.addAttribute("status", String.format("Found books: %d", books.size()));

        return "index";

    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}
