package org.example.arevalo_henry_csc311_playingcards;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public class HelloController {
    @FXML
    private ImageView firstCardImage;
    @FXML
    private ImageView secondCardImage;
    @FXML
    private ImageView thirdCardImage;
    @FXML
    private ImageView fourthCardImage;
    @FXML
    private TextField expressionField;
    @FXML
    private TextField solutionField;
    @FXML
    private Label statusLabel;

    private final GameLogic gameLogic = new GameLogic();
    private List<Card> currentCards;

    @FXML
    private void initialize() {
        refreshGame();
    }

    @FXML
    private void onVerifyButtonClick() {
        GameLogic.VerificationResult result =
                gameLogic.verifyExpression(currentCards, expressionField.getText());
        statusLabel.setText(result.getMessage());
        statusLabel.getStyleClass().removeAll("status-neutral", "status-success", "status-error");
        statusLabel.getStyleClass().add(result.isCorrect() ? "status-success" : "status-error");
    }

    @FXML
    private void onFindSolutionButtonClick() {
        Optional<String> solution = gameLogic.findSolution(currentCards);
        if (solution.isPresent()) {
            solutionField.setText(solution.get());
            statusLabel.setText("A solution was found for these cards.");
            statusLabel.getStyleClass().removeAll("status-neutral", "status-success", "status-error");
            statusLabel.getStyleClass().add("status-success");
        } else {
            solutionField.clear();
            statusLabel.setText("No solution exists for this set of cards. Refresh to deal new cards.");
            statusLabel.getStyleClass().removeAll("status-neutral", "status-success", "status-error");
            statusLabel.getStyleClass().add("status-error");
        }
    }

    @FXML
    private void onRefreshButtonClick() {
        refreshGame();
    }

    private void refreshGame() {
        currentCards = gameLogic.dealCards();
        ImageView[] cardImages = {
                firstCardImage, secondCardImage, thirdCardImage, fourthCardImage
        };

        for (int i = 0; i < currentCards.size(); i++) {
            URL imageResource = getClass().getResource(currentCards.get(i).getImageResource());
            if (imageResource == null) {
                throw new IllegalStateException(
                        "Missing image resource: " + currentCards.get(i).getImageResource());
            }
            cardImages[i].setImage(new Image(imageResource.toExternalForm()));
            cardImages[i].setAccessibleText(currentCards.get(i).toString());
        }

        expressionField.clear();
        solutionField.clear();
        statusLabel.setText("Enter an expression that uses each card value once and equals 24.");
        statusLabel.getStyleClass().removeAll("status-neutral", "status-success", "status-error");
        statusLabel.getStyleClass().add("status-neutral");
    }
}
