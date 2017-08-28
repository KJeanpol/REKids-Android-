package com.example.root.rekids.GUI;

/**
 * Created by root on 27/08/17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    public interface OnToggledListener {
        void OnToggled(MyView v, boolean touchOn);
    }

    private ShapeDrawable mDrawable;
    boolean touchOn;
    boolean mDownTouch = false;
    private OnToggledListener toggledListener;
    int idX = 0; //default
    int idY = 0; //default
    int shapes;

    public MyView(Context context, int x, int y) {
        super(context);
        idX = x;
        idY = y;
        init();
    }

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        touchOn = false;
        shapes = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shapes == 1) {

            int x = 10;
            int y = 10;
            int width = 125;
            int height = 300;

            mDrawable = new ShapeDrawable(new OvalShape());
            // If the color isn't set, the shape uses black as the default.
            mDrawable.getPaint().setColor(Color.RED);
            // If the bounds aren't set, the shape can't be drawn.
            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else if (shapes == 2){

            int x = 10;
            int y = 10;
            int width = 125;
            int height = 300;
            float[] corners = {50,50,25,25,50,50,25,25};

            mDrawable = new ShapeDrawable(new RoundRectShape(corners, null, null));
            // If the color isn't set, the shape uses black as the default.
            mDrawable.getPaint().setColor(Color.BLUE);
            // If the bounds aren't set, the shape can't be drawn.
            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else if (shapes == 3){

            int x = 10;
            int y = 10;
            int width = 125;
            int height = 300;

            mDrawable = new ShapeDrawable(new RectShape());
            // If the color isn't set, the shape uses black as the default.
            mDrawable.getPaint().setColor(Color.LTGRAY);
            // If the bounds aren't set, the shape can't be drawn.
            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else {

            int x = 10;
            int y = 10;
            int width = 125;
            int height = 300;

            mDrawable = new ShapeDrawable(new ArcShape(60, 280));
            // If the color isn't set, the shape uses black as the default.
            mDrawable.getPaint().setColor(Color.YELLOW);
            // If the bounds aren't set, the shape can't be drawn.
            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchOn = !touchOn;
                invalidate();

                if (shapes == 3){
                    shapes = 0;
                }else{
                    shapes++;
                }

                if(toggledListener != null){
                    toggledListener.OnToggled(this, touchOn);
                }

                mDownTouch = true;
                return true;

            case MotionEvent.ACTION_UP:
                if (mDownTouch) {
                    mDownTouch = false;
                    performClick();
                    return true;
                }

                if (shapes == 3){
                    shapes = 0;
                }else{
                    shapes++;
                }

        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setOnToggledListener(OnToggledListener listener){
        toggledListener = listener;
    }

    public int getIdX(){
        return idX;
    }

    public int getIdY(){
        return idY;
    }

}
