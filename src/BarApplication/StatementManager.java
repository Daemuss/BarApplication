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

//    public void getOrders() throws SQLException
//    {
//        StringBuilder stringBuilder = new StringBuilder();
//        Connection connection = database.getConnection();
//        String sql = "SELECT drinks.name, drinks.price, orders.table_number, orders.finished, orders.served, order_items.amount " +
//                "FROM orders " +
//                "INNER JOIN order_items ON orders.id = order_items.order_id " +
//                "INNER JOIN drinks ON drinks.id = order_items.drink_id " +
//                "WHERE orders.finished = 0 " +
//                "GROUP BY drinks.name, drinks.price, orders.table_number, orders.finished, orders.served, order_items.amount";
//
//        String blah = "SELECT * " +
//                "FROM order_items " +
//                "LEFT JOIN drinks ON order_items.drink_id = drinks.id " +
//                "RIGHT JOIN orders ON order_items.order_id = orders.id " +
//                "GROUP BY drinks.name, drinks.price, orders.table_number, orders.finished, orders.served, order_items.amount " +
//                "ORDER BY order_items.id";
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(blah);
//
//        while(resultSet.next())
//        {
//            String drinkName = resultSet.getString("name");
//            double drinkPrice = resultSet.getDouble("price");
//            int orderTableNumber = resultSet.getInt("table_number");
//            boolean orderFinisehd = resultSet.getBoolean("finished");
//            boolean orderServed = resultSet.getBoolean("served");
//            int drinkAmount = resultSet.getInt("amount");
//
//            stringBuilder.append(drinkName);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(drinkPrice);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(orderTableNumber);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(orderFinisehd);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(orderServed);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(drinkAmount);
//            stringBuilder.append(System.lineSeparator());
//            stringBuilder.append(System.lineSeparator());
//
//            System.out.print(stringBuilder.toString());
//        }
//
//        statement.close();
//        resultSet.close();
//        connection.close();
//    }

    public ArrayList<Order> getOrderList() throws SQLException
    {
        Connection connection = database.getConnection();
        ArrayList<Order> orderList = new ArrayList<>();

        String blah = "SELECT * " +
                "FROM order_items " +
                "LEFT JOIN drinks ON order_items.drink_id = drinks.id " +
                "RIGHT JOIN orders ON order_items.order_id = orders.id " +
                "GROUP BY drinks.name, drinks.price, orders.table_number, orders.finished, orders.served, order_items.amount " +
                "ORDER BY order_items.id";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(blah);

        while(resultSet.next())
        {
            String drinkName = resultSet.getString("name");
            double drinkPrice = resultSet.getDouble("price");
            int orderTableNumber = resultSet.getInt("table_number");
            boolean orderFinisehd = resultSet.getBoolean("finished");
            boolean orderServed = resultSet.getBoolean("served");
            int drinkAmount = resultSet.getInt("amount");

            Drink drink = new Drink(drinkName, drinkPrice, drinkAmount);
            Order order = new Order(orderTableNumber, orderFinisehd, orderServed);
            order.addDrink(drink);

            orderList.add(order);
        }

        statement.close();
        resultSet.close();
        connection.close();

        return orderList;
    }
}
