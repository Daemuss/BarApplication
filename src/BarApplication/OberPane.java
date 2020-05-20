package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class OberPane
{
    private Database database;
    private ExecuteStatement executeStatement;
    private Order order;
    private TextField textFieldTableNumber, textFieldAmountOfDrinks;
    private Label labelHeader, labelTableNumber, labelDrinkName;
    private Button buttonOrder, buttonPlus, buttonMinus;
    private int defaultAmountOfDrinks = 0;

    public OberPane(GridPane p) throws SQLException {
        database = new Database();
        executeStatement = new ExecuteStatement();

        this.createFXComponents();
        this.addtoGridPane(p);
        this.buttonAddEvent();
        this.renderListOfDrinks(p);
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

        for (String drinkNames : executeStatement.getDrinkNames())
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

            this.buttonPlusEvent();
        }
    }

    private void buttonAddEvent()
    {
        try
        {
            buttonOrder.setOnAction(event -> {
                Drink tequila = new Drink("Beer");
                int tableNumber = Integer.parseInt(textFieldTableNumber.getText());

                order = new Order(2, tableNumber, false);

                order.addDrink(tequila);
                try
                {
                    executeStatement.insertInto(order.getDrinkName(), order.getAmount(), order.getTableNumber(), order.getOrderFinished());
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }

    private void buttonPlusEvent()
    {
        buttonPlus.setOnAction(event ->  {
            defaultAmountOfDrinks++;

            System.out.println(defaultAmountOfDrinks);
        });
    }
}
