package com.romansmolakov.solgame;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Utility {

    public static Drawable getByCard(Card card, Context context) {
        if(card.isHidden()) return context.getResources().getDrawable(R.drawable.backcard_classic);
        switch (card.getLear()) {
            case HEARTS:
                return context.getResources().getDrawable(HEARTS_CARD[card.getRank() - 1]);
            case CLUBS:
                return context.getResources().getDrawable(CLUBS_CARD[card.getRank() - 1]);
            case DIAMONDS:
                return context.getResources().getDrawable(DIAMONDS_CARD[card.getRank() - 1]);
            case SPADES:
                return context.getResources().getDrawable(SPADES_CARD[card.getRank() - 1]);
            default:

        }
        return context.getResources().getDrawable(R.drawable.backcard_classic);// вернем рубашку если не указанна масть карты
    }

    public static boolean stackingCard(Card firstCard, Card secondCard) {
        if(firstCard == null && secondCard!=null && secondCard.getRank() == 1) return true;
        if (firstCard != null && secondCard != null) {
            if (((firstCard.getLear() == Lear.SPADES || firstCard.getLear() == Lear.CLUBS) && (secondCard.getLear() == Lear.HEARTS || secondCard.getLear() == Lear.DIAMONDS))
                    || ((secondCard.getLear() == Lear.SPADES || secondCard.getLear() == Lear.CLUBS) && (firstCard.getLear() == Lear.HEARTS || firstCard.getLear() == Lear.DIAMONDS))) {
                if (firstCard.getRank() == secondCard.getRank() - 1) return true;
            }
        }
        return false;
    }

    private static int[] HEARTS_CARD = {R.drawable.hearts_a, R.drawable.hearts_2, R.drawable.hearts_3, R.drawable.hearts_4, R.drawable.hearts_5, R.drawable.hearts_6, R.drawable.hearts_7
            , R.drawable.hearts_8, R.drawable.hearts_9, R.drawable.hearts_10, R.drawable.hearts_j, R.drawable.hearts_q, R.drawable.hearts_k};

    private static int[] CLUBS_CARD = {R.drawable.clubs_a, R.drawable.clubs_2, R.drawable.clubs_3, R.drawable.clubs_4, R.drawable.clubs_5, R.drawable.clubs_6, R.drawable.clubs_7
            , R.drawable.clubs_8, R.drawable.clubs_9, R.drawable.clubs_10, R.drawable.clubs_j, R.drawable.clubs_q, R.drawable.clubs_k};

    private static int[] DIAMONDS_CARD = {R.drawable.diamonds_a, R.drawable.diamonds_2, R.drawable.diamonds_3, R.drawable.diamonds_4, R.drawable.diamonds_5, R.drawable.diamonds_6, R.drawable.diamonds_7
            , R.drawable.diamonds_8, R.drawable.diamonds_9, R.drawable.diamonds_10, R.drawable.diamonds_j, R.drawable.diamonds_q, R.drawable.diamonds_k};

    private static int[] SPADES_CARD = {R.drawable.spades_a, R.drawable.spades_2, R.drawable.spades_3, R.drawable.spades_4, R.drawable.spades_5, R.drawable.spades_6, R.drawable.spades_7
            , R.drawable.spades_8, R.drawable.spades_9, R.drawable.spades_10, R.drawable.spades_j, R.drawable.spades_q, R.drawable.spades_k};
}
