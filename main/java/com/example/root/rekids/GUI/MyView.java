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
    String valor;
    int shapes;
    String emptyCell;

    /*
    Constructor
     */
    public MyView(Context context, int x, int y, String id) {
        super(context);
        idX = x;
        idY = y;
        emptyCell = id;
        init();
    }

    /*
    Constructor
     */
    public MyView(Context context) {
        super(context);
        init();
    }

    /*
    Constructor
     */
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /*
    Constructor
     */
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*
    Inicializa matriz
     */
    private void init() {
        touchOn = false;
        switch (emptyCell) {
            case "a":
                shapes = 0;
                break;
            case "b":
                shapes = 1;
                break;
            case "c":
                shapes = 2;
                break;
            case "d":
                shapes = 3;
                break;
            case "F":
                shapes = 4;
                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    /*
    Dibuja las formas de acuerdo al patron
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (shapes == 1) {

            int x = 10;
            int y = 10;
            int width = 70;
            int height = 70;

            mDrawable = new ShapeDrawable(new OvalShape());

            mDrawable.getPaint().setColor(Color.RED);

            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }else if (shapes == 0){

            int x = 10;
            int y = 10;
            int width = 70;
            int height = 70;
            float[] corners = {50,50,25,25,50,50,25,25};

            mDrawable = new ShapeDrawable(new RoundRectShape(corners, null, null));

            mDrawable.getPaint().setColor(Color.GREEN);

            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else if (shapes == 2){

            int x = 10;
            int y = 10;
            int width = 70;
            int height = 70;
            float[] corners = {50,50,25,25,50,50,25,25};

            mDrawable = new ShapeDrawable(new RoundRectShape(corners, null, null));

            mDrawable.getPaint().setColor(Color.BLUE);

            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else if (shapes == 4){

            int x = 10;
            int y = 10;
            int width = 70;
            int height = 70;

            mDrawable = new ShapeDrawable(new RectShape());

            mDrawable.getPaint().setColor(Color.LTGRAY);

            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
        else if(shapes==3){

            int x = 10;
            int y = 10;
            int width = 70;
            int height = 70;

            mDrawable = new ShapeDrawable(new ArcShape(60, 280));

            mDrawable.getPaint().setColor(Color.YELLOW);

            mDrawable.setBounds(x, y, x + width, y + height);

            mDrawable.draw(canvas);

        }
    }

    /*
    Cambia la forma de la celda si es posible
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        //Si el caracter es F, se puede cambiar
        if (emptyCell != "F"){
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchOn = !touchOn;
                invalidate();

                if (shapes == 4){
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

                if (shapes == 4){
                    shapes = 0;
                }else{
                    shapes++;
                }

        }
        return false;
    }

    /*
    Retorna el valor de String de cada figura
     */
    public String getValor(int id){
        switch (id){
            case 0:
                valor= "a";
                break;
            case 1:
                valor="b";
                break;
            case 2:
                valor="c";
                break;
            case 3:
                valor="d";
                break;
            case 4:
                valor="F";
                break;
        }
        return valor;
    }

    /*
    Touch the View event
     */
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

    public String getEmptyCell(){
        return emptyCell;
    }

}
