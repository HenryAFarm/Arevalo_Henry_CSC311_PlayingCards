package org.example.arevalo_henry_csc311_playingcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Henry Arevalo
 *
 * In this assignment, you will implement the Card 24 Game using JavaFX. The goal of the game is to use four randomly selected playing cards and apply arithmetic operations to create an expression that evaluates to 24.
 *
 *
 *
 * Game Rules & Requirements
 *
 *
 *
 * 1. Card Values
 *
 * The cards are represented by:
 * Numbers 2-10 as their face value.
 * Ace as 1.
 * Jack as 11.
 * Queen as 12.
 * King as 13.
 *
 *
 * 2. Gameplay
 *
 * The application randomly selects four playing cards and displays them.
 * The player must enter an arithmetic expression that:
 * Uses all four numbers exactly once.
 * Evaluates to 24.
 * Can include addition (+), subtraction (-), multiplication (*), and division (/).
 * Can use parentheses for grouping.
 *
 *
 * 3. Game Features
 *
 * A text field to enter the expression.
 * A “Verify” button to check the solution:
 * Validate whether the numbers used match the four displayed cards.
 * Ensure the expression correctly evaluates to 24.
 * Display the result in a dialog box.
 * A “Refresh” button to generate a new set of cards if a solution does not exist.
 *
 *
 */



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 440);




        stage.setTitle("Card game-24");
        stage.setScene(scene);
        stage.show();
    }
}
