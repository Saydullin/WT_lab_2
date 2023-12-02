package controller;

import data_access.DBConnection;
import data_access.DBRequests;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserIdentifier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/statistics")
public class StatisticsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String STATISTICS_PAGE = "/WEB-INF/view/jsp/statistics.jsp";
    public static final String SIGNIN_PAGE = "/WEB-INF/view/jsp/signin.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String role = UserIdentifier.getRole(cookies);
        int user_id = UserIdentifier.getUsercode(cookies);
        RequestDispatcher dispatcher;
        if (user_id == 0 || UserIdentifier.getRole(cookies).equals("") || UserIdentifier.getRole(cookies).equals("admin")){ //Cookies expired
            dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
            dispatcher.forward(request, response);
        }
        else {
            try{
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();
                int research_count = DBRequests.countResearch(dbConnection.connection, user_id);
                int question_count = DBRequests.countQuestions(dbConnection.connection, user_id);
                HashMap<String, Integer> topics_count = DBRequests.countTopics(dbConnection.connection, user_id);
                request.setAttribute("research_count", research_count);
                request.setAttribute("question_count", question_count);
                request.setAttribute("topics_count", topics_count);
                dispatcher = request.getRequestDispatcher(STATISTICS_PAGE);
                dispatcher.forward(request, response);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
