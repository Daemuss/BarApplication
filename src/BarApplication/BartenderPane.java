package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class BartenderPane {
    private Label drinkName, amount;
    private ArrayList<Order> orders;
    private CheckBox checboxReadyToServe;

    public BartenderPane(GridPane p)
    {
        this.addToOrderList();

        this.showOrders(p);
    }

    private void addToOrderList()
    {
        orders = new ArrayList<>();

        Drink beer = new Drink("Beer", 2.00, 2);
        Drink tequila = new Drink("Tequila", 2.00, 5);

        Order order = new Order(2, false, false);
        order.addDrink(beer);

        Order secondOrder = new Order(5, false, false);
        secondOrder.addDrink(tequila);

        orders.add(order);
        orders.add(secondOrder);
    }

    private void showOrders(GridPane p)
    {
        if(orders.size() > 0)
        {
            int i = 0;

            for (Order order : orders)
            {
                i++;
                if(!order.getReadyToServe())
                {
                    checboxReadyToServe = new CheckBox();

                    for (Drink drink : order.getDrinkList())
                    {
                        drinkName = new Label(drink.getDrinkName());
                        amount = new Label(String.valueOf(drink.getAmount()));
                    }

                    p.add(drinkName, 0, i);
                    p.add(amount, 1, i);
                    p.add(checboxReadyToServe, 2, i);

                    checkboxClickEvent(i);
                }
            }
        }
        else
        {
            System.out.println("No orders");
        }
    }

    private void checkboxClickEvent(int i)
    {
        int index = i - 1;

        checboxReadyToServe.setOnAction(event -> {
            System.out.print(orders.get(index).getReadyToServe());

            orders.get(index).setReadyToServe(true);

            System.out.print(orders.get(index).getReadyToServe());
        });
    }
}
