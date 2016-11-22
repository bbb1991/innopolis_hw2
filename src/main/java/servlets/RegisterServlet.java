package servlets;

import dbService.DBService;
import dbService.dataSets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");
        if (userDataSet != null) { // already logged in
            resp.sendRedirect(req.getContextPath());
            return;
        }
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");

        if (username == null || password1 == null || password2 == null) {
            req.setAttribute("error", "The required field is empty!");
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            return;
        }

        if (!password1.equals(password2)) {
            req.setAttribute("error", "The passwords not match!");
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            return;
        }

        UserDataSet userDataSet = DBService.getInstance().insertUser(username, password1);
        req.getSession().setAttribute("user", userDataSet);
        req.getSession().setAttribute("status", String.format("User %s successfully registered.", username));
        resp.sendRedirect(req.getContextPath());
    }
}
