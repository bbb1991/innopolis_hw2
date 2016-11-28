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
import java.util.Objects;

/**
 * Created by bbb1991 on 11/19/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@WebServlet({"/login", "/logout"})
public class LoginLogoutServlet extends HttpServlet {

    private final DBService dbservice;
    private static final Logger logger = LoggerFactory.getLogger(LoginLogoutServlet.class);

    public LoginLogoutServlet() throws CustomException {
        dbservice = DBService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");

        if ("logout".equalsIgnoreCase(type)) {
            req.getSession().invalidate();
            req.getSession().setAttribute("status", "User successfully logged out");
            resp.sendRedirect(req.getContextPath());
            return;
        }

        UserDataSet userDataSet = ((UserDataSet) req.getSession().getAttribute("user"));

        if (userDataSet != null) { // if user already logged in
            resp.sendRedirect(req.getContextPath());
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        logger.info("Incoming request for login. Username is: {}", username);

        if (username == null || password == null) {
            logger.error("Login/password field not filled in!");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required field not filled!");
            return;
        }

        UserDataSet userDataSet = null;
        try {
            userDataSet = dbservice.getUser(username);
        } catch (CustomException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            return;
        }

        // user not found.
        // but for security reasons. we don't tell about not exists
        if (userDataSet == null) {
            logger.error("Login/password not matched!");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login/password incorrect!");
            return;
        }

        if (!Objects.equals(userDataSet.getPassword(), password)) {
            logger.info("Password incorrect!");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login/password incorrect!");
            return;
        }

        logger.info("Everything OK. Adding session to known users. Session id is: {}", req.getSession().getId());
        req.getSession().setAttribute("user", userDataSet);
        req.getSession().setAttribute("status", String.format("User %s successfully logged in system.", username));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect(req.getContextPath());
    }
 }
