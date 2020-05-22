package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class OberPane
{
    private Database database;
    private ExecuteStatement executeStatement;
    private Order order;
    private TextField textFieldTableNumber, textFieldAmountOfDrinks;
    private ArrayList<TextField> textFieldListAmountOfDrinks;
    private Label labelHeader, labelTableNumber, labelDrinkName;
    private Button buttonOrder, buttonPlus, buttonMinus;
    private int defaultAmountOfDrinks = 0;

    public OberPane(GridPane p) throws SQLException {
        database = new Database();
        executeStatement = new ExecuteStatement();

        this.createFXComponents();
        this.addtoGridPane(p);
        this.buttonAddEvent();
        this.renderDrinkMenu(p);
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

    private void renderListTextFields(GridPane p)
    {
        int i = 0;
        for (TextField textFieldAmount : textFieldListAmountOfDrinks)
        {
            i++;

            textFieldAmount.setText(String.valueOf(defaultAmountOfDrinks));

            p.add(textFieldAmount, 2, 2 + i);
        }
    }

    private void renderDrinkMenu(GridPane p) throws SQLException
    {
        textFieldListAmountOfDrinks = new ArrayList<>();
        int i = 0;

        for (String drinkNames : executeStatement.getDrinkNames())
        {
            i++;

            labelDrinkName = new Label(drinkNames);
            buttonMinus = new Button("-");
            buttonPlus = new Button("+");
            textFieldAmountOfDrinks = new TextField();
            textFieldListAmountOfDrinks.add(textFieldAmountOfDrinks);

            p.add(labelDrinkName, 0, 2 + i);
            p.add(buttonMinus, 1, 2 + i);
            p.add(buttonPlus, 3, 2 + i);

            this.buttonPlusEvent(i);
            this.buttonMinusEvent(i);
        }

        renderListTextFields(p);
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

    private void buttonPlusEvent(int i)
    {
        int listIndex = i - 1;

        buttonPlus.setOnAction(event ->  {
            defaultAmountOfDrinks = defaultAmountOfDrinks + 1;
            textFieldListAmountOfDrinks.get(listIndex).setText(String.valueOf(defaultAmountOfDrinks));
        });
    }

    private void buttonMinusEvent(int i)
    {
        int listIndex = i - 1;

        buttonMinus.setOnAction(event ->  {
            defaultAmountOfDrinks = defaultAmountOfDrinks - 1;
            textFieldListAmountOfDrinks.get(listIndex).setText(String.valueOf(defaultAmountOfDrinks));
        });
    }
}
