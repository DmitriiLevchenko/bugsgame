package com.example.bugsgame.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.bugsgame.R;
import com.example.bugsgame.gamelogik.GameView;

public class Human extends ViewObj {
    public Human(Context context)
    {
        GetKoordinates();
        ChooseBitmapId();
        init(context);
    }
    public void GetKoordinates() {
        size = 7;
        y =GameView.maxY - size+1;
        x = GameView.maxX - size;
    }
    public void ChooseBitmapId()
    {
        bitmapId = R.drawable.character;
    }
    public boolean isCollision(float humanX, float humanY, float shipSize) {
        return !(((x+size) < humanX)||(x > (humanX+shipSize))||((y+size) < humanY)||(y > (humanY+shipSize)));
    }
}
