package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import sample.calculator.Calculator;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    double readyAnswer = 0;

    @FXML
    public Button backSpace;
    @FXML
    public Text bottomDisplay;
    @FXML
    public Text topDisplay;
    @FXML
    private Button clear;
    @FXML
    private Button equals;
    @FXML
    Node vBox;

    List<String> historyCalc = new ArrayList<>();

    @FXML
    void handleButtonActionNumb(javafx.event.ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        bottomDisplay.setText(bottomDisplay.getText() + value);
    }


    @FXML
    void handleButtonOperations(javafx.event.ActionEvent event) throws ParseException, InterruptedException {
        if (event.getSource() == backSpace) {
            backSpaceMethod();
        } else if (event.getSource() == clear) {
            bottomDisplay.setText("");
            topDisplay.setText("");
        } else if (event.getSource() == equals) {
            printDisplay();
        }
    }

    public void printDisplay() throws ParseException, InterruptedException {
        String userInput = bottomDisplay.getText();
        Calculator calculator = new Calculator();
        calculator.parse(userInput);
        byte b = calculator.expressionCheck(userInput);

        if (b != 1) {
            System.out.println(calculator.getError());
            topDisplay.setText(calculator.getError());
        } else {
            if (calculator.getAnswer().empty()) {
                topDisplay.setText(calculator.getError());
            } else {
                if (!bottomDisplay.getText().equals("0.0")) {
                    readyAnswer = calculator.getReadyAnswer();
                    historyCalc.add(userInput + " = " + readyAnswer);
                    topDisplay.setText(String.valueOf(readyAnswer));
                    bottomDisplay.setText(String.valueOf(readyAnswer));
                    System.out.println(historyCalc);
                }
            }
        }
    }

    public void backSpaceMethod() {
        if (bottomDisplay.getText().length() > 0) {
            StringBuilder sb = new StringBuilder(bottomDisplay.getText());
            sb.deleteCharAt(bottomDisplay.getText().length() - 1);
            bottomDisplay.setText(sb.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBox.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.NUMPAD0)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD1)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD2)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD3)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD4)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD5)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD6)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD7)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD8)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.NUMPAD9)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.SUBTRACT)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.ADD)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.DIVIDE)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.MULTIPLY)) {
                bottomDisplay.setText(bottomDisplay.getText() + event.getText());
            } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                backSpaceMethod();
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    printDisplay();
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
