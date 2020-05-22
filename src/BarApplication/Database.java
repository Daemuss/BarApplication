package BarApplication;

import java.sql.*;
import java.util.ArrayList;

public class Database
{
    private Connection connection;
    private final String DB_URL = "jdbc:mysql://localhost:3306/barapp?serverTimezone=UTC";
    private final String DB_USER = "BarAdmin";
    private final String DB_PASSWORD = "VodkaGoodForYou!1";

    public Database() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }
}
