package com.example.bugsgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.bugsgame.R;
import com.example.bugsgame.gamelogik.GameView;

public class ViewObj {
    public float x = -1;
    public float y = -1;
    public float size;
    protected float speed;
    protected int bitmapId;
    protected Bitmap bitmap;

    void init(Context context) { // сжимаем картинку до нужных размеров
//        Matrix matrix = new Matrix();
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameView.unitW), (int)(size * GameView.unitH),false);
//        bitmap = Bitmap.createBitmap(cBitmap,(int)x,(int)y,(int)(size * GameView.unitW), (int)(size * GameView.unitH),matrix,true);
//                cBitmap.recycle();
    }
    public void drow(Paint paint, Canvas canvas){
        canvas.drawBitmap(bitmap, x* GameView.unitW, y* GameView.unitH, paint);
    }
}
