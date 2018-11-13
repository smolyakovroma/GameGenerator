package com.romansmolakov.solgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    private ViewGroup mainLayout;
    private CardView openCards, baseHearts, baseSpades, stackCards;
    private CardView stack1, stack2, stack3, stack4, stack5, stack6, stack7;
    private CardView[] stacks = {stack1, stack2, stack3, stack4, stack5, stack6, stack7};
    private ImageView ivBack, ivSettings, ivNewGame;

    private int xDelta;
    private int yDelta;

    private RelativeLayout.LayoutParams openCardsParams, baseHeartsParams,baseSpadesParams;
    private Set<View> openCardsStack = new LinkedHashSet<>();
    private Set<View> baseHeartsStack = new LinkedHashSet<>();
    private Set<View> baseSpadesStack = new LinkedHashSet<>();

    private Set<View> stackSet1 = new LinkedHashSet<>();
    private Set<View> stackSet2 = new LinkedHashSet<>();
    private Set<View> stackSet3 = new LinkedHashSet<>();
    private Set<View> stackSet4 = new LinkedHashSet<>();
    private Set<View> stackSet5 = new LinkedHashSet<>();
    private Set<View> stackSet6 = new LinkedHashSet<>();
    private Set<View> stackSet7 = new LinkedHashSet<>();


    private LinkedList<Pair<View, Integer>> history = new LinkedList<>();
    private LinkedList<Card> list = new LinkedList<>();
    Drawable[]  back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        back = new Drawable[]{getResources().getDrawable(R.drawable.spades_q), getResources().getDrawable(R.drawable.hearts_k), getResources().getDrawable(R.drawable.diamonds_k),
                getResources().getDrawable(R.drawable.diamonds_q),getResources().getDrawable(R.drawable.hearts_j)};

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        openCards = (CardView) findViewById(R.id.openCards);
        baseHearts = (CardView) findViewById(R.id.baseHearts);
        baseSpades = (CardView) findViewById(R.id.baseSpades);
        stackCards = (CardView) findViewById(R.id.stackCards);

        stack1 = (CardView) findViewById(R.id.stack1);
        stack2 = (CardView) findViewById(R.id.stack2);
        stack3 = (CardView) findViewById(R.id.stack3);
        stack4 = (CardView) findViewById(R.id.stack4);
        stack5 = (CardView) findViewById(R.id.stack5);
        stack6 = (CardView) findViewById(R.id.stack6);
        stack7 = (CardView) findViewById(R.id.stack7);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);
        ivNewGame = (ImageView) findViewById(R.id.ivNewGame);
//        blueCard.setOnTouchListener(onTouchListener());
        openCardsParams = (RelativeLayout.LayoutParams) openCards
                .getLayoutParams();
        baseHeartsParams = (RelativeLayout.LayoutParams) baseHearts
                .getLayoutParams();
        baseSpadesParams = (RelativeLayout.LayoutParams) baseSpades
                .getLayoutParams();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!history.isEmpty()) {
                    View card = history.getLast().first;
                    //animation

                    Integer stack = history.getLast().second;
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                            card.getLayoutParams();
                    if (stack == 1) {
                        lParams.topMargin = openCardsParams.topMargin + (Constants.TOP_MARGIN * openCardsStack.size());
                        lParams.leftMargin = openCardsParams.leftMargin;
                        openCardsStack.add(card);
                        baseHeartsStack.remove(card);
                        baseSpadesStack.remove(card);
                    } else if (stack == 2) {
                        lParams.topMargin = baseHeartsParams.topMargin + (Constants.TOP_MARGIN * baseHeartsStack.size());
                        lParams.leftMargin = baseHeartsParams.leftMargin;
                        baseHeartsStack.add(card);
                        openCardsStack.remove(card);
                        baseSpadesStack.remove(card);
                    } else if (stack == 3) {
                        lParams.topMargin = baseSpadesParams.topMargin + (Constants.TOP_MARGIN * baseSpadesStack.size());
                        lParams.leftMargin = baseSpadesParams.leftMargin;
                        baseHeartsStack.remove(card);
                        openCardsStack.remove(card);
                        baseSpadesStack.add(card);
                    }
                    lParams.rightMargin = 0;
                    lParams.bottomMargin = 0;
                    card.setLayoutParams(lParams);
                    mainLayout.invalidate();

                    updateElevation(openCardsStack);
                    updateElevation(baseHeartsStack);
                    updateElevation(baseSpadesStack);
                    lastView(openCardsStack).setOnTouchListener(onTouchListener());
                    lastView(baseHeartsStack).setOnTouchListener(onTouchListener());
                    lastView(baseSpadesStack).setOnTouchListener(onTouchListener());
                    history.removeLast();
                }
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ivNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();

//                CardView card = new CardView(MainActivity.this);
//
//                for (Card c : list) {
//                    card = new CardView(MainActivity.this);
//                    card.setTag(c);
//                    blueStack.add(card);
//                    newCard(card, blueCardParams.topMargin, blueCardParams.leftMargin);
//                }

//                card.setOnTouchListener(onTouchListener());
            }
        });

        stackCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!list.isEmpty()) {
                    CardView card = new CardView(MainActivity2.this);
                    card.setTag(list.removeLast());
                    openCardsStack.add(card);
                    newCard(card, openCardsParams.topMargin, openCardsParams.leftMargin);
                    updateElevation(openCardsStack);
                    card.setOnTouchListener(onTouchListener());

                }
            }
        });

        newGame();

    }

    int k = 0;
    private void newCard(CardView card, int topMargin, int leftMargin){
        card.setUseCompatPadding(true);
        RelativeLayout.LayoutParams lpCard = new RelativeLayout.LayoutParams(openCardsParams.width, openCardsParams.height);
        lpCard.leftMargin = leftMargin;

        lpCard.topMargin = topMargin + (Constants.TOP_MARGIN * (openCardsStack.size() - 1));
        card.setCardElevation(openCards.getCardElevation());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        ImageView image = new ImageView(card.getContext());
//        card.setBackgroundColor(getResources().getColor(android.R.color.black));
        image.setPadding(5,0,5,0);
        image.setLayoutParams(params);
        image.setImageDrawable(Utility.getByCard((Card) card.getTag(), card.getContext()));

        card.addView(image);


        mainLayout.addView(card, lpCard);
    }


    private View lastView(Set<View> list) {
        View result = new View(this);
        for (View view : list) {
            result = view;
        }
        return result;
    }

    private void updateElevation(Set<View> list) {
        int level = 0;
        for (View view : list) {
            ViewCompat.setElevation(view, ++level);
            view.setOnTouchListener(null);
        }
    }

    private Integer getStack(View view) {
        if (openCardsStack.contains(view)) return 1;
        if (baseHeartsStack.contains(view)) return 2;
        if (baseSpadesStack.contains(view)) return 3;
        return 0;
    }

    private void newGame(){

        for (Lear lear : Lear.values()) {
            for (int i = 1; i < 3; i++) {//..13
                list.add(new Card(lear, i));
            }
        }
        //
        Collections.shuffle(list);
//        System.out.println(list);
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

//                ViewCompat.setTranslationZ(view, 1f);
//                mainLayout.bringChildToFront(view);
//                mainLayout.invalidate();
                boolean onStack = false;
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                        view.getLayoutParams();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
//                        ViewCompat.setElevation(view, 100f);
//                        Log.d("card", "Elev 1");
//                        view.setElevation(view.getElevation() + 1f);
//                        Log.d("card", String.valueOf(ViewCompat.getTranslationZ(view)));
//
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (!openCardsStack.contains(view) && openCardsParams.leftMargin == lParams.leftMargin && openCardsParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * openCardsStack.size())
                                && Utility.stackingCard((Card) lastView(openCardsStack).getTag(), (Card) view.getTag())) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            openCardsStack.add(view);
                            baseSpadesStack.remove(view);
                            baseHeartsStack.remove(view);
                            Log.d("card", "blue stock");
                        } else if (!baseHeartsStack.contains(view) && baseHeartsParams.leftMargin == lParams.leftMargin && baseHeartsParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * baseHeartsStack.size())
                                && Utility.stackingCard((Card) lastView(baseHeartsStack).getTag(), (Card) view.getTag())) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            openCardsStack.remove(view);
                            baseSpadesStack.remove(view);
                            baseHeartsStack.add(view);
//                            Log.d("card", "saved history " + historicalPosition.leftMargin);
                            Log.d("card", "green stock");
                        } else if (!baseSpadesStack.contains(view) && baseSpadesParams.leftMargin == lParams.leftMargin && baseSpadesParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * baseSpadesStack.size())
                                && Utility.stackingCard((Card) lastView(baseSpadesStack).getTag(), (Card) view.getTag())) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            openCardsStack.remove(view);
                            baseHeartsStack.remove(view);
                            baseSpadesStack.add(view);
//                            Log.d("card", "saved history " + historicalPosition.leftMargin);
                            Log.d("card", "red stock");
                        } else {
                            Log.d("card", "on back");
                            if (openCardsStack.contains(view)) {
                                lParams.topMargin = openCardsParams.topMargin + (Constants.TOP_MARGIN * (openCardsStack.size() - 1));
                                lParams.leftMargin = openCardsParams.leftMargin;
                            } else if (baseHeartsStack.contains(view)) {
                                lParams.topMargin = baseHeartsParams.topMargin + (Constants.TOP_MARGIN * (baseHeartsStack.size() - 1));
                                lParams.leftMargin = baseHeartsParams.leftMargin;
                            } else if (baseSpadesStack.contains(view)) {
                                lParams.topMargin = baseSpadesParams.topMargin + (Constants.TOP_MARGIN * baseSpadesStack.size());
                                lParams.leftMargin = baseSpadesParams.leftMargin;
                            }
//                            lParams.topMargin = backPosition.first;
//                            lParams.leftMargin = backPosition.second;
                            view.setLayoutParams(lParams);
                        }
                        updateElevation(openCardsStack);
                        updateElevation(baseHeartsStack);
                        updateElevation(baseSpadesStack);
                        lastView(openCardsStack).setOnTouchListener(onTouchListener());
                        lastView(baseHeartsStack).setOnTouchListener(onTouchListener());
                        lastView(baseSpadesStack).setOnTouchListener(onTouchListener());
                        break;

                    case MotionEvent.ACTION_MOVE:
//                        Log.d("card", "move");
                        ViewCompat.setElevation(view, 100f);
                        lParams.leftMargin = x - xDelta;
                        lParams.topMargin = y - yDelta;
                        if (!openCardsStack.contains(view) && openCardsParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & openCardsParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && openCardsParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & openCardsParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * openCardsStack.size()) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            if (Utility.stackingCard((Card) lastView(openCardsStack).getTag(), (Card) view.getTag())) {
                                lParams.leftMargin = openCardsParams.leftMargin;
                                lParams.topMargin = openCardsParams.topMargin + (Constants.TOP_MARGIN * openCardsStack.size());
                            }
                        }

                        if (!baseHeartsStack.contains(view) && baseHeartsParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & baseHeartsParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && baseHeartsParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & baseHeartsParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * baseHeartsStack.size()) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            if (Utility.stackingCard((Card) lastView(baseHeartsStack).getTag(), (Card) view.getTag())) {
                                lParams.leftMargin = baseHeartsParams.leftMargin;
                                lParams.topMargin = baseHeartsParams.topMargin + (Constants.TOP_MARGIN * baseHeartsStack.size());
                            }

                        }

                        if (!baseSpadesStack.contains(view) && baseSpadesParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & baseSpadesParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && baseSpadesParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & baseSpadesParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * (baseSpadesStack.size() + 1)) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            if (Utility.stackingCard((Card) lastView(baseSpadesStack).getTag(), (Card) view.getTag())) {
                                lParams.leftMargin = baseSpadesParams.leftMargin;
                                lParams.topMargin = baseSpadesParams.topMargin + (Constants.TOP_MARGIN * baseSpadesStack.size());
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
