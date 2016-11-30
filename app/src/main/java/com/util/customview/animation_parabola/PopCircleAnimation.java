package com.util.customview.animation_parabola;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.util.dragbackview.R;

/**
 * Created by hai.ning on 16/3/14.
 */
public class PopCircleAnimation {

    private Context mContext;
    protected ViewGroup mContainer;
    protected View startView;
    protected View endView;

    public PopCircleAnimation(Context context, ViewGroup container, View start, View end) {
        mContainer = container;
        mContext = context;
        startView = start;
        endView = end;
    }

    public void start() {
        final ImageView targetView = new ImageView(mContext);
        targetView.setBackgroundResource(R.drawable.circle_ball);

        mContainer.addView(targetView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        float startX = startView.getX() + startView.getMeasuredWidth()/2;
        float startY = startView.getY() + startView.getMeasuredHeight()/2;

        float endX = endView.getX() + endView.getMeasuredWidth()/2;
        float endY = endView.getY() + endView.getMeasuredHeight()/2;

        float controlX = startX;
        float controlY = endY + 200;

        PointF startPoint = new PointF(startX, startY);
        PointF endPoint = new PointF(endX, endY);
        PointF controlPoint = new PointF(controlX, controlY);

        startAnim(targetView, startPoint, endPoint, controlPoint);
    }

    protected void startAnim(final View targetView, PointF startPoint, PointF endPoint, PointF controlPoint) {
        AnimatorSet targetAnimSet = new AnimatorSet();

        ValueAnimator targetAnimtor = new ValueAnimator();
        targetAnimtor.setTarget(targetView);
        targetAnimtor.setObjectValues(startPoint, endPoint);
        targetAnimtor.setEvaluator(new PointTypeEvaluator(controlPoint));
        targetAnimtor.setInterpolator(new AccelerateDecelerateInterpolator());
        targetAnimtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                targetView.setX(pointF.x);
                targetView.setY(pointF.y);
            }
        });

        ObjectAnimator scaleXTargetView = ObjectAnimator.ofFloat(targetView, "scaleX", 1.0f, 0.3f);
        ObjectAnimator scaleYTargetView = ObjectAnimator.ofFloat(targetView, "scaleY", 1.0f, 0.3f);
//        targetAnimSet.playTogether(targetAnimtor, scaleXTargetView, scaleYTargetView);
        targetAnimSet.setDuration(1000);
        targetAnimSet.play(targetAnimtor).with(scaleXTargetView).with(scaleYTargetView);
        targetAnimSet.start();

        AnimatorSet endAnimSet = new AnimatorSet();
        ObjectAnimator scaleXEndView = ObjectAnimator.ofFloat(endView, "scaleX", 1.0f, 1.5f, 1.0f);
        ObjectAnimator scaleYEndView = ObjectAnimator.ofFloat(endView, "scaleY", 1.0f, 1.5f, 1.0f);

        endAnimSet.playTogether(scaleXEndView, scaleYEndView);
        endAnimSet.setDuration(300);
        endAnimSet.setStartDelay(1000);
        endAnimSet.start();

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

        private float cx, cy;

        public PointTypeEvaluator(PointF control) {
            cx = control.x;
            cy = control.y;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            final float t = fraction;
            float oneMinusT = 1.0f - t;
            PointF point = new PointF();

            PointF point0 = startValue;

            PointF point1 = new PointF();
            point1.set(cx, cy);

            PointF point2 = endValue;

            // 二次曲线算法：B(t) = (1 - t)^2 * P0 + 2t(1 - t) * P1 + t^2 * P2  （t[0,1]）
            // 三次曲线算法：B(t) = (1 - t)^3 * P0 + 3t(1 - t)^2 * P1 +　3t^2 * (1 - t) * P2 + t^3 * P3
            // 高阶曲线算法：
            point.x = oneMinusT * oneMinusT * (point0.x)
                    + 2 * oneMinusT * t * (point1.x)
                    + t * t * (point2.x);

            point.y = oneMinusT * oneMinusT * (point0.y)
                    + 2 * oneMinusT * t * (point1.y)
                    + t * t * (point2.y);
            return point;
        }
    }
}
