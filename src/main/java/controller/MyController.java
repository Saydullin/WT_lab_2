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
import service.UserDataChecker;
import service.UserIdentifier;
import service.UserInfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/account")
public class MyController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String PAGE1 = "/WEB-INF/view/jsp/page1.jsp";
    public static final String PAGE2 = "/WEB-INF/view/jsp/page2.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> params = ParametersParser.parse(request.getQueryString());
        Cookie[] cookies = request.getCookies();
        int user_id = UserIdentifier.getUsercode(cookies);
        RequestDispatcher dispatcher;
        if (user_id == 0){
            dispatcher = request.getRequestDispatcher(PAGE2);
            dispatcher.forward(request, response);
        }
        else if (params.containsKey("signout")){
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();
                DBRequests.updateUserStatus(dbConnection.connection, user_id, "inactive");
                Cookie userCodeRemove = new Cookie("usercode", "0");
                userCodeRemove.setMaxAge(0);
                response.addCookie(userCodeRemove);
                Cookie userRoleRemove = new Cookie("userrole", "");
                userRoleRemove.setMaxAge(0);
                response.addCookie(userRoleRemove);
                dispatcher = request.getRequestDispatcher(PAGE2);
                dispatcher.forward(request, response);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else if (params.containsKey("email")){
            try {
                int code = UserDataChecker.checkData(params);
                if (code == 0){
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.establish();
                    ResultSet rs = DBRequests.findUser(dbConnection.connection, params.get("email"));
                    if (!rs.next() || rs.getInt(1) == user_id) {
                        DBRequests.updateUserInfo(dbConnection.connection, user_id, params.get("email"), params.get("psw"));
                        UserInfo userInfo = DBRequests.getUserInfo(dbConnection.connection, user_id);
                        dbConnection.close();
                        dispatcher = request.getRequestDispatcher(PAGE1);
                        request.setAttribute("login", userInfo.getLogin());
                        request.setAttribute("password", userInfo.getPassword());
                        dispatcher.forward(request, response);
                    }
                    else { //if user with such login exists
                        dispatcher = request.getRequestDispatcher(PAGE1);
                        request.setAttribute("login", params.get("email"));
                        request.setAttribute("password", params.get("psw"));
                        request.setAttribute("code", 4);
                        dispatcher.forward(request, response);
                    }

                }
                else { //incorrect input
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.establish();
                    UserInfo userInfo = DBRequests.getUserInfo(dbConnection.connection, user_id);
                    dbConnection.close();
                    dispatcher = request.getRequestDispatcher(PAGE1);
                    request.setAttribute("login", userInfo.getLogin());
                    request.setAttribute("password", userInfo.getPassword());
                    request.setAttribute("code", code);
                    dispatcher.forward(request, response);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();
                UserInfo userInfo = DBRequests.getUserInfo(dbConnection.connection, user_id);
                dbConnection.close();
                dispatcher = request.getRequestDispatcher(PAGE1);
                request.setAttribute("login", userInfo.getLogin());
                request.setAttribute("password", userInfo.getPassword());
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


