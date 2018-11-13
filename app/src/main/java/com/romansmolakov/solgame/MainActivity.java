package com.romansmolakov.solgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mainLayout;
    private CardView openCards, baseHearts, baseSpades, baseDiamonds, baseClubs, stackCards;
    private Stack stack1, stack2, stack3, stack4, stack5, stack6, stack7;
    private ImageView ivBack, ivSettings, ivNewGame;
    private Stack[] stacks;
    private Stack[] listStack;
    private int xDelta;
    private int yDelta;


    private LinkedList<Pair<View, Integer>> history = new LinkedList<>();
    private LinkedList<Card> pool = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        openCards = (CardView) findViewById(R.id.openCards);
        baseHearts = (CardView) findViewById(R.id.baseHearts);
        baseSpades = (CardView) findViewById(R.id.baseSpades);
        baseDiamonds = (CardView) findViewById(R.id.baseDiamonds);
        baseClubs = (CardView) findViewById(R.id.baseClubs);
        stackCards = (CardView) findViewById(R.id.stackCards);

        RulesStack rulesStack = new RulesStack();

        stack1 = new Stack((CardView) findViewById(R.id.stack1)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack2 = new Stack((CardView) findViewById(R.id.stack2)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack3 = new Stack((CardView) findViewById(R.id.stack3)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack4 = new Stack((CardView) findViewById(R.id.stack4)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack5 = new Stack((CardView) findViewById(R.id.stack5)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack6 = new Stack((CardView) findViewById(R.id.stack6)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);
        stack7 = new Stack((CardView) findViewById(R.id.stack7)).setVerticalMargin(Constants.TOP_MARGIN).setRules(rulesStack);

        listStack = new Stack[]{stack1, stack2, stack3, stack4, stack5, stack6, stack7};
        RulesBase rulesBase = new RulesBase();

        stacks = new Stack[]{new Stack(openCards), new Stack(stackCards), new Stack(baseHearts).setRules(rulesBase), new Stack(baseSpades).setRules(rulesBase), new Stack(baseDiamonds).setRules(rulesBase)
                , new Stack(baseClubs).setRules(rulesBase), stack1, stack2, stack3, stack4, stack5, stack6, stack7};

        for (Stack stack : stacks) {
            stack.setOnTouchListener(onTouchListener());
        }

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);
        ivNewGame = (ImageView) findViewById(R.id.ivNewGame);


        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ivNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePool();
                stackCards.setVisibility(View.VISIBLE);

            }
        });

        stackCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pool.isEmpty()) {
                    CardView card = new CardView(MainActivity.this);
                    card.setTag(pool.removeLast());
                    ((Card) card.getTag()).setHidden(false);
                    stacks[0].addCard(card);
                    newCard(card, stacks[0]);
                    if (pool.isEmpty()) {
                        stackCards.setVisibility(View.INVISIBLE);
                    }
//                    updateElevation(openCardsStack);

                }
            }
        });

        generatePool();

    }


    private void newCard(CardView card, Stack stack) {

        card.setUseCompatPadding(true);
        RelativeLayout.LayoutParams lpCard = new RelativeLayout.LayoutParams(stack.getStackParams().width, stack.getStackParams().height);
        lpCard.leftMargin = stack.getHorizontalShift();
        lpCard.topMargin = stack.getVerticalShift();


        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        ImageView image = new ImageView(card.getContext());
//        card.setBackgroundColor(getResources().getColor(android.R.color.black));
        image.setPadding(5, 0, 5, 0);
        image.setLayoutParams(p);
        image.setImageDrawable(Utility.getByCard((Card) card.getTag(), card.getContext()));

        card.addView(image);


        mainLayout.addView(card, lpCard);
    }


    private void generatePool() {

        for (Lear lear : Lear.values()) {
            for (int i = 1; i < 14; i++) {//..14
                pool.add(new Card(lear, i));
            }
        }
        int k = 1;
        for (Stack stack : listStack) {
            for (int i = 0; i < k; i++) {
                CardView card = new CardView(MainActivity.this);
                card.setTag(pool.removeLast());
                if(i+1 == k) ((Card) card.getTag()).setHidden(false);
                newCard(card, stack);
                stack.addCard(card);
            }
            k++;
        }


        //
        Collections.shuffle(pool);
//        System.out.println(pool);
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                        view.getLayoutParams();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        Card card = (Card) view.getTag();
                        if (card.isHidden()) {
                            card.setHidden(false);
                            ((ImageView)   ((CardView) view).getChildAt(0)).setImageDrawable(Utility.getByCard((Card) view.getTag(), view.getContext()));
                            break;
                        }
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        boolean moved = false;
                        for (Stack stack : stacks) {
                            if (!stack.getList().contains(view)) {
                                if (stack.getStackParams().leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & stack.getStackParams().leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                        && stack.getStackParams().topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin
                                        && stack.getVerticalShift() + Constants.RANGE_STACK_HORIZONTAL >= lParams.topMargin) {
                                    if (stack.checkRules(view)) {
                                        for (Stack removedStack : stacks) {
                                            removedStack.removedCard(view);
                                        }
                                        stack.addCard(view);
                                        moved = true;
                                    }
                                    break;
                                }
                            }
                        }
                        if (!moved) {
                            for (Stack stack : stacks) {
                                if (stack.getList().contains(view)) {
                                    lParams.leftMargin = stack.getHorizontalShift();
                                    lParams.topMargin = stack.getVerticalShift() - stack.getVerticalMargin();
                                    ViewCompat.setElevation(view, stack.getList().size());
                                }
                            }
                            view.setLayoutParams(lParams);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        ViewCompat.setElevation(view, 100f);
                        lParams.leftMargin = x - xDelta;
                        lParams.topMargin = y - yDelta;
                        for (Stack stack : stacks) {
                            if (!stack.getList().contains(view)) {
                                if (stack.getStackParams().leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & stack.getStackParams().leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                        && stack.getStackParams().topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin
                                        && stack.getVerticalShift() + Constants.RANGE_STACK_HORIZONTAL >= lParams.topMargin) {
                                    if (stack.checkRules(view)) {
                                        lParams.leftMargin = stack.getHorizontalShift();
                                        lParams.topMargin = stack.getVerticalShift();
                                    }
                                }

                            }
                        }


                        lParams.rightMargin = 0;
                        lParams.bottomMargin = 0;
                        view.setLayoutParams(lParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }


}
