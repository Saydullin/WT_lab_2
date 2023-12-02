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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/research_info")
public class ResearchInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String RESEARCHINFO_PAGE = "/WEB-INF/view/jsp/research_management.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HashMap<String, String> params = ParametersParser.parse(request.getQueryString());
        if (!params.isEmpty()){
            if (params.containsKey("item")) {
                String research = params.get("item");
                if (research != null) {
                    try {
                        DBConnection dbConnection = new DBConnection();
                        dbConnection.establish();
                        ResearchInfo researchInfo = DBRequests.findResearchInfo(dbConnection.connection, research);
                        String[] topics = DBRequests.getTopics(dbConnection.connection);
                        dbConnection.close();
                        RequestDispatcher dispatcher = request.getRequestDispatcher(RESEARCHINFO_PAGE);
                        if (dispatcher != null) {
                            request.setAttribute("topics", topics);
                            request.setAttribute("research_name", researchInfo.getResearch_name());
                            request.setAttribute("questions", researchInfo.getQuestions());
                            request.setAttribute("status", researchInfo.getStatus());
                            dispatcher.forward(request, response);
                        } else {
                            throw new ServletException();
                        }
                    } catch (SQLException | IOException | ServletException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (params.containsKey("research")) {
                try {
                    //Add question to DB
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.establish();
                    DBRequests.addQuestion(dbConnection.connection, params.get("research"), params.get("topic"), params.get("question"));
                    //Get info about research
                    ResearchInfo researchInfo = DBRequests.findResearchInfo(dbConnection.connection, params.get("research"));
                    String[] topics = DBRequests.getTopics(dbConnection.connection);
                    dbConnection.close();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(RESEARCHINFO_PAGE);
                    if (dispatcher != null) {
                        request.setAttribute("topics", topics);
                        request.setAttribute("research_name", researchInfo.getResearch_name());
                        request.setAttribute("questions", researchInfo.getQuestions());
                        request.setAttribute("status", researchInfo.getStatus());
                        dispatcher.forward(request, response);
                    }
                    else {
                        throw new ServletException();
                    }

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if (params.containsKey("changed_research")){
                try {
                    //Add question to DB
                    DBConnection dbConnection = new DBConnection();
                    dbConnection.establish();
                    DBRequests.setResearchStatus(dbConnection.connection, params.get("changed_research"), params.get("research_status"));
                    //Get info about research
                    ResearchInfo researchInfo = DBRequests.findResearchInfo(dbConnection.connection, params.get("changed_research"));
                    String[] topics = DBRequests.getTopics(dbConnection.connection);
                    dbConnection.close();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(RESEARCHINFO_PAGE);
                    if (dispatcher != null) {
                        request.setAttribute("topics", topics);
                        request.setAttribute("research_name", researchInfo.getResearch_name());
                        request.setAttribute("questions", researchInfo.getQuestions());
                        request.setAttribute("status", researchInfo.getStatus());
                        dispatcher.forward(request, response);
                    }
                    else {
                        throw new ServletException();
                    }

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}
