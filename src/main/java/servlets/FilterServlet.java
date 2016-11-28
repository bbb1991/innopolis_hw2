package servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by bbb1991 on 11/27/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@WebFilter({"/add_book", "/find_book"})
public class FilterServlet implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        System.err.println("doing filter");

        Object u = session.getAttribute("user");
        if (u != null) {
            chain.doFilter(request, response);
        } else { // if user doesn't logged in.
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("login"));
        }
    }

    @Override
    public void destroy() {}
}
