package controller;

import data_access.DBConnection;
import data_access.DBRequests;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ParametersParser;
import service.UserDataChecker;
import java.util.HashMap;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SIGNUP_PAGE = "index.jsp";
    public static final String SIGNIN_PAGE = "/WEB-INF/view/jsp/signin.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException {
        try {
            HashMap<String, String> parameters = ParametersParser.parse(request.getQueryString());
            int checkResult = UserDataChecker.checkData(parameters);
            if (checkResult == 0) {
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();

                if (DBRequests.findUser(dbConnection.connection, parameters.get("email")).next()) {
                    dbConnection.close();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNUP_PAGE);
                    if (dispatcher != null){
                        request.setAttribute("code", 4);
                        dispatcher.forward(request, response);
                    } else{
                        throw new ServletException();
                    }
                }
                else {
                    DBRequests.addUser(dbConnection.connection, parameters.get("email"), parameters.get("psw"));
                    dbConnection.close();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
                    if (dispatcher != null){
                        dispatcher.forward(request, response);
                    } else{
                        throw new ServletException();
                    }
                }
            }
            else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(SIGNUP_PAGE);
                if (dispatcher != null){
                    request.setAttribute("code", checkResult);
                    dispatcher.forward(request, response);
                } else{
                    throw new ServletException();
                }
            }

        }
        catch (Exception e) {
            throw new ServletException(e);
        }
    }
}