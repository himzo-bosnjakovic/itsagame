package com.hmzo.myfirstgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class FlyingFishView extends View {
    private Bitmap[] fish = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int yellowX, yellowY, yellowSpeed = 12;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 17;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 20;
    private Paint redPaint = new Paint();

    private boolean touch = false;
    private int score;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap[] life = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = getWidth();
        canvasHeight = getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if(fishY < minFishY) {
            fishY = minFishY;
        }
        if(fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;
        if(touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        //YELLOW BALL SPAWN AND MOVEMENT
        yellowX = yellowX - yellowSpeed;
        if(hitBallChecker(yellowX, yellowY)) {
            score = score + 1;
            yellowX = -100;
        }
        if(yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 20, yellowPaint);

        //GREEN BALL SPAWN AND MOVEMENT
        greenX = greenX - greenSpeed;
        if(hitBallChecker(greenX, greenY)) {
            score = score + 3;
            greenX = -100;
        }
        if(greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);

        //RED BALL SPAWN AND MOVEMENT
        redX = redX - redSpeed;
        if(hitBallChecker(redX, redY)) {
            score = 0;
            redX = -100;
        }
        if(redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 25, redPaint);

        //CANVAS DRAW TOPBAR
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        canvas.drawBitmap(life[0], 400, 10, null);
        canvas.drawBitmap(life[0], 475, 10, null);
        canvas.drawBitmap(life[0], 550, 10, null);
    }

    public boolean hitBallChecker(int x, int y) {
        return fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;

            fishSpeed = -22;
        }
        return true;
    }
}
