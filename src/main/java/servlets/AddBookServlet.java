package servlets;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/22/16.
 * Сервлет для добавлени новой книги
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
//@WebServlet("/add_book")
public class AddBookServlet extends HttpServlet {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(AddBookServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("add_book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null || content == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required fields is empty");
            return;
        }

        try {
            String id = DBService.getInstance().insertBook(userDataSet.getId(), title, content, userDataSet.getUsername());
            logger.info("Added book. id is: {}", id);

            req.getSession().setAttribute("status", String.format("Your book successfully added. Id is: %s", id));

            resp.sendRedirect(req.getContextPath());
        } catch (CustomException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.toString());
        }

    }
}
