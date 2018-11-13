package com.romansmolakov.solgame;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Stack {

    private CardView stack;
    private RelativeLayout.LayoutParams stackParams;
    private LinkedList<View> list = new LinkedList<>();
    private View.OnTouchListener onTouchListener;
    private boolean backInHistory = true;

    private Rules rules;

    private int verticalMargin, horizontalMargin;

    public Stack(CardView stack) {
        this.stack = stack;
        this.stackParams = (RelativeLayout.LayoutParams) stack
                .getLayoutParams();
        this.rules = new Rules() {
            @Override
            public boolean check(Stack stack, View view) {
                System.out.println("false");
                return false;
            }
        };
    }

    public void setBackInHistory(boolean backInHistory) {
        this.backInHistory = backInHistory;
    }

    public Stack setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
        return this;
    }

    public Stack setHorizontalMargin(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
        return this;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public RelativeLayout.LayoutParams getStackParams() {
        return stackParams;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public LinkedList<View> getList() {
        return list;
    }

    public void addCard(View card) {
        if (list.isEmpty()) {
            list.add(card);
            list.getLast().setOnTouchListener(onTouchListener);
        } else if (list.getLast() != card) {
            list.getLast().setOnTouchListener(null);
            list.add(card);
            list.getLast().setOnTouchListener(onTouchListener);
        }
        ViewCompat.setElevation(list.getLast(), list.size());

    }

    public void removedCard(View card) {
        list.remove(card);
        if (!list.isEmpty()) {
            list.getLast().setOnTouchListener(onTouchListener);
        }
    }

    public int getVerticalShift() {
        return stackParams.topMargin + verticalMargin * list.size();
    }

    public int getHorizontalShift() {
        return stackParams.leftMargin + horizontalMargin * list.size();
    }

    public Stack setRules(Rules rules) {
        this.rules = rules;
        return this;
    }

    public boolean checkRules(View view){
        return rules.check(this, view);
    }
    //    private View lastView() {
//        return stackSet.
//    }
//
//    private void updateElevation(Set<View> list) {
//        int level = 0;
//        for (View view : list) {
//            ViewCompat.setElevation(view, ++level);
//            view.setOnTouchListener(null);
//        }
//    }
//
//    private Integer getStack(View view) {
//        if (openCardsStack.contains(view)) return 1;
//        if (baseHeartsStack.contains(view)) return 2;
//        if (baseSpadesStack.contains(view)) return 3;
//        return 0;
//    }
}
