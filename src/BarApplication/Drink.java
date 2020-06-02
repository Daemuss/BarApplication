package BarApplication;

public class Drink
{
    private String drinkName;
    private double price;
    private int amount;

    public Drink(String drinkName, double price, int amount)
    {
        this.drinkName = drinkName;
        this.price = price;
        this.amount = amount;
    }

    public String getDrinkName()
    {
        return  drinkName;
    }

    public int getAmount()
    {
        return amount;
    }

    public double getPrice()
    {
        return price;
    }

    public double totalPriceOfDrink()
    {
        return price * this.getAmount();
    }
}
