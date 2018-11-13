package com.romansmolakov.solgame;

import android.view.View;

public class RulesStack implements Rules {
    @Override
    public boolean check(Stack stack, View view) {
        Card secondCard = (Card) view.getTag();
        if(secondCard==null) return false;
        if(stack.getList().isEmpty()){
            return secondCard.getRank() == 13;
        }else{

            Card firstCard = (Card) stack.getList().getLast().getTag();
            if (firstCard != null) {
                if (((firstCard.getLear() == Lear.SPADES || firstCard.getLear() == Lear.CLUBS) && (secondCard.getLear() == Lear.HEARTS || secondCard.getLear() == Lear.DIAMONDS))
                        || ((secondCard.getLear() == Lear.SPADES || secondCard.getLear() == Lear.CLUBS) && (firstCard.getLear() == Lear.HEARTS || firstCard.getLear() == Lear.DIAMONDS))) {
                    if (firstCard.getRank() == secondCard.getRank() + 1) return true;
                }
            }
        }
        return false;
    }
}
