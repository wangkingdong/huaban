package adapter.it.com.palette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wang on 2016/12/21.
 */

public class MySurface extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    Paint paint;
    SurfaceHolder holder;
    boolean isChace;
    Canvas canvas;
    Path path;
    float xx;
    float yy;
    public MySurface(Context context) {
        this(context,null);
    }

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(false);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new PathEffect());
        holder = getHolder();
        holder.addCallback(this);
        path = new Path();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        Log.e("aaa","MotionEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("aaa","按下的时候");
                isChace=true;
                xx=x;
                yy=y;
                path.moveTo(xx,yy);
            case MotionEvent.ACTION_MOVE:
                xx=x;
                yy=y;
                path.lineTo(xx,yy);
                new Thread(this).start();
                break;
            case MotionEvent.ACTION_UP:
                isChace=false;
                break;
        }

        Log.e("aaa","bbb");
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        Log.e("aaa","没开始的子线程");
        while (isChace){
            try {
                Log.e("aaa","子线程");
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                canvas.drawPath(path,paint);
            }finally {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
