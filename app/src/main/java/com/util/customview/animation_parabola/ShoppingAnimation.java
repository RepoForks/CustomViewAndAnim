package com.util.customview.animation_parabola;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.util.customview.R;

/**
 * Created by hai.ning on 16/3/14.
 */
public class ShoppingAnimation {

    private Context mContext;
    protected RelativeLayout mContainer;
    protected View startView;
    protected View endView;

    public ShoppingAnimation(Context context, RelativeLayout container, View end) {
        mContainer = container;
        mContext = context;
        endView = end;
    }

    public void start() {
        final ImageView targetView = new ImageView(mContext);
        targetView.setBackgroundResource(R.drawable.cart2);

        int w = 80;
        int h = 80;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mContainer.addView(targetView, params);

        PointF startPoint = new PointF(mContainer.getX() + mContainer.getMeasuredWidth()/2 - w/2,
                mContainer.getY() + mContainer.getMeasuredHeight()/2 - h/2);
        PointF endPoint = new PointF(endView.getX() + endView.getMeasuredWidth()/2 - w/2,
                endView.getY() + endView.getMeasuredHeight()/2 - h/2);

        startAnim(targetView, startPoint, endPoint);
    }

    protected void startAnim(final View targetView, PointF startPoint, PointF endPoint) {
        AnimatorSet targetAnimSet = new AnimatorSet();

        ValueAnimator targetAnimtor = new ValueAnimator();
        targetAnimtor.setTarget(targetView);
        targetAnimtor.setObjectValues(startPoint, endPoint);
        targetAnimtor.setEvaluator(new PointTypeEvaluator());
        targetAnimtor.setInterpolator(new AccelerateDecelerateInterpolator());
        targetAnimtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                targetView.setX(pointF.x);
                targetView.setY(pointF.y);
            }
        });

        ObjectAnimator scaleXTargetView = ObjectAnimator.ofFloat(targetView, "scaleX", 1.0f, 1.5f, 1.0f);
        ObjectAnimator scaleYTargetView = ObjectAnimator.ofFloat(targetView, "scaleY", 1.0f, 1.5f, 1.0f);
        targetAnimSet.playTogether(scaleXTargetView, scaleYTargetView);
        targetAnimSet.setDuration(500);
        targetAnimSet.start();

        AnimatorSet animSet2 = new AnimatorSet();
        ObjectAnimator alphaTargetView = ObjectAnimator.ofFloat(targetView, "alpha", 1.0f, .3f);
        ObjectAnimator scaleXTargetView2 = ObjectAnimator.ofFloat(targetView, "scaleX", 1.0f, .8f, .5f);
        ObjectAnimator scaleYTargetView2 = ObjectAnimator.ofFloat(targetView, "scaleY", 1.0f, .8f, .5f);
        animSet2.playTogether(targetAnimtor, alphaTargetView, scaleXTargetView2, scaleYTargetView2);
        animSet2.setDuration(500);
        animSet2.setStartDelay(500);
        animSet2.start();

        targetAnimtor.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mContainer.removeView(targetView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    class PointTypeEvaluator implements TypeEvaluator<PointF> {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = (endValue.x - startValue.x)*fraction + startValue.x;
            float y = (endValue.y - startValue.y)*fraction + startValue.y;
            return new PointF(x,y);
        }
    }
}
