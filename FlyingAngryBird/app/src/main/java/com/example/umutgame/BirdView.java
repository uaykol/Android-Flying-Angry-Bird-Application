package com.example.umutgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BirdView extends View {

    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    private int canvasWidth, canvasHeight;

    private int yellowX, yellowY, yellowSpeed = 10; // SPEED OF YELLOW BAIT
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 30; // SPEED OF GREEN BAIT
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 20; // SPEED OF GREEN BAIT
    private Paint redPaint = new Paint();

    private int score, lifeCounterOfBird;

    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];



    public BirdView(Context context)
    {

        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background1);

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

        birdY = 550;
        score = 0;
        lifeCounterOfBird = 3;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight() * 3;
        birdY = birdY + birdSpeed;

        if(birdY < minBirdY)
        {
            birdY = minBirdY;
        }
        if(birdY > maxBirdY)
        {
            birdY = maxBirdY;
        }
        birdSpeed = birdSpeed + 2;
        if(touch)
        {
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }



        // YELLOW BAIT
        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }

        if(yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        // GREEN BAIT
        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX = -100;
        }

        if(greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        canvas.drawCircle(greenX, greenY, 25, greenPaint);

        // RED BAIT
        redX = redX - redSpeed;

        if(hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfBird--;

            if(lifeCounterOfBird == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if(redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        canvas.drawCircle(redX, redY, 25, redPaint);
        //

        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        for(int i = 0; i < 3; i++)
        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lifeCounterOfBird)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

    }


    public boolean hitBallChecker(int x, int y)
    {
        if(birdX < x && x < (birdX + bird[0].getWidth()) && birdY < y && y < (birdY + bird[0].getHeight()))
        {
            return true;
        }

        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            birdSpeed = -22;

        }
        return true;
    }
}
