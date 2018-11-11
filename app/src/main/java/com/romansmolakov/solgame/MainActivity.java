package com.romansmolakov.solgame;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mainLayout;
    private CardView blueCard, greenCard;
    private ImageView ivBack, ivSettings;

    private int xDelta;
    private int yDelta;

    private HashMap<CardView, Pair<Integer, Integer>> cardViewLayoutParamsHashMap = new HashMap<>();
    private RelativeLayout.LayoutParams blueCardParams, greenCardParams;
    private Set<View> blueStack = new LinkedHashSet<>();
    private View lastViewInBlueStack;
    private Set<View> greenStack = new LinkedHashSet<>();
    private View lastViewInGreenStack;
    private LinkedList<Pair<Integer, Integer>> history = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        blueCard = (CardView) findViewById(R.id.blueCard);
        greenCard = (CardView) findViewById(R.id.greenCard);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);
//        blueCard.setOnTouchListener(onTouchListener());
        blueCardParams = (RelativeLayout.LayoutParams) blueCard
                .getLayoutParams();

        greenCardParams = (RelativeLayout.LayoutParams) greenCard
                .getLayoutParams();
        int[] colors = {getResources().getColor(android.R.color.holo_orange_dark), getResources().getColor(android.R.color.holo_red_light), getResources().getColor(android.R.color.holo_purple),
                getResources().getColor(android.R.color.holo_blue_dark), getResources().getColor(R.color.colorPrimary)};
        CardView card = new CardView(this);
        for (int i = 0; i < 5; i++) {
            card = new CardView(this);
            card.setCardBackgroundColor(colors[i]);
            card.setUseCompatPadding(true);
            RelativeLayout.LayoutParams lpCard = new RelativeLayout.LayoutParams(blueCardParams.width, blueCardParams.height);
            lpCard.leftMargin = blueCardParams.leftMargin;
            blueStack.add(card);
            lpCard.topMargin = blueCardParams.topMargin + (80 * blueStack.size());
            card.setCardElevation(blueCard.getCardElevation());
            cardViewLayoutParamsHashMap.put(card, new Pair<>(lpCard.topMargin, lpCard.leftMargin));
            mainLayout.addView(card, lpCard);

        }

        card.setOnTouchListener(onTouchListener());
        lastViewInBlueStack = card;

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
                if(!history.isEmpty()){
                    if(history.getLast().second == blueCardParams.leftMargin){

                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lastViewInBlueStack
                                .getLayoutParams();

                        lastViewInGreenStack.setOnTouchListener(null);
                        blueStack.remove(lastViewInBlueStack);
                        greenStack.add(lastViewInBlueStack);
                        updateElevation(greenStack);
                        layoutParams.topMargin = greenCardParams.topMargin + (80 * greenStack.size());
                        layoutParams.leftMargin = greenCardParams.leftMargin;
                        lastViewInGreenStack = lastViewInBlueStack;
                        lastViewInBlueStack = lastView(blueStack);

                        lastViewInGreenStack.setOnTouchListener(onTouchListener());
                        if (lastViewInBlueStack != null)
                            lastViewInBlueStack.setOnTouchListener(onTouchListener());
                        if (lastViewInGreenStack != null)
                            lastViewInGreenStack.setOnTouchListener(onTouchListener());
                        lastViewInGreenStack.setLayoutParams(layoutParams);
                        history.removeLast();
                    }else if(history.getLast().second == greenCardParams.leftMargin){
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lastViewInGreenStack
                                .getLayoutParams();

                        lastViewInBlueStack.setOnTouchListener(null);
                        blueStack.add(lastViewInGreenStack);
                        greenStack.remove(lastViewInGreenStack);
                        updateElevation(blueStack);
                        layoutParams.topMargin = blueCardParams.topMargin + (80 * blueStack.size());
                        layoutParams.leftMargin = blueCardParams.leftMargin;
                        lastViewInBlueStack = lastViewInGreenStack;
                        lastViewInGreenStack = lastView(greenStack);

                        lastViewInBlueStack.setOnTouchListener(onTouchListener());
                        if (lastViewInBlueStack != null)
                            lastViewInBlueStack.setOnTouchListener(onTouchListener());
                        if (lastViewInGreenStack != null)
                            lastViewInGreenStack.setOnTouchListener(onTouchListener());
                        lastViewInBlueStack.setLayoutParams(layoutParams);
                        history.removeLast();
                    }
                    mainLayout.invalidate();
//                    if(history.size()==1) history.clear();
                }
            }
        });
    }


    private View lastView(Set<View> list){
        View result = new View(this);
        for (View view : list) {
            result = view;
        }
        return result;
    }

    private void updateElevation(Set<View> list){
        int level = 0;
        for (View view : list) {
            ViewCompat.setElevation(view, ++level);
        }
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



                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
//                        ViewCompat.setElevation(view, 100f);
//                        Log.d("card", "Elev 1");
//                        view.setElevation(view.getElevation() + 1f);
//                        Log.d("card", String.valueOf(ViewCompat.getTranslationZ(view)));
//
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
//                        Log.d("card", "up");
//                        ViewCompat.setTranslationZ(view, 0f);
//                        ViewCompat.setElevation(view, 0f);
                        if(!onStack && cardViewLayoutParamsHashMap.containsKey(view)){
                            RelativeLayout.LayoutParams backParams = (RelativeLayout.LayoutParams)
                                    view.getLayoutParams();
                            backParams.topMargin = cardViewLayoutParamsHashMap.get(view).first;
                            backParams.leftMargin = cardViewLayoutParamsHashMap.get(view).second;
                            view.setLayoutParams(backParams);
                            updateElevation(blueStack);
                            updateElevation(greenStack);
//                            ViewCompat.setElevation(view, cardViewLayoutParamsHashMap.size());

                        }
                        Log.d("card", "UP");
                        break;

                    case MotionEvent.ACTION_MOVE:
//                        Log.d("card", "move");
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        ViewCompat.setElevation(view, 100f);
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
//                        if(!blueStack.contains(view)) {
                            if (blueCardParams.leftMargin - 30 <= layoutParams.leftMargin & blueCardParams.leftMargin + 30 >= layoutParams.leftMargin
                                    && blueCardParams.topMargin - 60 <= layoutParams.topMargin & blueCardParams.topMargin + 60 + (80 * blueStack.size()) >= layoutParams.topMargin) {
//                            Log.d("card", "got card!");
                                layoutParams.leftMargin = blueCardParams.leftMargin;
                                lastView(blueStack).setOnTouchListener(null);
                                blueStack.add(view);
                                greenStack.remove(view);
                                updateElevation(blueStack);
                                layoutParams.topMargin = blueCardParams.topMargin + (80 * blueStack.size());


                                onStack = true;

                                lastViewInBlueStack = view;
                                lastViewInGreenStack = lastView(greenStack);
                                lastViewInGreenStack.setOnTouchListener(onTouchListener());
                                if (lastViewInBlueStack != null)
                                    lastViewInBlueStack.setOnTouchListener(onTouchListener());
                                if (lastViewInGreenStack != null)
                                    lastViewInGreenStack.setOnTouchListener(onTouchListener());
                            }
//                        }
//                        if(!greenStack.contains(view)) {
                            if (greenCardParams.leftMargin - 30 <= layoutParams.leftMargin & greenCardParams.leftMargin + 30 >= layoutParams.leftMargin
                                    && greenCardParams.topMargin - 60 <= layoutParams.topMargin & greenCardParams.topMargin + 60 + (80 * greenStack.size()) >= layoutParams.topMargin) {
//                            Log.d("card", "got card!");
                                layoutParams.leftMargin = greenCardParams.leftMargin;
                                blueStack.remove(view);
                                lastView(greenStack).setOnTouchListener(null);
                                greenStack.add(view);
                                updateElevation(greenStack);
                                layoutParams.topMargin = greenCardParams.topMargin + (80 * greenStack.size());
                                onStack = true;
                                lastViewInGreenStack = view;
                                lastViewInBlueStack = lastView(blueStack);
                                if (lastViewInBlueStack != null)
                                    lastViewInBlueStack.setOnTouchListener(onTouchListener());
                                if (lastViewInGreenStack != null)
                                    lastViewInGreenStack.setOnTouchListener(onTouchListener());
                            }
//                        }
                        if(onStack){
                            cardViewLayoutParamsHashMap.put((CardView) view, new Pair<>(layoutParams.topMargin, layoutParams.leftMargin));
                            if(history.isEmpty() ||  (history.getLast().first != layoutParams.topMargin && history.getLast().second != layoutParams.leftMargin)) {
                                history.add(new Pair<>(layoutParams.topMargin, layoutParams.leftMargin));
                                Log.d("card", "" + layoutParams.topMargin);
                            }

                        }


                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
}
