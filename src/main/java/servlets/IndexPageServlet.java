package servlets;

import accounts.UserProfile;
import helpers.AccountServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class IndexPageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(IndexPageServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession().getId();
        logger.info("Incoming GET request. Session ID is: {}", sessionId);

        UserProfile profile = AccountServiceHelper.getInstance().getUserBySessionId(sessionId);

        if (profile != null) {
            String username = profile.getUsername();
            req.setAttribute("username", username);
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
