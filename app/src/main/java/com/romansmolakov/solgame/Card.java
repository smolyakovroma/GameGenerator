package com.romansmolakov.solgame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class Card {
    private CardView cardView;
    private float xDelta;
    private float yDelta;
    private String name;
    private float cardElevation;

    public Card(CardView cardView, float xDelta, float yDelta, String name, float cardElevation) {
        this.cardView = cardView;
        this.cardElevation = cardElevation;
//        this.cardView.setCardElevation(1f);
//        this.cardView.setBackgroundColor(cardView.getContext().getResources().getColor(android.R.color.holo_green_light));
//        this.cardView.setUseCompatPadding(true);
//        this.cardView.setRadius(40.0f);

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );


//        this.cardView.setLayoutParams(params);

        // Set CardView corner radius
//        this.cardView.setRadius(30);
        this.cardView.setUseCompatPadding(true);
        // Set cardView content padding
//        this.cardView.setContentPadding(15, 15, 15, 15);

        // Set a background color for CardView
        this.cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(android.R.color.holo_green_light));

//        // Set the CardView maximum elevation
//        this.cardView.setMaxCardElevation(15);

        // Set CardView elevation
        this.cardView.setCardElevation(this.cardElevation);

        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(cardView.getContext());
        tv.setLayoutParams(params);
        tv.setText(name);
        tv.setBackgroundColor(Color.RED);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//        tv.setGravity(Gravity.CENTER);
//        tv.setTextAlignment(2);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv.setTextColor(Color.WHITE);


        // Put the TextView in CardView
        this.cardView.addView(tv);
        this.cardView.setTag(this);
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.name = name;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public float getxDelta() {
        return xDelta;
    }

    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
    }

    public float getyDelta() {
        return yDelta;
    }

    public void setyDelta(float yDelta) {
        this.yDelta = yDelta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name);
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
