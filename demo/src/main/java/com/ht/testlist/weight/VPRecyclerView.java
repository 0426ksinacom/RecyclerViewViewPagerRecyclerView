package com.ht.testlist.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 *解决viewpage  左右滑动会被误认为是上下滑动
 */
public class VPRecyclerView extends RecyclerView {

    /****
     * 滑动距离及坐标 归还父控件焦点
     ****/
    private float xDistance, yDistance, xLast, yLast;

    public VPRecyclerView(Context context) {
        super(context);
    }

    public VPRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VPRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                Log.i("bbb", "yDistance" + yDistance + "");
                Log.i("bbb", "xDistance" + xDistance + "");

                int orientation = getOrientation(xDistance, yDistance);
                if (orientation=='r'||orientation =='l'){
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            return dx > 0 ? 'r' : 'l';//右,左
        } else {
            //Y轴移动
            return dy > 0 ? 'd' : 'u';//下//上
        }
    }
}
