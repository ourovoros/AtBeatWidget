package us.deusexmachina.atbeatwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ViewColor extends View {

    public ViewColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    int colBG = 0;
    Paint paint = new Paint();
    RectF rbase = new RectF();


    @Override
    protected void  onDraw  (Canvas canvas)
    {
        rbase.set(0, 0, getWidth(), getHeight());       
        paint.setColor(colBG);
        canvas.drawRect(rbase, paint);
    }

    public void setColor(int col)
    {
        colBG = col;
    }
}
