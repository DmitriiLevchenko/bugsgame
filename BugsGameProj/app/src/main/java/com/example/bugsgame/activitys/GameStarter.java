package com.example.bugsgame.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bugsgame.R;
import com.example.bugsgame.gamelogik.GameView;
import com.example.bugsgame.model.Bug;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameStarter extends AppCompatActivity implements View.OnTouchListener {
    public static float TouchX;
    public static float TouchY;
    private boolean isshow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_starter);
        InitsializateComponents();
    }

    private void InitsializateComponents() {
        GameView gameView = new GameView(this);
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setOnTouchListener(this);
        gameLayout.addView(gameView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        TouchX = event.getX();
        TouchY = event.getY();
        //Log.d(MainActivity.TAG, String.valueOf(event.getX() + event.getY()));
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
