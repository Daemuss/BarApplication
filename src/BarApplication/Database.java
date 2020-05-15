package BarApplication;

import java.sql.*;

public class Database
{
    private Connection connection;
    private Statement statement;
    private String sqlQuery;
    private ResultSet resultSet;
    private StringBuilder stringBuilder;
    private final String DB_URL = "jdbc:mysql://localhost:3306/barapp?serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    public Database() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        return  connection;
    }

    public void insertInto() throws SQLException {
        stringBuilder = new StringBuilder();
        Connection connection = this.getConnection();
        Statement stmt = connection.createStatement();

        sqlQuery = stringBuilder.toString();

        stmt.execute("INSERT INTO bar_order VALUES(0, 'test order', 1)");

        System.out.println("Inserting works");

        stmt.close();
        connection.close();
    }

    public void printResults(String tableName) throws SQLException {

        stringBuilder = new StringBuilder();
        Connection connection = this.getConnection();
        Statement stmt = connection.createStatement();

        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(tableName);

        sqlQuery = stringBuilder.toString();
        resultSet = stmt.executeQuery(sqlQuery);

        while(resultSet.next())
        {
            int id = resultSet.getInt("id");
            String genreName = resultSet.getString("name");

            System.out.println("Id: " + id);
            System.out.println("Name: " + genreName);
        }
        resultSet.close();
        stmt.close();
        connection.close();

    }
}
