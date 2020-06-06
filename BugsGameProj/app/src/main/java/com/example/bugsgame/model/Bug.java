package com.example.bugsgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.bugsgame.R;
import com.example.bugsgame.activitys.GameStarter;
import com.example.bugsgame.activitys.MainActivity;
import com.example.bugsgame.gamelogik.GameView;
import com.example.bugsgame.tools.BugsType;

import java.util.Random;

public class Bug extends ViewObj {
    public BugsType bugsType = null;
    public boolean ischecked = false;
    public Context bugcontext;

    public Bug(Context context) {
        this.bugcontext = context;
        GetKoordinates();
        ChooseBitmapId();
        init(context);
    }

    public void Reduild() {
        GetKoordinates();
        ChooseBitmapId();
        init(bugcontext);
    }

    public void GetKoordinates() {
        speed = 0.15f;
        size = 3;
        if (x == -1) {
            x = (int) (1 + Math.random() * (GameView.maxX - size));
        }
        if (y == -1) {
            y = 18;
        }
    }

    public void ChooseBitmapId() {
        if (!ischecked) {
            if (bugsType == null) {
                int choosebitmap = (int) (1 + Math.random() * 3);
                switch (choosebitmap) {
                    case 1:
                        bugsType = BugsType.FIRST;
                        break;
                    case 2:
                        bugsType = BugsType.SECOND;
                        break;
                    case 3:
                        bugsType = BugsType.THIRD;
                        break;
                    case 4:
                        bugsType = BugsType.FOURTH;
                        break;
                }
            }
            switch (bugsType) {
                case FIRST:
                    bitmapId = R.drawable.bug_1;
                    break;
                case SECOND:
                    bitmapId = R.drawable.bug_2;
                    break;
                case THIRD:
                    bitmapId = R.drawable.bug_3;
                    break;
                case FOURTH:
                    bitmapId = R.drawable.bug_4;
                    break;
            }
        } if(ischecked) {
            this.ischecked = false;
            switch (bugsType) {
                case FIRST:
                    bitmapId = R.drawable.bug_1choosetr;
                    break;
                case SECOND:
                    bitmapId = R.drawable.bug_2choosetr;
                    break;
                case THIRD:
                    bitmapId = R.drawable.bug_3choosetr;
                    break;
                case FOURTH:
                    bitmapId = R.drawable.bug_4choose;
                    break;
            }
        }
    }

    public void update() {
        y += speed;
    }

    public void checkRotait() {

    }

    public boolean isCollision(float humanX, float humanY, float humansize) {
        // return !(((x + size / 2-1) < humanX) || (x > (humanX + humansize)) || ((y + size / 2-1) < humanY) || (y > (humanY + humansize)));
        return y + size / 2 > GameView.maxY;
    }

    public boolean checktouchColission(float x, float y) {
        float maxX = (this.x + this.size) * GameView.unitW;
        float minX = (this.x - this.size) * GameView.unitW;
        float maxY = (this.y + this.size) * GameView.unitH;
        float minY = (this.y - this.size) * GameView.unitH;
        if (x < maxX && x > minX && y < maxY && y > minY)
            return true;

        return false;
    }
}
