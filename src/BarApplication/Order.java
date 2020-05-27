package BarApplication;

import java.util.ArrayList;

public class Order
{
    private ArrayList<Drink> drinkList;
    private boolean readyToServe, isServed;
    private int tableNumber;

    public Order(int tableNumber, boolean readyToServe, boolean isServed)
    {
        this.drinkList = new ArrayList<>();

        this.tableNumber = tableNumber;
        this.readyToServe = readyToServe;
        this.isServed = isServed;
    }

    public void addDrink(Drink drink)
    {
        this.drinkList.add(drink);
    }

    public void setReadyToServe(boolean readyToServe)
    {
        this.readyToServe = readyToServe;
    }

    public ArrayList<Drink> getDrinkList()
    {
        return drinkList;
    }

    public int getTableNumber()
    {
        return tableNumber;
    }

    public boolean getReadyToServe()
    {
        return readyToServe;
    }

    public boolean getIsServed()
    {
        return isServed;
    }
}
