package data_access;

import com.mysql.jdbc.Driver;

import java.sql.*;


public class DBConnection {

    public Connection connection;

    public void establish(){
        String url = "jdbc:mysql://localhost:3306/research_app?useSSL=false";
        String user = "root";
        String pass = "password";
        try{
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
