package com.pace.cs639spring.hw4;

/**
 * Created by kachi
 */

public class Circle {

    //constants used to control the circle's direction.
    static int DIRECTION_LEFT = -1;
    static int DIRECTION_RIGHT = 1;

    //the value of the radius in pixels
    private int mPxRadius;
    private int mColor;

    //the value of the circle's speed in dp. This is actually what gets show in the mSpeedValue TextView
    int mSpeed;
    //the value of the circle's speed in px. This is what we actually use to update the circle's position in onDraw()
    int mPxSpeed;
    int mCurrentCenterX;
    private int mDirection;

    public Circle(int radius, int color, int speed) {
        mPxRadius = Util.dpToPx(radius);
        mColor = color;
        mSpeed = speed;
        mPxSpeed = Util.dpToPx(speed);
        mCurrentCenterX = color;
        mDirection = DIRECTION_RIGHT;
    }

    public int getColor() {
        return mColor;
    }

    public int getRadius() {
        return mPxRadius;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public int getCurrentRight() {
        return mCurrentCenterX + mPxRadius;
    }

    public int getCurrentCenterX() {
        return mCurrentCenterX;
    }

    public int getCurrentLeft() {
        return mCurrentCenterX - mPxRadius;
    }

    public boolean isMovingLeft() {
        return mDirection == DIRECTION_LEFT;
    }

    public void changeDirectionToLeft() {
        mDirection = DIRECTION_LEFT;
    }

    public boolean isMovingRight() {
        return mDirection == DIRECTION_RIGHT;
    }

    public void changeDirectionToRight() {
        mDirection = DIRECTION_RIGHT;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
        mPxSpeed =  Util.dpToPx(speed);
    }

    public void updatePosition() {
        mCurrentCenterX += (mDirection * mPxSpeed);
    }

    /**
     * A helper function to ensure that the left and rightmost side of the circle do not exceed
     * the bounds of the view. If it does, reset the circle's center x so that the sides of the
     * circle are touching the bounds of the view and not going over it
     * @param minValue
     * @param maxValue
     */
    public void clamp(int minValue, int maxValue) {
        if (getCurrentLeft() < minValue) mCurrentCenterX = minValue + mPxRadius;
        else if (getCurrentRight() > maxValue) mCurrentCenterX = maxValue - mPxRadius;
    }
}
