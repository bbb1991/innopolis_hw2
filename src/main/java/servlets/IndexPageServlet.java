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
import java.util.List;

/**
 * Created by bbb1991 on 11/20/16.
 * Сервлет для главной страницы со списком книг
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@WebServlet("/")
public class IndexPageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(IndexPageServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<BookDataSet> books = DBService.getInstance().getAllBooks();
            req.setAttribute("books", books);
        } catch (CustomException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            logger.error("ERROR while getting a list of books", e);
            return;
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
