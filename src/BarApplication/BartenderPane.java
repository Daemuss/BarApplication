package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class BartenderPane {
    private Label amount, tableNunber, tableNumberHeader, drinksHeader, amountHeader, labelFinished;
    private ArrayList<Order> orders;
    private TextArea textAreaDrinks;
    private StringBuilder stringBuilder;
    private StatementManager statementManager;

    public BartenderPane(GridPane p) throws SQLException
    {
//        this.addToOrderList();
        this.statementManager = new StatementManager();
        this.orders = statementManager.getOrderListWithItems();
        this.showOrders(p);
    }

//    private void addToOrderList()
//    {
//        orders = new ArrayList<>();
//
//        Drink beer = new Drink("Beer", 2.00, 2);
//        Drink tequila = new Drink("Tequila", 2.00, 5);
//        Drink cola = new Drink("Cola", 2.00, 2);
//        Drink water = new Drink("Water", 2.00, 5);
//
//        Order order = new Order(2, false, false);
//        order.addDrink(beer);
//        order.addDrink(cola);
//
//        Order secondOrder = new Order(5, false, false);
//        secondOrder.addDrink(tequila);
//
//        Order thirdOrder = new Order(1, false, false);
//        thirdOrder.addDrink(cola);
//
//        Order fourthOrder = new Order(4, false, false);
//        fourthOrder.addDrink(water);
//
//        orders.add(order);
//        orders.add(secondOrder);
//        orders.add(thirdOrder);
//        orders.add(fourthOrder);
//    }

    private void addTitleLabels(GridPane p)
    {
        tableNumberHeader = new Label("Table Number");
        drinksHeader = new Label("Drinks");
        amountHeader = new Label("Amount of drinks");
        labelFinished = new Label("Order Finished");

        p.add(tableNumberHeader, 0, 0);
        p.add(drinksHeader, 1, 0);
        p.add(amountHeader, 2, 0);
        p.add(labelFinished, 3, 0);
    }

    private void showOrders(GridPane p)
    {
        p.getChildren().clear();

        this.addTitleLabels(p);

        if(orders.size() > 0)
        {
            int ordersListIndex = 0;
            int i = 1;

            for (Order order : orders)
            {
                ordersListIndex++;
                i++;
                if(!order.getReadyToServe())
                {
                    stringBuilder = new StringBuilder();
                    CheckBox checboxReadyToServe = new CheckBox();
                    textAreaDrinks = new TextArea();
                    tableNunber = new Label(String.valueOf(order.getTableNumber()));

                    this.setTextAreaSettings(textAreaDrinks);
                    p.add(tableNunber, 0, i);
                    for (Drink drink : order.getDrinkList())
                    {
                        stringBuilder.append(drink.getDrinkName());
                        stringBuilder.append(System.lineSeparator());

                        amount = new Label(String.valueOf(drink.getAmount()));
                        textAreaDrinks.setText(stringBuilder.toString());

                        p.add(amount, 2, i);
                    }
                    p.add(textAreaDrinks, 1, i);
                    p.add(checboxReadyToServe, 3, i);

                    checkboxClickEvent(checboxReadyToServe, ordersListIndex, p);
                }
            }
        }
        else
        {
            System.out.println("No orders");
        }
    }

    private void setTextAreaSettings(TextArea textAreaDrinks)
    {
        textAreaDrinks.setEditable(false);
        textAreaDrinks.setPrefHeight(50);
        textAreaDrinks.setPrefWidth(100);
    }

    private void checkboxClickEvent(CheckBox checboxReadyToServe, int i, GridPane p)
    {
        int index = i - 1;

        checboxReadyToServe.setOnAction(event -> {
            orders.get(index).setReadyToServe(true);

            this.showOrders(p);
        });
    }
}
