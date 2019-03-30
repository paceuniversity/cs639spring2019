package com.pace.cs639spring.hw4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kachi
 */

public class CircleAnimationView extends View {

    //the number of frames that I'd like the circle to move at. I want 60 frames to be drawn
    //every second (or 1000 milliseconds). This number evaluates to ~16.67 (specifically 16, since longs can't be decimals)
    //This means that my refresh rate, or how much time I will wait before I call invalidate again, will be 16 millisconds.
    private long REFRESH_RATE = 1000/60;

    private int DEFAULT_CIRCLE_RADIUS = 10;
    private int DEFAULT_CIRCLE_COLOR = Color.BLACK;
    private int DEFAULT_CIRCLE_SPEED = 5;

    private Circle mCircle;
    private Paint mCirclePaint;

    boolean mIsRunning;

    //Runnable that just calls invalidateAndRepeat()
    Runnable mInvalidateRunnable = new Runnable() {
        @Override
        public void run() {
            invalidateAndRepeat();
        }
    };

    public CircleAnimationView(Context context) {
        super(context);
        //if we're using this constructor, initialize the circle with some default values
        init(DEFAULT_CIRCLE_RADIUS, DEFAULT_CIRCLE_COLOR, DEFAULT_CIRCLE_SPEED);
    }

    public CircleAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //pull custom attributes from XML
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Homework);
        int radius = a.getInteger(R.styleable.Homework_circleRadius, DEFAULT_CIRCLE_RADIUS);
        int color = a.getColor(R.styleable.Homework_circleColor, DEFAULT_CIRCLE_COLOR);
        int speed = a.getInteger(R.styleable.Homework_circleSpeed, DEFAULT_CIRCLE_SPEED);
        init(radius, color, speed);
        a.recycle();
    }

    private void init(int radius, int color, int speed) {
        //initialize the circle associated w/ our CircleAnimationView with some values
        mCircle = new Circle(radius, color, speed);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircle.getColor());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        int centerY = getHeight()/2; //we're going to vertically center the circle
        mCirclePaint.setColor(mCircle.getColor());

        //if the right side of the circle is touching the right side of this view and the circle is
        //still moving right, then change the circle's direction so that it will start moving to the left
        //else if the left side of the circle is touching the left side of this view and the circle is
        //still moving left, then change the circle's direction so that it will start moving to the right
        if (mCircle.getCurrentRight() >= getWidth() && mCircle.isMovingRight()) {
            mCircle.changeDirectionToLeft();
        } else if (mCircle.getCurrentLeft() <= 0 && mCircle.isMovingLeft()) {
            mCircle.changeDirectionToRight();
        }


        //only want to animate the circle if our animation should be running
        if (mIsRunning) {
            //updates the circle's position by just adding its current speed value to its current position.
            //distance = speed x time
            mCircle.updatePosition();
            mCircle.clamp(0, getWidth()); //makes sure that the circle stays within the bounds of the view
        }
        //draws circle to canvas w/ updated values
        canvas.drawCircle(mCircle.getCurrentCenterX(), centerY, mCircle.getRadius(), mCirclePaint);
    }

    //replaces the current circle with a new one
    public void setCircle(Circle circle) {
        mCircle = circle;
    }

    public void start() {
        if (mIsRunning) return; //if we're already running, don't bother

        mIsRunning = true;
        //calling this is what makes the circle animate.
        invalidateAndRepeat();
    }

    public void stop() {
        mIsRunning = false;
        //calling this removes the mInvalidateRunnable Runnable from the main thread and stops the
        //circle from animating
        removeCallbacks(mInvalidateRunnable);
    }

    public void invalidateAndRepeat() {
        //tells the view to redraw itself
        invalidate();
        //tells the main thread to execute whatever code is inside mInvalidateRunnable's run() method
        //after a certain number of milliseconds
        postDelayed(mInvalidateRunnable, REFRESH_RATE);
    }
}
