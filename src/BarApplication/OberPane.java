package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class OberPane
{
    private Database database;
    private Order order;
    private Drinks drinks;
    private TextField textFieldTableNumber, textFieldAmountOfDrinks;
    private Label labelHeader, labelTableNumber, labelDrinkName;
    private Button buttonOrder, buttonPlus, buttonMinus;
    private int defaultAmountOfDrinks = 0;

    public OberPane(GridPane p) throws SQLException {
        database = new Database();

        this.createFXComponents();
        this.addtoGridPane(p);
        this.buttonAddEvent();
        this.renderListOfDrinks(p);
        this.buttonPlusEvent();
    }

    private void createFXComponents()
    {
        textFieldTableNumber = new TextField();

        labelHeader = new Label("Order drink");
        labelTableNumber = new Label("Table number: ");

        buttonOrder = new Button("Order");
    }

    private void addtoGridPane(GridPane p)
    {
        p.add(labelHeader, 0, 0);

        p.add(labelTableNumber, 0, 1);
        p.add(textFieldTableNumber, 1, 1);

        p.add(buttonOrder, 2, 2);
    }

    private void renderListOfDrinks(GridPane p) throws SQLException {
        int i = 0;

        for (String drinkNames : database.getDrinkNames())
        {
            i++;

            labelDrinkName = new Label(drinkNames);
            buttonMinus = new Button("-");
            buttonPlus = new Button("+");
            textFieldAmountOfDrinks = new TextField();

            textFieldAmountOfDrinks.setText(String.valueOf(defaultAmountOfDrinks));

            p.add(labelDrinkName, 0, 2 + i);
            p.add(buttonMinus, 1, 2 + i);
            p.add(textFieldAmountOfDrinks, 2, 2 + i);
            p.add(buttonPlus, 3, 2 + i);
        }
    }

    private void buttonAddEvent()
    {
        Drinks tequila = new Drinks("Tequila");

        order = new Order(2, 1, false);

        buttonOrder.setOnAction(event -> {
            order.addDrink(tequila);

            try
            {
                database.insertInto(order.getDrinkName(), order.getAmount(), order.getTableNumber(), order.getOrderFinished());
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void buttonPlusEvent()
    {
        int count = this.defaultAmountOfDrinks;

        buttonPlus.setOnAction(event ->  {

        });
    }
}
