package com.stfalcon.chatkit.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by Anton Bevza on 1/12/17.
 */

public class ShapeImageView extends ImageView {
    private Path path;

    public ShapeImageView(Context context) {
        super(context);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = new Path();
        float halfWidth = (float) w / 2f;
        float firstParam = (float) w * 0.1f;
        float secondParam = (float) w * 0.8875f;

        path.moveTo(halfWidth, (float) w);
        path.cubicTo(firstParam, (float) w, 0, secondParam, 0, halfWidth);
        path.cubicTo(0, firstParam, firstParam, 0, halfWidth, 0);
        path.cubicTo(secondParam, 0, (float) w, firstParam, (float) w, halfWidth);
        path.cubicTo((float) w, secondParam, secondParam, (float) w, halfWidth, (float) w);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.clipPath(path);
            super.onDraw(canvas);
        }
    }
}
