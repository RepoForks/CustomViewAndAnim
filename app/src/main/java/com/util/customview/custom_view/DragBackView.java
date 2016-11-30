package com.util.customview.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by hai.ning on 16/2/1.
 */
public class DragBackView extends CircleShopBagView {

    private float dX;
    private float dY;

    private static final int DURATION = 300;

    private ValueAnimator mAnimator;

    public DragBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragBackView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mAnimator = new ValueAnimator();
        mAnimator.setTarget(this);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(DURATION);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = Float.valueOf(animation.getAnimatedValue().toString());
                updateEdgeDistance(value);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = event.getRawX() - getX();
                dY = event.getRawY() - getY();
                break;
            case MotionEvent.ACTION_MOVE:
                dragTo(event.getRawX() - dX, event.getRawY() - dY);
                break;
            case MotionEvent.ACTION_UP:
                startAnimate();
                break;
        }
        return true;
    }

    public void dragTo(float x, float y) {
        int x_max = ((View) getParent()).getMeasuredWidth() - getMeasuredWidth();
        int y_max = ((View) getParent()).getMeasuredHeight() - getMeasuredHeight();

        x = Math.max(x, 0);
        x = Math.min(x, x_max);
        y = Math.max(y, 0);
        y = Math.min(y, y_max);

        setX(x);
        setY(y);
    }

    private void startAnimate() {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        float left = getX();
        float right = ((View) getParent()).getMeasuredWidth() - getX() - getWidth();
        if (left < right) {
            mAnimator.setFloatValues(left, lp.leftMargin);
        } else {
            mAnimator.setFloatValues(getX(), ((View) getParent()).getMeasuredWidth() - getWidth() - lp.rightMargin);
        }
        mAnimator.start();
    }

    public void updateEdgeDistance(float x) {
        setX(x);
    }

}
