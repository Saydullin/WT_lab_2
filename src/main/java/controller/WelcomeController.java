package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/welcome")
public class WelcomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String REGISTRATION_PAGE = "index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE);
        if (dispatcher != null){
            dispatcher.forward(request, response);
        } else{
            throw new ServletException();
        }

    }
}
