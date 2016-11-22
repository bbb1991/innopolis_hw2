package servlets;

import dbService.DBService;
import dbService.dataSets.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/22/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class AddBookServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AddBookServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");
        if (userDataSet == null) {
//            req.setAttribute("error", "You don't have permission to view this page. Please, login first.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You don't have permission to view this page.");
            return;
        }
        req.getRequestDispatcher("add_book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");

        if (userDataSet == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "You don't have permission.");
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null || content == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String id  = DBService.getInstance().insertBook(userDataSet.getId(), title, content, userDataSet.getUsername());
        logger.info("Added book. id is: {}", id);

        req.getSession().setAttribute("status", String.format("Your book successfully added. Id is: %s", id));

        resp.sendRedirect(req.getContextPath());

    }
}
