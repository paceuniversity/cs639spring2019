package com.pace.cs639spring.hw3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kachi on 2/21/2018.
 */

public class SophisticatedLineGraphView extends View {

    //key is the date in millis
    //value is the number of students
    private Map<Long, Integer> mAttendanceMap = new HashMap<>();

    Point mTopLeft, mBottomLeft, mBottomRight;
    int mMax; //will hold the maximum value of students in our hash map

    int mPointColor; //color as integer
    int mMaxColor; //color as integer

    int mLineColor;
    int mPathColor;

    private boolean mShowLines = true;

    private boolean mHighlightIntegral = true;

    Paint mAxesPaint;
    TextPaint mAxesTextPaint;
    Paint mRegularPointPaint;
    Paint mMaximumPointPaint;
    Paint mLinePaint;
    Paint mPathPaint;

    Path mPath = new Path();
    List<DrawablePoint> mDrawablePoints = new ArrayList<>();

    float circleRadius = 5;

    public SophisticatedLineGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Homework);
        mPointColor = a.getColor(R.styleable.Homework_pointColor, Color.GRAY); //get standard point color from xml
        mMaxColor = a.getColor(R.styleable.Homework_maxColor, Color.BLACK); //get max point color from xml
        mLineColor = a.getColor(R.styleable.Homework_lineColor, Color.parseColor("#C6E2FF"));
        mPathColor = a.getColor(R.styleable.Homework_graphAreaColor, Color.parseColor("#9FB6CD"));
        a.recycle();
        initializePoints();
        initializePaints();

    }

    private void initializePoints() {
        mTopLeft = new Point();
        mBottomLeft = new Point();
        mBottomRight = new Point();
    }

    private void initializePaints() {
        //setup the paint used to draw our axes & the numbers on the y axis
        mAxesPaint = new Paint();
        mAxesPaint.setColor(Color.BLACK);
        mAxesPaint.setStrokeWidth(5);
        mAxesPaint.setTextSize(dpToPx(14));

        //create paint to be used to draw the text for the dates and student count
        mAxesTextPaint = new TextPaint();
        mAxesTextPaint.setColor(Color.BLACK);
        mAxesTextPaint.setTextSize(dpToPx(12));
        //this is not required. Just using it so that it's easier to center text.
        //if you didn't know about this, you could use the regular alignment (LEFT)
        //and just subtract some arbitrary value from the text's x-position on the canvas when drawing the text
        mAxesTextPaint.setTextAlign(Paint.Align.CENTER);


        //create paint to be used to draw the non-maximum points
        mRegularPointPaint = new Paint();
        mRegularPointPaint.setColor(mPointColor);

        //create paint to be used to draw the maximum point
        mMaximumPointPaint = new Paint();
        mMaximumPointPaint.setColor(mMaxColor);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(dpToPx(3));


        mPathPaint = new Paint();
        mPathPaint.setColor(mPathColor);
        mPathPaint.setStrokeWidth(0f);

    }

    public void showLines(boolean showLines) {
        boolean shouldInvalidate = showLines != mShowLines;
        mShowLines = showLines;
        if (shouldInvalidate) invalidate();
    }

    public void highlightIntegral(boolean highlight) {
        boolean shouldInvalidate = highlight != mHighlightIntegral;
        mHighlightIntegral = highlight;
        if (shouldInvalidate) invalidate();
    }

    public void setCircleRadius(float radius) {
        boolean shouldInvalidate = radius != circleRadius;
        circleRadius = radius;
        if (shouldInvalidate) invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTopLeft.set(dpToPx(25), dpToPx(15)); //topmost mPoint of the y-axis
        mBottomLeft.set(dpToPx(25), getHeight() - dpToPx(20)); //graph origin
        mBottomRight.set(getWidth() - dpToPx(10), getHeight() - dpToPx(20)); //rightmost mPoint of x-axis

        drawAxes(canvas);
        drawPoints(canvas);
    }

    private void drawAxes(Canvas canvas) {
        canvas.drawLine(mTopLeft.x, mTopLeft.y, mBottomLeft.x, mBottomLeft.y, mAxesPaint); //draw y-axis
        canvas.drawLine(mBottomLeft.x, mBottomLeft.y, mBottomRight.x, mBottomRight.y, mAxesPaint); //draw x-axis

        //if we have students present, label the lowest value and highest value on the y-axis
        if (mAttendanceMap.size() > 0) {
            //I'm subtracting the x-position by some arbitrary value so that "0" text doesn't touch the axis.
            canvas.drawText(getContext().getString(R.string.zero), mBottomLeft.x - dpToPx(10), mBottomLeft.y, mAxesTextPaint);
            int maxStudentCount = Collections.max(mAttendanceMap.values());
            mMax = maxStudentCount + (5 - (maxStudentCount % 5)); //round value to nearest 5

            //I'm subtracting the x-position by some arbitrary value so that the max value text here doesn't touch the axis.
            canvas.drawText(String.valueOf(mMax), mTopLeft.x - dpToPx(10), mTopLeft.y + dpToPx(10), mAxesTextPaint);
        }
    }

    private void drawPoints(Canvas canvas) {

        List<Long> keys = new ArrayList<>(mAttendanceMap.keySet());
        Collections.sort(keys); //sort our dates in ascending order

        //get the highest student count in our data
        int highestCount = mAttendanceMap.isEmpty() ? 0 :  Collections.max(mAttendanceMap.values());

        //the leftmost area where we can do any drawing on our graph. This is basically left padding
        int startingPointX = mTopLeft.x + dpToPx(10);
        //the rightmost area where we can do any drawing on our graph. This is basically right padding
        int endingPointX = mBottomRight.x - dpToPx(5);

        int totalAvailableHorizontalDrawingSpace = endingPointX - startingPointX;
        //since we can only have 5 points on the screen at a time, divide our graph into 5 columns of equal length
        float totalAvailableHorizontalSpaceForSinglePoint = totalAvailableHorizontalDrawingSpace /5;


        float totalAvailableVerticalDrawingSpace = mBottomLeft.y - mTopLeft.y;

        //loop through our data points
        if (mHighlightIntegral) mDrawablePoints.clear();

        DrawablePoint prevPoint;
        DrawablePoint currentPoint = null;

        for (int index=0; index < keys.size(); index++) {
            long dateInMillis = keys.get(index);
            int studentCount = mAttendanceMap.get(dateInMillis);
            boolean isMaximumValue = keys.size() > 1 && studentCount == highestCount;

            //the graph's starting x position + (the drawing space allowed for a single point * the number of previously draw points)
            float currentPointsDrawingAreaStartPosition = startingPointX + (index * totalAvailableHorizontalSpaceForSinglePoint);

            //calculate the horizontal center of our point
            float pointCenterX = currentPointsDrawingAreaStartPosition + (totalAvailableHorizontalSpaceForSinglePoint/2);

            //in order to find the vertical center of our point, we have to find the ratio between
            //this student count & the maximum student count
            //ie if studentCount = 7 and mMax = 14, this value will be .5
            float studentCountToMaxRatio = (float)studentCount/(float)mMax;

            //subtract by 1 since we want a percentage of how far we should be from the top of our y axis.
            //in other words if studentCountToMaxRatio returned a value of .25, then ratioFromTop would be .75
            //this means that the point would be 25% away from the bottom and 75% away from the top.
            float ratioFromTop = 1 - studentCountToMaxRatio;

            //add the ratio from the top to whatever the top-most position is to calculate where to draw the point
            //remember that the origin (0, 0) is the top left of the screen, so as you move down, the y values increase
            float pointCenterY = mTopLeft.y + (totalAvailableVerticalDrawingSpace * ratioFromTop);


            //two points make a line. Save the old point in prevPoint so that when we calculate the new point,
            //we'll have the two points needed to draw a line.
            prevPoint = currentPoint;
            currentPoint = new DrawablePoint(new PointF(pointCenterX, pointCenterY), isMaximumValue ? mMaximumPointPaint : mRegularPointPaint);
            mDrawablePoints.add(currentPoint);

            //if we're not highlighting the integral, then just draw the points now
            //else, wait until we've drawn the integral first before drawing point lines and points
            //this will ensure that the lines and points get drawn on top of the highlighted area
            if (!mHighlightIntegral) {
                //draw the points to the graph, showing lines if needed
                drawPoints(canvas, prevPoint, currentPoint, index == keys.size() - 1);
            }

            //format date
            String date = DateFormat.format("MM/dd", dateInMillis).toString();
            //draw text directly underneath the point beneath the x-axis
            canvas.drawText(date, pointCenterX, mBottomLeft.y + dpToPx(15), mAxesTextPaint);
        }

        if (mHighlightIntegral) {
            //if we have more than one point, then show the integral
            if  (mDrawablePoints.size() > 1) drawHighlightedIntegral(canvas);

            //draw all the previous points that we did not draw before since we needed to draw the integral first
            for (int i = 0; i < mDrawablePoints.size(); i++) {
                DrawablePoint drawing = mDrawablePoints.get(i);
                drawPoints(canvas, i == 0 ? null : mDrawablePoints.get(i - 1), drawing,i== mDrawablePoints.size() - 1);
            }
        }
    }

    private void drawHighlightedIntegral(Canvas canvas) {
        mPath.reset();
        float startX = mDrawablePoints.get(0).mPoint.x;
        float endX = mDrawablePoints.get(mDrawablePoints.size() - 1).mPoint.x;

        float bottomAxisPosition = mBottomLeft.y;

        mPath.moveTo(startX, bottomAxisPosition);
        for (DrawablePoint drawablePoint : mDrawablePoints) {
            mPath.lineTo(drawablePoint.mPoint.x, drawablePoint.mPoint.y);
        }
        mPath.lineTo(endX, bottomAxisPosition);
        mPath.lineTo(startX, bottomAxisPosition);
        mPath.close();
        canvas.drawPath(mPath, mPathPaint);
    }

    public void drawPoints(Canvas canvas, DrawablePoint prevPoint, DrawablePoint currPoint, boolean last) {
        //because we want to points to appear on top of the line, we have to draw things kind of backwards.
        //what this means is that since a line consists of two points and a connecting segment, we have to
        //first draw the connecting segment, then draw the first point of the line.
        //after you do this, you have to figure out if the current point is the last point in the graph,
        //if it is, then draw it now. if it's not, then wait until drawPoints if called again to draw the current point later
        if (prevPoint != null) {
            if (mShowLines) {
                canvas.drawLine(prevPoint.mPoint.x, prevPoint.mPoint.y, currPoint.mPoint.x, currPoint.mPoint.y, mLinePaint);
            }
            canvas.drawCircle(prevPoint.mPoint.x, prevPoint.mPoint.y, dpToPx(circleRadius), prevPoint.mPaintColor);
        }
        //if this point, is the last point in our graph, just draw it now. Don't bother waiting until the
        //next point comes in (since it won't come in)
        if (last) {
            canvas.drawCircle(currPoint.mPoint.x, currPoint.mPoint.y, dpToPx(circleRadius), currPoint.mPaintColor);
        }

    }

    public void addData(long timeInMillis, int numberOfStudents) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        //remove all hour, minute, second & millisecond information from the date so that
        //the time we receive represents the starts of the day. This is how we are going to
        //make sure that a date that represents 2/28/2018 1:30PM and a date that represents
        //2/28/2018 2:30PM all have the same map key since they'll both get converted to
        //2/28/2018 12AM
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mAttendanceMap.put(calendar.getTimeInMillis(), numberOfStudents);

        //if we have more than 5 data points, remove older values
        if (mAttendanceMap.size() > 5) {
            Long oldestDate = Collections.min(mAttendanceMap.keySet());
            mAttendanceMap.remove(oldestDate);
        }

        invalidate(); //redraw graph
    }

    public void clearData() {
        if (mAttendanceMap.size() > 0) {
            mAttendanceMap.clear();
            invalidate();
        }
    }

    private int dpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
    }
}
