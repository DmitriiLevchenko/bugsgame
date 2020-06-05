package com.example.bugsgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.bugsgame.R;
import com.example.bugsgame.activitys.MainActivity;
import com.example.bugsgame.gamelogik.GameView;
import com.example.bugsgame.tools.BugsType;

import java.util.Random;

public class Bug extends ViewObj {
    public BugsType bugsType;

    public Bug(Context context) {
        GetKoordinates();
        ChooseBitmapId();
        init(context);
    }

    public void GetKoordinates() {
        speed = 0.12f;
        size = 3;
        y = 12;
        x = (int) (0 + Math.random() * GameView.maxX);
    }

    public void ChooseBitmapId() {

        int choosebitmap = (int) (1 + Math.random() * 3);
        switch (choosebitmap) {
            case 1:
                bitmapId = R.drawable.bug_1;
                bugsType = BugsType.FIRST;
                break;
            case 2:
                bitmapId = R.drawable.bug_2;
                bugsType = BugsType.SECOND;
                break;
            case 3:
                bitmapId = R.drawable.bug_3;
                bugsType = BugsType.THIRD;
                break;
            case 4:
                bitmapId = R.drawable.bug_4;
                bugsType = BugsType.FOURTH;
                break;
        }
    }

    public void update(float humanx, float humany) {
        if(x < humanx)x+=speed;
        if (x > humanx) x -= speed;
        if (y < humany) y += speed;
        if (y > humany) y -= speed;
    }

    public void checkRotait() {

    }

    public boolean isCollision(float humanX, float humanY, float humansize) {
        return !(((x + size / 2-1) < humanX) || (x > (humanX + humansize)) || ((y + size / 2-1) < humanY) || (y > (humanY + humansize)));
    }
    public boolean checktouchColission(float x, float y) {
        float maxX = (this.x + this.size)*GameView.unitW;
        float minX = (this.x - this.size)*GameView.unitW;
        float maxY = (this.y + this.size)*GameView.unitH;
        float minY = (this.y - this.size)*GameView.unitH;
        if (x < maxX && x > minX && y < maxY && y > minY)
            return true;

        return false;
    }
}
