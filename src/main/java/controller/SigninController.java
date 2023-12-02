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
import service.ParametersParser;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/signin")
public class SigninController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SIGNIN_PAGE = "/WEB-INF/view/jsp/signin.jsp";
    public static final String RESEARCH_LIST_PAGE = "/WEB-INF/view/jsp/research_list.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        if (ParametersParser.parse(request.getQueryString()).isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException();
            }
        }
        else {
            DBConnection dbConnection = new DBConnection();
            dbConnection.establish();
            HashMap<String, String> parameters = ParametersParser.parse(request.getQueryString());
            try {
                ResultSet rs = DBRequests.findUser(dbConnection.connection, parameters.get("email"));
                if (rs.next()) {
                    int id_user = rs.getInt(1);
                    String role = rs.getString(2);
                    String password = rs.getString(3);
                    if (password.equals(parameters.get("psw"))) {
                        DBRequests.updateUserStatus(dbConnection.connection, id_user, "active");
                        dbConnection.close();
                        response.getWriter().append("id: ").append(String.valueOf(id_user)).append(" role: ").append(role);
                        Cookie cookie = new Cookie("usercode", String.valueOf(id_user));
                        response.addCookie(cookie);
                        Cookie cookie2 = new Cookie("userrole", role);
                        response.addCookie(cookie2);
                        if (role.equals("admin")) {//Admin
                            try {
                                String[] researches;
                                DBConnection dbCon = new DBConnection();
                                dbCon.establish();
                                researches = DBRequests.getResearchesList(dbCon.connection);
                                dbCon.close();
                                request.setAttribute("researches", researches);
                                request.setAttribute("user_role", role);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            RequestDispatcher dispatcher = request.getRequestDispatcher(RESEARCH_LIST_PAGE);
                            if (dispatcher != null){
                                dispatcher.forward(request, response);
                            } else{
                                throw new ServletException();
                            }
                        }
                        else {//User
                            try {
                                String[] researches;
                                DBConnection dbCon = new DBConnection();
                                dbCon.establish();
                                researches = DBRequests.getActiveResearchesList(dbCon.connection, id_user);
                                dbCon.close();
                                request.setAttribute("researches", researches);
                                request.setAttribute("user_role", role);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            RequestDispatcher dispatcher = request.getRequestDispatcher(RESEARCH_LIST_PAGE);
                            if (dispatcher != null){
                                dispatcher.forward(request, response);
                            } else{
                                throw new ServletException();
                            }
                        }

                    }
                    else {
                        dbConnection.close();
                        RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
                        if (dispatcher != null){
                            request.setAttribute("code", 2);
                            dispatcher.forward(request, response);
                        } else{
                            throw new ServletException();
                        }
                    }
                }
                else {
                    dbConnection.close();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
                    if (dispatcher != null){
                        request.setAttribute("code", 1);
                        dispatcher.forward(request, response);
                    } else{
                        throw new ServletException();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
