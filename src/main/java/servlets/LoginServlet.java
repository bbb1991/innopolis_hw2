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
import java.util.Objects;

/**
 * Created by bbb1991 on 11/19/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class LoginServlet extends HttpServlet {

    private static final DBService dbservice = DBService.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.setAttribute("error", "Required field not filled!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            return;
        }

        UserDataSet userDataSet = dbservice.getUser(username);

        // user not found.
        // but for security reasons. we don't tell about not exists
        if (userDataSet == null) {
            logger.error("Login/password not matched!");
            req.setAttribute("error", "Login/password incorrect!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            return;
        }

        if (!Objects.equals(userDataSet.getPassword(), password)) {
            logger.info("Password incorrect!");
            req.setAttribute("error", "Login/password incorrect!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            return;
        }

        logger.info("Everything OK. Adding session to known users. Session id is: {}", req.getSession().getId());
        req.getSession().setAttribute("user", userDataSet);
        req.getSession().setAttribute("status", String.format("User %s successfully logged in system.", username));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect(req.getContextPath());
    }
 }
