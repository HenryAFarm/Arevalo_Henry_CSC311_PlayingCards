package org.example.arevalo_henry_csc311_playingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Handles the card-dealing and answer-validation rules for the 24 game.
 * This class is independent of JavaFX so the game rules can be tested separately.
 */
public final class GameLogic {
    private static final int CARDS_PER_ROUND = 4;
    private static final double RESULT_TOLERANCE = 1.0e-9;

    /**
     * Creates a shuffled standard deck and deals four distinct cards.
     */
    public List<Card> dealCards() {
        List<Card> deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }

        Collections.shuffle(deck);
        return Collections.unmodifiableList(new ArrayList<>(deck.subList(0, CARDS_PER_ROUND)));
    }

    /**
     * Checks that an expression uses the dealt card values exactly once and evaluates to 24.
     */
    public VerificationResult verifyExpression(List<Card> cards, String expression) {
        Objects.requireNonNull(cards, "cards cannot be null");
        if (cards.size() != CARDS_PER_ROUND || cards.contains(null)) {
            throw new IllegalArgumentException("A round must contain exactly four cards.");
        }
        if (expression == null || expression.trim().isEmpty()) {
            return VerificationResult.failure("Enter an expression using the four card values.");
        }

        ExpressionParser parser = new ExpressionParser(expression);
        final double value;
        try {
            value = parser.parse();
        } catch (IllegalArgumentException exception) {
            return VerificationResult.failure(exception.getMessage());
        }

        if (parser.getValues().size() != CARDS_PER_ROUND) {
            return VerificationResult.failure("Use each of the four card values exactly once.");
        }

        List<Integer> expectedValues = new ArrayList<>();
        for (Card card : cards) {
            expectedValues.add(card.getValue());
        }
        List<Integer> enteredValues = new ArrayList<>(parser.getValues());
        Collections.sort(expectedValues);
        Collections.sort(enteredValues);
        if (!expectedValues.equals(enteredValues)) {
            return VerificationResult.failure("The expression must use the four displayed card values.");
        }

        if (Math.abs(value - 24.0) > RESULT_TOLERANCE) {
            return VerificationResult.failure("The expression does not evaluate to 24.");
        }

        return VerificationResult.success();
    }

    public static final class VerificationResult {
        private final boolean correct;
        private final String message;

        private VerificationResult(boolean correct, String message) {
            this.correct = correct;
            this.message = message;
        }

        private static VerificationResult success() {
            return new VerificationResult(true, "Correct! The expression evaluates to 24.");
        }

        private static VerificationResult failure(String message) {
            return new VerificationResult(false, message);
        }

        public boolean isCorrect() {
            return correct;
        }

        public String getMessage() {
            return message;
        }
    }

    private static final class ExpressionParser {
        private final String expression;
        private final List<Integer> values = new ArrayList<>();
        private int position;

        private ExpressionParser(String expression) {
            this.expression = expression;
        }

        private double parse() {
            double result = parseExpression();
            skipWhitespace();
            if (position != expression.length()) {
                throw error("Unexpected character '" + expression.charAt(position) + "'.");
            }
            return result;
        }

        private double parseExpression() {
            double result = parseTerm();
            while (true) {
                skipWhitespace();
                if (match('+')) {
                    result += parseTerm();
                } else if (match('-')) {
                    result -= parseTerm();
                } else {
                    return result;
                }
            }
        }

        private double parseTerm() {
            double result = parseFactor();
            while (true) {
                skipWhitespace();
                if (match('*')) {
                    result *= parseFactor();
                } else if (match('/')) {
                    double divisor = parseFactor();
                    if (divisor == 0.0) {
                        throw error("Division by zero is not allowed.");
                    }
                    result /= divisor;
                } else {
                    return result;
                }
            }
        }

        private double parseFactor() {
            skipWhitespace();
            if (match('(')) {
                double result = parseExpression();
                skipWhitespace();
                if (!match(')')) {
                    throw error("Missing closing parenthesis.");
                }
                return result;
            }

            if (position >= expression.length() || !Character.isDigit(expression.charAt(position))) {
                throw error("Expected a card value or an opening parenthesis.");
            }

            int start = position;
            while (position < expression.length() && Character.isDigit(expression.charAt(position))) {
                position++;
            }

            final int value;
            try {
                value = Integer.parseInt(expression.substring(start, position));
            } catch (NumberFormatException exception) {
                throw error("Card values must be whole numbers.");
            }
            values.add(value);
            return value;
        }

        private boolean match(char expected) {
            if (position < expression.length() && expression.charAt(position) == expected) {
                position++;
                return true;
            }
            return false;
        }

        private void skipWhitespace() {
            while (position < expression.length() && Character.isWhitespace(expression.charAt(position))) {
                position++;
            }
        }

        private IllegalArgumentException error(String message) {
            return new IllegalArgumentException(message);
        }

        private List<Integer> getValues() {
            return values;
        }
    }
}
