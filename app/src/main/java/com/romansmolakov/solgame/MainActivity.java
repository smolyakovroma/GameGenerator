package com.romansmolakov.solgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mainLayout;
    private CardView blueCard, greenCard, redCard;
    private ImageView ivBack, ivSettings;

    private int xDelta;
    private int yDelta;

    private RelativeLayout.LayoutParams blueCardParams, greenCardParams, redCardParams;
    private Set<View> blueStack = new LinkedHashSet<>();
    private Set<View> greenStack = new LinkedHashSet<>();
    private Set<View> redStack = new LinkedHashSet<>();
    private LinkedList<Pair<View, Integer>> history = new LinkedList<>();

    Drawable[]  back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        back = new Drawable[]{getResources().getDrawable(R.drawable.spades_q), getResources().getDrawable(R.drawable.hearts_k), getResources().getDrawable(R.drawable.diamons_k),
                getResources().getDrawable(R.drawable.diamons_q),getResources().getDrawable(R.drawable.hearts_j)};

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        blueCard = (CardView) findViewById(R.id.blueCard);
        greenCard = (CardView) findViewById(R.id.greenCard);
        redCard = (CardView) findViewById(R.id.redCard);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);
//        blueCard.setOnTouchListener(onTouchListener());
        blueCardParams = (RelativeLayout.LayoutParams) blueCard
                .getLayoutParams();
        greenCardParams = (RelativeLayout.LayoutParams) greenCard
                .getLayoutParams();
        redCardParams = (RelativeLayout.LayoutParams) redCard
                .getLayoutParams();

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        Collections.shuffle(list);
//        System.out.println(list);

        CardView card = new CardView(this);
        for (int i = 0; i < 5; i++) {
            card = new CardView(this);

            blueStack.add(card);
            newCard(card);


        }

        card.setOnTouchListener(onTouchListener());
//        lastViewInBlueStack = card;

//        CardView greenCard = new CardView(this);
//        RelativeLayout.LayoutParams lpCard = new RelativeLayout.LayoutParams(blueCardParams.width, blueCardParams.height);
//        lpCard.leftMargin = 100;
//        lpCard.topMargin = 200;
//        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        leftMarginParams.leftMargin = 50;

//        addCard();

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
                        blueStack.add(card);
                        greenStack.remove(card);
                        redStack.remove(card);
                        lParams.topMargin = blueCardParams.topMargin + (80 * blueStack.size());
                        lParams.leftMargin = blueCardParams.leftMargin;

                    } else if (stack == 2) {
                        greenStack.add(card);
                        blueStack.remove(card);
                        redStack.remove(card);
                        lParams.topMargin = greenCardParams.topMargin + (80 * greenStack.size());
                        lParams.leftMargin = greenCardParams.leftMargin;
                    } else if (stack == 3) {
                        greenStack.remove(card);
                        blueStack.remove(card);
                        redStack.add(card);
                        lParams.topMargin = redCardParams.topMargin + (80 * redStack.size());
                        lParams.leftMargin = redCardParams.leftMargin;
                    }
                    lParams.rightMargin = 0;
                    lParams.bottomMargin = 0;
                    card.setLayoutParams(lParams);
                    mainLayout.invalidate();

                    updateElevation(blueStack);
                    updateElevation(greenStack);
                    updateElevation(redStack);
                    lastView(blueStack).setOnTouchListener(onTouchListener());
                    lastView(greenStack).setOnTouchListener(onTouchListener());
                    lastView(redStack).setOnTouchListener(onTouchListener());
                    history.removeLast();
                }
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    int k = 0;
    private void newCard(CardView card){
        card.setUseCompatPadding(true);
        RelativeLayout.LayoutParams lpCard = new RelativeLayout.LayoutParams(blueCardParams.width, blueCardParams.height);
        lpCard.leftMargin = blueCardParams.leftMargin;

        lpCard.topMargin = blueCardParams.topMargin + (Constants.TOP_MARGIN * blueStack.size());
        card.setCardElevation(blueCard.getCardElevation());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        ImageView image = new ImageView(card.getContext());
//        card.setBackgroundColor(getResources().getColor(android.R.color.black));
        image.setPadding(5,0,5,0);
        image.setLayoutParams(params);
        image.setImageDrawable(back[k++]);

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
        if (blueStack.contains(view)) return 1;
        if (greenStack.contains(view)) return 2;
        if (redStack.contains(view)) return 3;
        return 0;
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
                        if (!blueStack.contains(view) && blueCardParams.leftMargin == lParams.leftMargin && blueCardParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * (blueStack.size() + 1))) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            blueStack.add(view);
                            redStack.remove(view);
                            greenStack.remove(view);
                            Log.d("card", "blue stock");
                        } else if (!greenStack.contains(view) && greenCardParams.leftMargin == lParams.leftMargin && greenCardParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * (greenStack.size() + 1))) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            blueStack.remove(view);
                            redStack.remove(view);
                            greenStack.add(view);
//                            Log.d("card", "saved history " + historicalPosition.leftMargin);
                            Log.d("card", "green stock");
                        } else if (!redStack.contains(view) && redCardParams.leftMargin == lParams.leftMargin && redCardParams.topMargin == lParams.topMargin - (Constants.TOP_MARGIN * (redStack.size() + 1))) {
                            history.add(new Pair<View, Integer>(view, getStack(view)));
                            blueStack.remove(view);
                            greenStack.remove(view);
                            redStack.add(view);
//                            Log.d("card", "saved history " + historicalPosition.leftMargin);
                            Log.d("card", "green stock");
                        } else {
                            Log.d("card", "on back");
                            if (blueStack.contains(view)) {
                                lParams.topMargin = blueCardParams.topMargin + (Constants.TOP_MARGIN * blueStack.size());
                                lParams.leftMargin = blueCardParams.leftMargin;
                            } else if (greenStack.contains(view)) {
                                lParams.topMargin = greenCardParams.topMargin + (Constants.TOP_MARGIN * greenStack.size());
                                lParams.leftMargin = greenCardParams.leftMargin;
                            } else if (redStack.contains(view)) {
                                lParams.topMargin = redCardParams.topMargin + (Constants.TOP_MARGIN * redStack.size());
                                lParams.leftMargin = redCardParams.leftMargin;
                            }
//                            lParams.topMargin = backPosition.first;
//                            lParams.leftMargin = backPosition.second;
                            view.setLayoutParams(lParams);
                        }
                        updateElevation(blueStack);
                        updateElevation(greenStack);
                        updateElevation(redStack);
                        lastView(blueStack).setOnTouchListener(onTouchListener());
                        lastView(greenStack).setOnTouchListener(onTouchListener());
                        lastView(redStack).setOnTouchListener(onTouchListener());
                        break;

                    case MotionEvent.ACTION_MOVE:
//                        Log.d("card", "move");
                        ViewCompat.setElevation(view, 100f);
                        lParams.leftMargin = x - xDelta;
                        lParams.topMargin = y - yDelta;
                        if (!blueStack.contains(view) && blueCardParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & blueCardParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && blueCardParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & blueCardParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * (blueStack.size() + 1)) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            lParams.leftMargin = blueCardParams.leftMargin;
                            lParams.topMargin = blueCardParams.topMargin + (Constants.TOP_MARGIN * (blueStack.size() + 1));


                        }

                        if (!greenStack.contains(view) && greenCardParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & greenCardParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && greenCardParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & greenCardParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * (greenStack.size() + 1)) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            lParams.leftMargin = greenCardParams.leftMargin;
                            lParams.topMargin = greenCardParams.topMargin + (Constants.TOP_MARGIN * (greenStack.size() + 1));

                        }

                        if (!redStack.contains(view) && redCardParams.leftMargin - Constants.RANGE_STACK_VERTICAL <= lParams.leftMargin & redCardParams.leftMargin + Constants.RANGE_STACK_VERTICAL >= lParams.leftMargin
                                && redCardParams.topMargin - Constants.RANGE_STACK_HORIZONTAL <= lParams.topMargin & redCardParams.topMargin + Constants.RANGE_STACK_HORIZONTAL + (Constants.TOP_MARGIN * (redStack.size() + 1)) >= lParams.topMargin) {
//                            Log.d("card", "got card!");
                            lParams.leftMargin = redCardParams.leftMargin;
                            lParams.topMargin = redCardParams.topMargin + (Constants.TOP_MARGIN * (redStack.size() + 1));

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
