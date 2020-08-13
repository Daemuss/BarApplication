package BarApplication;

public class Drink
{
    private String drinkName;
    private double price;
    private int drinkId, amount;

    public Drink(int drinkId, String drinkName, double price, int amount)
    {
    	this.drinkId = drinkId;
        this.drinkName = drinkName;
        this.price = price;
        this.amount = amount;
    }

    public int getDrinkId()
    {
    	return drinkId;
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
