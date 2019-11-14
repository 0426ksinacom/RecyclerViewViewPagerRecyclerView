package com.ht.testlist.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * RecyclerView -ViewPage-RecyclerView  内层嵌套的RV,   解决上下滑动冲突
 */
public class RVVPRecyclerView extends RecyclerView {
    public RVVPRecyclerView(Context context) {
        super(context);
    }

    public RVVPRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RVVPRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标
    private int maxY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                downY = y;
//                True if the child does not want the parent to
//                intercept touch events.
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //获取到距离差
                float dx = x - downX;
                float dy = y - downY;
                Log.d("aaa", "ACTION_MOVE");
                //通过距离差判断方向
                int orientation = getOrientation(dx, dy);
                int[] location = {0, 0};
                getLocationOnScreen(location);
                switch (orientation) {
                    case 'd':
                        if (canScrollVertically(-1)) {
                            Log.d("aaa", "b   不要拦截");
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {//内层RecyclerView下拉到最顶部时候不再处理事件
                            getParent().requestDisallowInterceptTouchEvent(false);
                            Log.d("aaa", "b  拦截");
                        }
                        break;
                    case 'u':
                        Log.d("aaa", "maxY" + maxY + "");
                        Log.d("aaa", "location[1]" + location[1] + "");
                        if (location[1] <= maxY) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            Log.d("aaa", "u   不要拦截");
                        } else {
                            Log.d("aaa", "u   拦截");
                            getParent().requestDisallowInterceptTouchEvent(false);
//                            不加这个返回虽然拦截了，当子list在最顶部的时候，上滑不会整体移动
                            return true;
                        }
                        break;
                    case 'r':
                        Log.d("aaa", "r  拦截");
                        getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    //左右滑动交给ViewPager处理
                    case 'l':
                        Log.d("aaa", "l  拦截");
                        getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                break;
        }
        return super.dispatchTouchEvent(e);
    }

    public void setMaxY(int height) {
        this.maxY = height;
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
