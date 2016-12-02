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
 * Сервлет, которая обслуживает путь <code>/find_book</code>. Данные сервлет отвечает за предоставление страницу формы
 * поиска и обработку запроса с поиском книги
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
//@WebServlet({"/find_book"})
public class ViewBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/find_book.jsp").forward(req, resp); // перенаправляем на страницу с формой поиска
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Берем параметры запроса
        String title = req.getParameter("title");
        String id = req.getParameter("id");
        String author = req.getParameter("author");

        if (title == null && author == null && id == null) { // проверяем на то, что хоть один из них был заполнен
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required fields not filled");
            return;
        }

        if (id != null) { // ищем по ID
            BookDataSet bookDataSet;
            try {
                bookDataSet = DBService.getInstance().findBookById(id);
            } catch (CustomException e) { // если случился ошибка с работой в БД
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
