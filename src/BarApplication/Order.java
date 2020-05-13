package BarApplication;

import java.util.ArrayList;

public class Order
{
    private ArrayList<Drinks> drinkList;
    private boolean orderFinished;
    private int tableNumber;

    public Order(ArrayList<Drinks> drinkList, boolean orderFinished, int tableNumber)
    {
        this.drinkList = drinkList;
        this.orderFinished = orderFinished;
        this.tableNumber = tableNumber;
    }

    public boolean getOrderFinished()
    {
        return orderFinished;
    }

    public int getTableNumber()
    {
        return tableNumber;
    }
}
