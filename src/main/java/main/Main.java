package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.HelloServlet;

/**
 * Created by bbb1991 on 11/18/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Main {
    public static void main(String[] args) throws Exception {
        HelloServlet helloServlet = new HelloServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(helloServlet), "/hello");

        Server server = new Server(8080);

        server.setHandler(context);

        server.start();
        server.join();
    }
}
