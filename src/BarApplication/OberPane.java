package BarApplication;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class OberPane
{
    private Database database;
    private StatementManager executeStatement;
    private Order order;
    private TextField textFieldTableNumber, textFieldAmountOfDrinks;
    private ArrayList<TextField> textFieldListAmountOfDrinks;
    private Label labelHeader, labelTableNumber, labelDrinkName, labelPrice;
    private Button buttonOrder, buttonPlus, buttonMinus, buttonBartenderPane;
    private int defaultAmountOfDrinks = 0;
    private ArrayList<Integer> amountList;

    public OberPane(GridPane p) throws SQLException {
        database = new Database();
        executeStatement = new StatementManager();

//        executeStatement.getOrders();

        this.createFXComponents();
        this.addtoGridPane(p);
        this.buttonAddEvent();
        this.renderDrinkMenu(p);
        this.buttonBartenderPaneEvent();
    }

    private void createFXComponents()
    {
        textFieldTableNumber = new TextField();

        labelHeader = new Label("Order drink");
        labelTableNumber = new Label("Table number: ");

        buttonOrder = new Button("Order");
        buttonBartenderPane = new Button("Bartender");
    }

    private void addtoGridPane(GridPane p)
    {
        p.add(labelHeader, 0, 0);

        p.add(labelTableNumber, 0, 1);
        p.add(textFieldTableNumber, 1, 1);

        p.add(buttonOrder, 2, 2);
        p.add(buttonBartenderPane, 3, 2);
    }

    private void renderListTextFields(GridPane p)
    {
        int i = 0;
        for (TextField textFieldAmount : textFieldListAmountOfDrinks)
        {
            i++;

            textFieldAmount.setText(String.valueOf(defaultAmountOfDrinks));

            p.add(textFieldAmount, 3, 2 + i);
        }
    }

    private void renderDrinkMenu(GridPane p) throws SQLException
    {
        textFieldListAmountOfDrinks = new ArrayList<>();
        amountList = new ArrayList<>();
        int i = 0;

        for (Drink drinkNames : executeStatement.getDrinkNames())
        {
            i++;

            amountList.add(defaultAmountOfDrinks);

            labelDrinkName = new Label(drinkNames.getDrinkName());
            labelPrice = new Label("�" + drinkNames.getPrice());
            buttonMinus = new Button("-");
            buttonPlus = new Button("+");
            textFieldAmountOfDrinks = new TextField();
            textFieldListAmountOfDrinks.add(textFieldAmountOfDrinks);

            p.add(labelDrinkName, 0, 2 + i);
            p.add(labelPrice, 1, 2 + i);
            p.add(buttonMinus, 2, 2 + i);
            p.add(buttonPlus, 4, 2 + i);

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
                Drink beer = new Drink("Beer", 2.00, 2);
                int tableNumber = Integer.parseInt(textFieldTableNumber.getText());

                order = new Order(tableNumber, false, false);

                order.addDrink(beer);
                try
                {
                    executeStatement.insertInto(order.getTableNumber(), order.getReadyToServe(), order.getIsServed());
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
        AtomicInteger amount = new AtomicInteger(amountList.get(i - 1));

        buttonPlus.setOnAction(event ->  {
            amount.getAndIncrement();
            textFieldListAmountOfDrinks.get(listIndex).setText(String.valueOf(amount.get()));
        });
    }

    private void buttonMinusEvent(int i)
    {
        int listIndex = i - 1;
        AtomicInteger amount = new AtomicInteger(amountList.get(i - 1));

        buttonMinus.setOnAction(event ->  {
            amount.set(amount.get() - 1);
            textFieldListAmountOfDrinks.get(listIndex).setText(String.valueOf(amount.get()));
        });
    }

    private void createBartenderPane() throws SQLException
    {
        GridPane root = new GridPane();
        new BartenderPane(root);
        Stage bartenderStage = new Stage();
        bartenderStage.setTitle("Bartender");
        bartenderStage.setScene(new Scene(root, 400, 400));
        bartenderStage.show();
    }

    private void buttonBartenderPaneEvent()
    {
        buttonBartenderPane.setOnAction(event -> {
            try
            {
                createBartenderPane();
            }
            catch (SQLException e)
            {
                System.out.print(e);
            }

        });
    }
}
