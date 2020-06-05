package com.example.bugsgame.gamelogik;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bugsgame.R;
import com.example.bugsgame.activitys.GameStarter;
import com.example.bugsgame.activitys.MainActivity;
import com.example.bugsgame.model.Bug;
import com.example.bugsgame.model.Human;
import com.example.bugsgame.tools.BugsType;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    public static int maxX = 20;
    public static int maxY = 32;
    public static float unitW = 0;
    public static float unitH = 0;
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private ArrayList<Bug> bugs = new ArrayList<>();
    private Human human;
    private Context context;
    private int currenttick = 0;
    private final int NEWBUGSPAWNTIMER = 25;

    public  ArrayList<Bug> chosedBugs = new ArrayList<>();
    public BugsType currentBugtype = null;

    private float previewX;
    private float previewY;

    public GameView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameRunning) {
            draw();
            control();
            update();
            checkifnewBug();
            control();
            checkCollision();
            control();
            checkTouch();
        }
    }
    private void checkTouch() {
        if(previewY != GameStarter.TouchY || previewX != GameStarter.TouchX)
        {
            for(Bug b:bugs)
            {
                previewX = GameStarter.TouchX;
                previewY = GameStarter.TouchY;
                if(b.checktouchColission(GameStarter.TouchX,GameStarter.TouchY))
                {
                    if(b.bugsType == currentBugtype || currentBugtype == null)
                    {
                        for(Bug chooseb:chosedBugs)
                        {
                            if(chooseb.equals(b))
                            {
                                return;
                            }
                        }
                        Log.d(MainActivity.TAG, String.valueOf(b.bugsType));
                        chosedBugs.add(b);
                        Log.d(MainActivity.TAG, String.valueOf(chosedBugs.size()));
                        if(chosedBugs.size() == 3)
                        {
                            removeBugs();
                        }
                    }
                    else
                    {
                        Log.d(MainActivity.TAG, String.valueOf("else"));
                        chosedBugs.clear();
                        chosedBugs.add(b);
                    }
                    currentBugtype = b.bugsType;
                }
            }
        }
    }

    private void removeBugs() {
        for(Bug b:chosedBugs)
        {
            bugs.remove(b);
        }
        chosedBugs.clear();
        currentBugtype = null;
    }

    private void checkCollision() {
        for(Bug b:bugs)
        {
           if( b.isCollision(human.x,human.y,human.size))gameRunning=false;
        }
    }
    private void checkifnewBug() {
        if (currenttick == NEWBUGSPAWNTIMER) {
            currenttick = 0;
            bugs.add(new Bug(context));
        }
        currenttick++;
    }
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            if (firstTime) {
                unitW = surfaceHolder.getSurfaceFrame().width() / maxX;
                unitH = surfaceHolder.getSurfaceFrame().height() / maxY;
                bugs.add(new Bug(context));
                human = new Human(context);
                firstTime = false;
            }
            canvas = surfaceHolder.lockCanvas();

            Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background3);

            Bitmap bitmap = Bitmap.createScaledBitmap(
                    cBitmap, surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), false);
            canvas.drawBitmap(bitmap, 0, 0, null);

            human.drow(paint, canvas);
            for (Bug b : bugs) {
                b.drow(paint, canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void update() {
        for (Bug b : bugs) {
            b.update(human.x, human.y);
        }
    }
    private void control() {
        try {
            gameThread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
