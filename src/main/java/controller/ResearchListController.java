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

@WebServlet("/list")
public class ResearchListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String RESEARCH_LIST_PAGE = "/WEB-INF/view/jsp/research_list.jsp";
    public static final String SIGNIN_PAGE = "/WEB-INF/view/jsp/signin.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        int user_id = UserIdentifier.getUsercode(cookies);
        RequestDispatcher dispatcher;
        if (user_id == 0 || UserIdentifier.getRole(cookies).equals("")){ //Cookies expired
            dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
            dispatcher.forward(request, response);
        }
        else {
            if (UserIdentifier.getRole(cookies).equals("admin")){ //Show admin list of all researches
                try {
                    String[] researches;
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.establish();
                    researches = DBRequests.getResearchesList(dbConnection.connection);
                    request.setAttribute("researches", researches);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else { //Show user list of researches which he didn't answer
                try {
                    String[] researches;
                    DBConnection dbCon = new DBConnection();
                    dbCon.establish();
                    researches = DBRequests.getActiveResearchesList(dbCon.connection, user_id);
                    dbCon.close();
                    request.setAttribute("researches", researches);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dispatcher = request.getRequestDispatcher(RESEARCH_LIST_PAGE);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException();
            }

        }
    }
}
