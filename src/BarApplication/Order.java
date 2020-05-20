package BarApplication;

import java.util.ArrayList;

public class Order
{
    private ArrayList<Drink> drinkList;
    private boolean orderFinished;
    private int amount, tableNumber;

    public Order(int amount, int tableNumber, boolean orderFinished)
    {
        this.drinkList = new ArrayList<>();

        this.amount = amount;
        this.tableNumber = tableNumber;
        this.orderFinished = orderFinished;
    }

    public void addDrink(Drink drinks)
    {
        this.drinkList.add(drinks);
    }

    public String getDrinkName()
    {
        return drinkList.get(0).getDrinkName();
    }

    public int getAmount()
    {
        return amount;
    }

    public int getTableNumber()
    {
        return tableNumber;
    }

    public boolean getOrderFinished()
    {
        return orderFinished;
    }
}
