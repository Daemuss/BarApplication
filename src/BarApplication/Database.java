package BarApplication;

import java.sql.*;
import java.util.ArrayList;

public class Database
{
    private Connection connection;
    private Statement statement;
    private String sqlQuery;
    private ResultSet resultSet;
    private StringBuilder stringBuilder;
    private final String DB_URL = "jdbc:mysql://localhost:3306/barapp?serverTimezone=UTC";
    private final String DB_USER = "BarAdmin";
    private final String DB_PASSWORD = "VodkaGoodForYou!1";

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

        return connection;
    }

    public void insertInto(String drinkName, int amount, int tableNumber, boolean orderFinished) throws SQLException {
        Connection connection = this.getConnection();
        String preparedInsert = "INSERT INTO bar_order VALUES(0, ?, ?, ?, ?)";

        PreparedStatement insertOrder = connection.prepareStatement(preparedInsert);
        insertOrder.setString(1, drinkName);
        insertOrder.setInt(2, amount);
        insertOrder.setInt(3, tableNumber);
        insertOrder.setBoolean(4, orderFinished);

        insertOrder.execute();

        System.out.println("Inserting works");

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

    public ArrayList<String> getDrinkNames() throws SQLException {
        ArrayList<String> drinkNamesList = new ArrayList<>();

        stringBuilder = new StringBuilder();
        Connection connection = this.getConnection();
        Statement stmt = connection.createStatement();

        stringBuilder.append("SELECT * FROM drinks");

        sqlQuery = stringBuilder.toString();
        resultSet = stmt.executeQuery(sqlQuery);

        while(resultSet.next())
        {
            String genreName = resultSet.getString("name");

            drinkNamesList.add(genreName);
        }
        resultSet.close();
        stmt.close();
        connection.close();

        return drinkNamesList;
    }
}
