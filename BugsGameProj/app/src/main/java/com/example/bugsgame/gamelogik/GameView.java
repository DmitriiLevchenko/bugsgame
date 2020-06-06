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
    public static boolean gameRunning = true;
    public static boolean OverRunning = true;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private ArrayList<Bug> bugs = new ArrayList<>();
    private Human human;
    private Context context;
    private int currenttick = 0;
    private  int currentmesagetick = 0;
    private int MESSAGEINTERVAL = 30;
    private final int NEWBUGSPAWNTIMER = 12;

    public  ArrayList<Bug> chosedBugs = new ArrayList<>();
    public BugsType currentBugtype = null;

    private float previewX;
    private float previewY;
    private boolean isdel = false;

    public GameView(Context context) {
        super(context);
        gameRunning = true;
        OverRunning = true;
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
            checkCollision();
            control();
            checkTouch();
        }
        while(OverRunning)
        {
            gameOver();
            control();
        }

    }



    private boolean checkTouch() {
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
                            if(chooseb == b)
                            {
                                return false;
                            }
                        }
                        b.ischecked = true;
                        chosedBugs.add(b);
                        if(chosedBugs.size() == 3)
                        {
                            isdel = true;
                        }
                    }
                    else
                    {
                        for(Bug bug:chosedBugs)
                        {
                            bug.ischecked = false;
                        }
                        chosedBugs.clear();
                        b.ischecked = true;
                        chosedBugs.add(b);
                    }
                    currentBugtype = b.bugsType;
                }
            }
            if(isdel)
            {
                currentBugtype = null;
                isdel = false;
                removeBugs();
            }
        }
        return true;
    }

    private void removeBugs() {
        for(Bug b:chosedBugs)
        {
            b.ischecked = false;
            bugs.remove(b);
        }
        chosedBugs.clear();
        currentBugtype = null;
    }

    private void checkCollision() {
        for(Bug b:bugs)
        {
           if( b.isCollision(human.x,human.y,human.size))
               gameRunning=false;


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
            checkmessage();
            human.drow(paint, canvas);
            for (Bug b : bugs) {
                b.drow(paint, canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void update() {
        for (Bug b : bugs) {
            b.update();
        }
    }
    private void control() {
        try {
            gameThread.sleep(18);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void checkmessage()
    {
        if(currentmesagetick < MESSAGEINTERVAL)
        {
            currentmesagetick++;
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(40);
            canvas.drawText(" Catch the bugs. Combine 3 beetles of ",surfaceHolder.getSurfaceFrame().width()/2, 150, paint);
            canvas.drawText(" the same color. Tap beetle to select",surfaceHolder.getSurfaceFrame().width()/2, 200, paint);
        }
    }
    private void gameOver() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background3);

            Bitmap bitmap = Bitmap.createScaledBitmap(
                    cBitmap, surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), false);
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.drawText("Bug got to you, game over. Try it again. ", surfaceHolder.getSurfaceFrame().width() / 2, 300, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
