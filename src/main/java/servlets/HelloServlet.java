package servlets;

import accounts.UserProfile;
import helpers.AccountServiceHelper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/18/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
//@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile profile = AccountServiceHelper.getInstance().getUserBySessionId(req.getSession().getId());

        if (profile == null) {
            req.getRequestDispatcher("/").forward(req, resp);
        } else {
//            req.setAttribute("username");
        }
    }

//    Servlet
}
