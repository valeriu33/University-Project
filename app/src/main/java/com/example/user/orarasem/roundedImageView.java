package com.example.user.orarasem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class roundedImageView extends android.support.v7.widget.AppCompatImageView {


    private float radius = 18.0f;
    private Path path;
    private RectF rect;

    public roundedImageView(Context context) {
        super(context);
    }

    public roundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public roundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public roundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context,attrs,defStyleAttr);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
//        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
//        canvas.clipPath(path);
//        super.onDraw(canvas);
//    }
}
