package servlets;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.UserDataSet;
import helpers.PasswordHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/21/16.
 * сервлет который отвечает за регистрацию пользователя
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@WebServlet("/register")
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "The required field is empty!");
            return;
        }

        if (!password1.equals(password2)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "The passwords isn't match!");
            return;
        }

        String password;
        try {
            password = PasswordHelper.getHash(password1);
        } catch (CustomException e) { // если при работе с генерацией хэша произошла ошибка
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }

        UserDataSet userDataSet;
        try {
            userDataSet = DBService.getInstance().insertUser(username, password);
        } catch (CustomException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            return;
        }
        req.getSession().setAttribute("user", userDataSet);
        req.getSession().setAttribute("status", String.format("User %s successfully registered.", username));
        resp.sendRedirect(req.getContextPath());
    }
}
