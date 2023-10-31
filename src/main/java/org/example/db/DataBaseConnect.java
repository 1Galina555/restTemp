package org.example.db;
import java.sql.*;

public class DataBaseConnect implements ConnectionManager{
    private static Connection connection;
    private Statement statement;
    private String className;
    private String url;
    private String user;
    private String password;
    public DataBaseConnect(String className, String url, String user, String password) {
        this.className = className;
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Unable to connect database");
            e.printStackTrace();
        }
    }
    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }
    public void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement getPreparedStatement(String value) throws SQLException {
        return connection.prepareStatement(value);
    }

}
