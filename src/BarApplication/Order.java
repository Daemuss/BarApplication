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

    public void addDrink(Drink drinks)
    {
        this.drinkList.add(drinks);
    }

    public String getDrinkName()
    {
        return drinkList.get(0).getDrinkName();
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
