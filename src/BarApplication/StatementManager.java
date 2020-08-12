package BarApplication;

import java.sql.*;
import java.util.ArrayList;

public class StatementManager
{
    private Database database;
    private StringBuilder stringBuilder;       

    // Inserts an order into the database
    public void insertInto(int tableNumber, boolean readyToServe, boolean isServed) throws SQLException
    {    
    	database = new Database();
    	String preparedInsert = "INSERT INTO orders VALUES(0, ?, ?, ?)";  
    	String preparedInsertOrderItems = "INSERT INTO order_items VALUES(0, ?, ?, ?)";
    	
		try (Connection connection = database.getConnection();
			 PreparedStatement insertOrder = connection.prepareStatement(preparedInsert);
			 PreparedStatement insertOrderItems = connection.prepareStatement(preparedInsertOrderItems);
		) {					        	       	      	        
	        insertOrder.setInt(1, tableNumber);
	        insertOrder.setBoolean(2, readyToServe);
	        insertOrder.setBoolean(3, false);
	        
	        insertOrderItems.setInt(1, 3);
	        insertOrderItems.setInt(2, 3);
	        insertOrderItems.setInt(3, 3);

	        insertOrder.execute();
	        insertOrderItems.execute();

	        System.out.println("Inserting works");	      
		}		
    }

    // Returns the drink names from the database
    public ArrayList<Drink> getDrinkNames() throws SQLException
    {
    	database = new Database();
        ArrayList<Drink> drinkNamesList = new ArrayList<>();
        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM drinks");
        String sqlQuery = stringBuilder.toString();
        
        try(Connection connection = database.getConnection();
            Statement stmt = connection.createStatement();
    		ResultSet resultSet = stmt.executeQuery(sqlQuery);	
        ) {        	           
            while(resultSet.next())
            {
                String drinkName = resultSet.getString("name");
                Double drinkPrice = resultSet.getDouble("price");

                drinkNamesList.add(new Drink(drinkName, drinkPrice, 2));
            }
            return drinkNamesList;
        }       
    }   

    // Returns a list of orders
    public ArrayList<Order> getOrderList() throws SQLException
    {
    	database = new Database();
    	ArrayList<Order> orderList = new ArrayList<>();
    	String blah = "SELECT * " +
                "FROM order_items " +
                "LEFT JOIN drinks ON order_items.drink_id = drinks.id " +
                "RIGHT JOIN orders ON order_items.order_id = orders.id " +
                "GROUP BY drinks.name, drinks.price, orders.table_number, orders.finished, orders.served, order_items.amount " +
                "ORDER BY order_items.id";        	
    	
    	try (Connection connection = database.getConnection();
			 Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(blah);	
    	) {
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
    		return orderList;
    	}    	
    }
}
