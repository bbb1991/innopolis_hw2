package me.bbb1991.controller;

import me.bbb1991.helpers.CustomException;
import me.bbb1991.helpers.Helper;
import me.bbb1991.model.Book;
import me.bbb1991.model.Comment;
import me.bbb1991.model.User;
import me.bbb1991.service.BookService;
import me.bbb1991.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * Класс контроллер для просмотра конкретной книги
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ViewBookPageController {

    /**
     * Сервис для работы с БД
     */
    private BookService bookService;

    private CommentService commentService;

    private Helper helper;

    private static final Logger logger = LoggerFactory.getLogger(ViewBookPageController.class);

    /**
     * Получение книги по ID
     *
     * @param id    ID книги, которую необходимо получить
     * @param model
     * @return книга, найденная по ID, либо 404
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public String getBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);

        if (book == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("No book found with ID: %d", id));
        }

        List<Comment> comments = commentService.getCommentsByBookId(book.getId());

        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());

        return "book";
    }

    @RequestMapping(value = "/book/{id}/add_comment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("comment") Comment comment, @PathVariable("id") Long id) {

        logger.info("Adding new comment: {}", comment);

        User user = helper.getCurrentUser();

        logger.info("User is: {}", user);

        Book book = bookService.getBookById(id);

        logger.info("Book is: {}" , book);


        comment.setUser(user);
        comment.setBook(book);
        commentService.saveOrUpdateComment(comment);

        logger.info("Comment added.");

        return "redirect:/book/" + comment.getBook().getId();
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setHelper(Helper helper) {
        this.helper = helper;
    }
}
