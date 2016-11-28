package servlets;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;
import dbService.dataSets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/23/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@WebServlet({"/find_book"})
public class ViewBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");
//        if (userDataSet == null) {
//            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You don't have permission. Login in first");
//            return;
//        }
        req.getRequestDispatcher("/find_book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String id = req.getParameter("id");
        String author = req.getParameter("author");

        if (title == null && author == null && id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required fields not filled");
            return;
        }

        if (id != null) {
            BookDataSet bookDataSet = null;
            try {
                bookDataSet = DBService.getInstance().findBookById(id);
            } catch (CustomException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
                return;
            }
            req.setAttribute("book", bookDataSet);
            req.getRequestDispatcher("/view_result.jsp").forward(req, resp);
            return;
        }

        // todo find by name or author
    }
}
