package BarApplication;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class OberPane
{
    private Database database;
    private TextField textFieldTableNumber;
    private Label labelDrink;
    private Button buttonAdd, buttonResults, buttonPlus, buttonMinus;

    public OberPane(GridPane p)
    {
        database = new Database();

        this.createFXComponents();
        this.addtoGridPane(p);
        this.buttonAddEvent();
        this.buttonResultsEvent();
    }

    private void createFXComponents()
    {
        textFieldTableNumber = new TextField();

        buttonAdd = new Button("Add");
        buttonResults = new Button("Get results");
    }

    private void addtoGridPane(GridPane p)
    {
        p.add(textFieldTableNumber, 0, 0);

        p.add(buttonAdd, 0, 1);
        p.add(buttonResults, 1, 1);
    }

    private void buttonAddEvent()
    {
        buttonAdd.setOnAction(event -> {
            String name = textFieldTableNumber.getText();
            try
            {
                database.insertInto();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void buttonResultsEvent()
    {
        buttonResults.setOnAction(event -> {
            String name = textFieldTableNumber.getText();

            try
            {
                database.printResults(name);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
