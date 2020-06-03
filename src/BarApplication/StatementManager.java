package BarApplication;

import java.sql.*;
import java.util.ArrayList;

public class StatementManager
{
    private Database database;
    private StringBuilder stringBuilder;

    public StatementManager()
    {
        database = new Database();
    }

    public void insertInto(Drink drink, int tableNumber, boolean readyToServe, boolean isServed) throws SQLException
    {
        Connection connection = database.getConnection();
        String preparedInsert = "INSERT INTO bar_order VALUES(0, ?, ?, ?, ?)";

        PreparedStatement insertOrder = connection.prepareStatement(preparedInsert);
        insertOrder.setString(1, drink.getDrinkName());
        insertOrder.setInt(2, drink.getAmount());
        insertOrder.setInt(3, tableNumber);
        insertOrder.setBoolean(4, readyToServe);

        insertOrder.execute();

        System.out.println("Inserting works");

        insertOrder.close();
        connection.close();
    }

    public ArrayList<Drink> getDrinkNames() throws SQLException
    {
        ArrayList<Drink> drinkNamesList = new ArrayList<>();

        stringBuilder = new StringBuilder();
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();

        stringBuilder.append("SELECT * FROM drinks");

        String sqlQuery = stringBuilder.toString();
        ResultSet resultSet = stmt.executeQuery(sqlQuery);

        while(resultSet.next())
        {
            String drinkName = resultSet.getString("name");
            Double drinkPrice = resultSet.getDouble("price");

            drinkNamesList.add(new Drink(drinkName, drinkPrice, 2));
        }
        resultSet.close();
        stmt.close();
        connection.close();

        return drinkNamesList;
    }

    public void getOrders() throws SQLException
    {
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = database.getConnection();
        String sql = "SELECT * FROM orders, drinks, order_items GROUP BY order_items.order_id";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next())
        {
            String drinkName = resultSet.getString("name");
            double drinkPrice = resultSet.getDouble("price");
            int orderTableNumber = resultSet.getInt("table_number");
            boolean orderFinisehd = resultSet.getBoolean("finished");
            boolean orderServerd = resultSet.getBoolean("served");
            int drinkAmount = resultSet.getInt("amount");

            stringBuilder.append(drinkName);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(drinkPrice);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(orderTableNumber);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(orderFinisehd);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(orderServerd);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(drinkAmount);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());

            System.out.print(stringBuilder.toString());
        }

        statement.close();
        resultSet.close();
        connection.close();
    }
}
