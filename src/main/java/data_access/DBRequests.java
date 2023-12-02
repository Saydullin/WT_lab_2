package data_access;

import service.ParametersParser;
import service.ResearchInfo;
import service.UserDataChecker;
import service.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBRequests
{
    public static ResultSet findUser(Connection connection, String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_user, role, password FROM users WHERE login = ?");
        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static void addUser(Connection connection, String email, String password) throws SQLException{
        int maxCounter;
        try {
            PreparedStatement helpStmt = connection.prepareStatement("SELECT MAX(id_user) FROM users");
            ResultSet rs = helpStmt.executeQuery();
            rs.next();
            maxCounter = rs.getInt(1);
        }
        catch (SQLException e){
           maxCounter = 0;
        }
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (id_user, login, password, role, status) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, maxCounter + 1);
        stmt.setString(2, email);
        stmt.setString(3, password);
        if (UserDataChecker.isAdmin(email)){
            stmt.setString(4, "admin");
        }
        else {
            stmt.setString(4, "user");
        }
        stmt.setString(5, "inactive");
        stmt.executeUpdate();
    }

    public static void updateUserStatus(Connection connection, int id, String status) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE users SET status = ? WHERE id_user = ?");
        stmt.setString(1, status);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }

    public static void addResearch(Connection connection, String research_name) throws SQLException {
        int maxCounter;
        try {
            PreparedStatement helpStmt = connection.prepareStatement("SELECT MAX(id_research) FROM researches");
            ResultSet rs = helpStmt.executeQuery();
            rs.next();
            maxCounter = rs.getInt(1);
        }
        catch (SQLException e){
            maxCounter = 0;
        }
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO researches (id_research, research_name, status) VALUES (?, ?, ?)");
        stmt.setInt(1, maxCounter + 1);
        stmt.setString(2, research_name);
        stmt.setString(3, "paused");
        stmt.executeUpdate();
    }

    public static String[] getResearchesList(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT research_name FROM researches");
        ResultSet rs = stmt.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString(1));
        }
        String[] result = new String[]{};
        return list.toArray(result);
    }

    public static String[] getActiveResearchesList(Connection connection, int id_user) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT research_name, id_research  FROM researches WHERE status = 'running'");
        ResultSet rs = stmt.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()){
            if (!checkAnswered(connection, rs.getInt(2), id_user)) {
                list.add(rs.getString(1));
            }
        }
        String[] result = new String[]{};
        return list.toArray(result);
    }

    public static boolean checkAnswered(Connection connection, int id_research, int id_user) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_answer FROM answers WHERE research = ? AND user = ?");
        stmt.setInt(1, id_research);
        stmt.setInt(2, id_user);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public static ResearchInfo findResearchInfo(Connection connection, String research_name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id_research, status FROM researches WHERE research_name = ?");
        statement.setString(1, research_name);
        ResultSet rs = statement.executeQuery();
        rs.next();
        int research_id = rs.getInt(1);
        String status = rs.getString(2);
        PreparedStatement stmt = connection.prepareStatement("SELECT question, topic FROM questions WHERE research = ?");
        stmt.setInt(1, research_id);
        ResultSet resultSet = stmt.executeQuery();
        PreparedStatement topic_stmt = connection.prepareStatement("SELECT topic_name FROM topics WHERE id_topic = ?");
        HashMap<String, String> questions = new HashMap<>();
        ResultSet topicSet;
        while (resultSet.next()){
            int topic_id = resultSet.getInt(2);
            topic_stmt.setInt(1, topic_id);
            topicSet = topic_stmt.executeQuery();
            topicSet.next();
            questions.put(resultSet.getString(1), topicSet.getString(1));
        }
        return new ResearchInfo(research_id, research_name, questions, status);
    }

    public static String[] getTopics (Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT topic_name FROM topics");
        ResultSet rs = stmt.executeQuery();
        String[] result = new String[]{};
        List<String> topics = new ArrayList<>();
        while (rs.next()) {
            topics.add(rs.getString(1));
        }
        return topics.toArray(result);
    }

    public static void addQuestion(Connection connection, String research, String topic, String question) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_topic FROM topics WHERE topic_name = ?");
        stmt.setString(1, topic);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int topic_id = rs.getInt(1);
        int maxCounter;
        try {
            PreparedStatement idStmt = connection.prepareStatement("SELECT MAX(id_question) FROM questions");
            ResultSet idRs = idStmt.executeQuery();
            idRs.next();
            maxCounter = idRs.getInt(1);
        }
        catch (SQLException e){
            maxCounter = 0;
        }
        PreparedStatement researchStmt = connection.prepareStatement("SELECT id_research FROM researches WHERE research_name = ?");
        researchStmt.setString(1, research);
        ResultSet researchRs = researchStmt.executeQuery();
        researchRs.next();
        int research_id = researchRs.getInt(1);
        PreparedStatement lastStmt = connection.prepareStatement("INSERT INTO questions (id_question, question, topic, research) VALUES (?, ?, ?, ?)");
        lastStmt.setInt(1, maxCounter + 1);
        lastStmt.setString(2, question);
        lastStmt.setInt(3, topic_id);
        lastStmt.setInt(4, research_id);
        lastStmt.executeUpdate();
    }

    public static void setResearchStatus(Connection connection, String research, String status) throws SQLException {
        PreparedStatement researchStmt = connection.prepareStatement("SELECT id_research FROM researches WHERE research_name = ?");
        researchStmt.setString(1, research);
        ResultSet researchRs = researchStmt.executeQuery();
        researchRs.next();
        int research_id = researchRs.getInt(1);
        PreparedStatement stmt = connection.prepareStatement("UPDATE researches SET status = ? WHERE id_research = ?");
        stmt.setString(1, status);
        stmt.setInt(2, research_id);
        stmt.executeUpdate();
    }

    public static HashMap<String, String> getAnswers(Connection connection, String question) throws SQLException {
        PreparedStatement questStmt = connection.prepareStatement("SELECT id_question FROM questions WHERE question = ?");
        questStmt.setString(1, question);
        ResultSet questRs = questStmt.executeQuery();
        questRs.next();
        int question_id = questRs.getInt(1);
        HashMap<String, String> result = new HashMap<>();
        PreparedStatement answerStmt = connection.prepareStatement("SELECT answer, user FROM answers WHERE question = ?");
        PreparedStatement userStmt = connection.prepareStatement("SELECT login FROM users WHERE id_user = ?");
        answerStmt.setInt(1, question_id);
        ResultSet answerRs = answerStmt.executeQuery();
        int user_id;
        ResultSet userRs;
        while (answerRs.next()){
            user_id = answerRs.getInt(2);
            userStmt.setInt(1, user_id);
            userRs = userStmt.executeQuery();
            userRs.next();
            result.put(answerRs.getString(1), userRs.getString(1));
            answerRs.getString(1);
        }
        return result;
    }

    public static UserInfo getUserInfo(Connection connection, int id_user) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id_user = ?");
        stmt.setInt(1, id_user);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return new UserInfo(id_user, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
    }

    public static void updateUserInfo(Connection connection, int id, String login, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE users SET login = ?, password = ? WHERE id_user = ?");
        stmt.setString(1, login);
        stmt.setString(2, password);
        stmt.setInt(3, id);
        stmt.executeUpdate();
    }

    public static int getReserchId(Connection connection, String research_name) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_research FROM researches WHERE research_name = ?");
        stmt.setString(1, research_name);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static int getQuestionId(Connection connection, String question) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_question FROM questions WHERE question = ?");
        stmt.setString(1, question);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static void addAnswer(Connection connection, String research_name, String question, String answer, int user_id) throws SQLException {
        int maxCounter;
        try {
            PreparedStatement helpStmt = connection.prepareStatement("SELECT MAX(id_answer) FROM answers");
            ResultSet rs = helpStmt.executeQuery();
            rs.next();
            maxCounter = rs.getInt(1);
        }
        catch (SQLException e){
            maxCounter = 0;
        }
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO answers (id_answer, question, user, answer, research) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, maxCounter + 1);
        stmt.setInt(2, getQuestionId(connection, question));
        stmt.setInt(3, user_id);
        stmt.setString(4, answer);
        stmt.setInt(5, getReserchId(connection, research_name));
        stmt.executeUpdate();
    }

    public static int countQuestions(Connection connection, int user_id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM answers WHERE user = ?");
        stmt.setInt(1, user_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static int countResearch(Connection connection, int user_id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(DISTINCT research) FROM answers WHERE user = ?");
        stmt.setInt(1, user_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static HashMap<String, Integer> countTopics(Connection connection, int user_id) throws SQLException {
        PreparedStatement topicStmt = connection.prepareStatement("SELECT id_topic, topic_name FROM topics");
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(DISTINCT answers.question) FROM answers JOIN questions" +
                " ON answers.question = questions.id_question WHERE questions.topic = ? AND user = ?");
        HashMap<String, Integer> result = new HashMap<>();
        ResultSet topicRs = topicStmt.executeQuery();
        ResultSet rs;
        while (topicRs.next()) {
            stmt.setInt(1, topicRs.getInt(1));
            stmt.setInt(2, user_id);
            rs = stmt.executeQuery();
            rs.next();
            result.put(topicRs.getString(2), rs.getInt(1));
        }
        return result;
    }
}
