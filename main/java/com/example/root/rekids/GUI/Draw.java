package com.example.root.rekids.GUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.root.rekids.R;

/**
 * Created by jose on 26/08/2017.
 */

public class Draw extends SurfaceView {
    Bitmap airplane;
    SurfaceHolder ourHolder;



    public Draw(Context context) {
        super(context);
        airplane = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
