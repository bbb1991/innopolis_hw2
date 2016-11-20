package servlets;

import accounts.UserProfile;
import dbService.DBService;
import helpers.AccountServiceHelper;
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
        if (isUserAuthorized(req, resp)) {
            resp.sendRedirect("/");
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

//        dbservice.

        logger.info("Incoming request for login. Username is: {}", username);

        if (username == null || password == null) {
            logger.error("Login/password field not filled in!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = AccountServiceHelper.getInstance().getUserByLogin(username);

        if (profile == null) {
            logger.error("Login/password not matched!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!Objects.equals(profile.getPassword(), password)) {
            logger.info("Password incorrect!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        logger.info("Everything OK. Adding session to known users. Session id is: {}", req.getSession().getId());
        AccountServiceHelper.getInstance().addSession(req.getSession().getId(), profile);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.sendRedirect("/");
    }

    private static boolean isUserAuthorized(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();

        UserProfile profile = AccountServiceHelper.getInstance().getUserBySessionId(sessionId);

        return profile != null;
    }
 }
