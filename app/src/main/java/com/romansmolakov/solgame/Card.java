package com.romansmolakov.solgame;

public class Card {

    private Lear lear;
    private int rank; // 2 = 2, 10 = 10, jack = 11, queen = 12, king = 13, ace = 1
    private boolean isHidden = true;

    public Card(Lear lear, int rank) {
        this.lear = lear;
        this.rank = rank;
    }

    public Lear getLear() {
        return lear;
    }

    public void setLear(Lear lear) {
        this.lear = lear;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card("
                + lear +
                "/" + rank +
                ')';
    }
}

enum Lear {

    CLUBS, DIAMONDS, HEARTS, SPADES;
}