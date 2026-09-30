package org.example.arevalo_henry_csc311_playingcards;

import java.util.Objects;

public final class Card {
    public enum Rank {
        ACE("ace", 1),
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("jack", 11),
        QUEEN("queen", 12),
        KING("king", 13);

        private final String imageName;
        private final int value;

        Rank(String imageName, int value) {
            this.imageName = imageName;
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Suit {
        CLUBS("clubs"),
        DIAMONDS("diamonds"),
        HEARTS("hearts"),
        SPADES("spades");

        private final String imageName;

        Suit(String imageName) {
            this.imageName = imageName;
        }
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank, "rank cannot be null");
        this.suit = Objects.requireNonNull(suit, "suit cannot be null");
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return rank.getValue();
    }

    public String getImageResource() {
        return "/PlayingCardsExtracted/png/" + rank.imageName + "_of_" + suit.imageName + ".png";
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name().toLowerCase();
    }
}
