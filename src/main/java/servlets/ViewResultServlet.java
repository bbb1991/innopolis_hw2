package servlets;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;

/**
 * Created by bbb1991 on 11/28/16.
 * Сервлет для обслуживания пути /view result. Предоставляет книгу по ID
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@WebServlet("/view_result")
public class ViewResultServlet extends HttpServlet {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(ViewResultServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id == null) { // если ID не передан в параметре
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty");
            return;
        }

        try {
            BookDataSet bookDataSet = DBService.getInstance().findBookById(id); // берем книгу по ID
            req.setAttribute("book", bookDataSet);
            req.getRequestDispatcher("/view_result.jsp").forward(req, resp); // и перенаправляем в JSP страницу
        } catch (CustomException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
