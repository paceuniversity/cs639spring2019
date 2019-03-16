package com.pace.cs639spring.hw3;

import android.graphics.Paint;
import android.graphics.PointF;

public class DrawablePoint {

    PointF mPoint;
    Paint mPaintColor;

    DrawablePoint(PointF point, Paint paintColor) {
        mPoint = point;
        mPaintColor = paintColor;
    }
}