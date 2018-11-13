package com.romansmolakov.solgame;

import android.view.View;

public class RulesBase implements Rules {
    @Override
    public boolean check(Stack stack, View view) {
        Card secondCard = (Card) view.getTag();
        if (stack.getList().isEmpty()) {
            return secondCard.getRank() == 1;
        } else {
            Card firstCard = (Card) stack.getList().getLast().getTag();
            if (firstCard != null) {
                if (firstCard.getLear() == secondCard.getLear()) {
                    if (firstCard.getRank() == secondCard.getRank() - 1) return true;
                }
            }
        }
        return false;
    }
}
