package BarApplication;

import java.sql.*;
import java.util.ArrayList;

public class ExecuteStatement
{
    private Database database;
    private StringBuilder stringBuilder;

    public ExecuteStatement()
    {
        database = new Database();
    }

    public void insertInto(String drinkName, int amount, int tableNumber, boolean orderFinished) throws SQLException {
        Connection connection = database.getConnection();
        String preparedInsert = "INSERT INTO bar_order VALUES(0, ?, ?, ?, ?)";

        PreparedStatement insertOrder = connection.prepareStatement(preparedInsert);
        insertOrder.setString(1, drinkName);
        insertOrder.setInt(2, amount);
        insertOrder.setInt(3, tableNumber);
        insertOrder.setBoolean(4, orderFinished);

        insertOrder.execute();

        System.out.println("Inserting works");

        insertOrder.close();
        connection.close();
    }

    public ArrayList<String> getDrinkNames() throws SQLException {
        ArrayList<String> drinkNamesList = new ArrayList<>();

        stringBuilder = new StringBuilder();
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();

        stringBuilder.append("SELECT * FROM drinks");

        String sqlQuery = stringBuilder.toString();
        ResultSet resultSet = stmt.executeQuery(sqlQuery);

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
