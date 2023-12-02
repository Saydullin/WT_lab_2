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
import service.ResearchInfo;
import service.UserIdentifier;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/survey")
public class SurveyController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SURVEY_PAGE = "/WEB-INF/view/jsp/survey.jsp";
    public static final String RESEARCH_LIST_PAGE = "/WEB-INF/view/jsp/research_list.jsp";
    public static final String SIGNIN_PAGE = "/WEB-INF/view/jsp/signin.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HashMap<String, String> params = ParametersParser.parse(request.getQueryString());
        Cookie[] cookies = request.getCookies();
        int user_id = UserIdentifier.getUsercode(cookies);
        RequestDispatcher dispatcher;
        if (user_id == 0){
            dispatcher = request.getRequestDispatcher(SIGNIN_PAGE);
            dispatcher.forward(request, response);
        }
        else {
            if (!params.isEmpty()) {
                if (params.containsKey("item")) { //User get questions of the research
                    String research = params.get("item");
                    if (research != null) {
                        try {
                            DBConnection dbConnection = new DBConnection();
                            dbConnection.establish();
                            ResearchInfo researchInfo = DBRequests.findResearchInfo(dbConnection.connection, research);
                            dbConnection.close();
                            if (researchInfo.getStatus().equals("paused")) {
                                dispatcher = request.getRequestDispatcher(RESEARCH_LIST_PAGE);
                            } else {
                                dispatcher = request.getRequestDispatcher(SURVEY_PAGE);
                                request.setAttribute("research_name", researchInfo.getResearch_name());
                                request.setAttribute("questions", researchInfo.getQuestions());
                            }
                            if (dispatcher != null) {
                                dispatcher.forward(request, response);
                            } else {
                                throw new ServletException();
                            }
                        } catch (SQLException | IOException | ServletException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // User submit answers
                    String research_name = params.get("research");
                    try {
                        DBConnection dbConnection = new DBConnection();
                        dbConnection.establish();
                        params.forEach((key, value) -> {
                            if (!key.equals("research")) {
                                try {
                                    DBRequests.addAnswer(dbConnection.connection, research_name, key, value, user_id);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dbConnection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String[] researches = new String[]{};
                    try {
                        DBConnection dbCon = new DBConnection();
                        dbCon.establish();
                        researches = DBRequests.getActiveResearchesList(dbCon.connection, user_id);
                        dbCon.close();
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                    dispatcher = request.getRequestDispatcher(RESEARCH_LIST_PAGE);
                    request.setAttribute("researches", researches);
                    if (dispatcher != null) {
                        dispatcher.forward(request, response);
                    } else {
                        throw new ServletException();
                    }
                }
            }
        }
    }
}


