package com.util.customview.auto_scroll;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by hai.ning on 16/3/18.
 */
public class AutoScrollView extends ViewGroup {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private int mOrientation;
    private int mDuration;

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mPictureWidth;
    private int mPictureHeight;

    private ImageView mView1;
    private ImageView mView0;

    public AutoScrollView(Context context) {
        super(context);
        mContext = context;
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void init(int resId1, int resId2, int orientation, int duration) {
        mOrientation = orientation;
        mDuration = duration;

        mView0 = new ImageView(mContext);
        mView0.setImageResource(resId2);
        mView0.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(mView0);

        mView1 = new ImageView(mContext);
        mView1.setImageResource(resId1);
        mView1.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(mView1);

        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), resId2);
        mPictureWidth = b.getWidth();
        mPictureHeight = b.getHeight();
    }

    public void start() {
        LinearInterpolator interpolator = new LinearInterpolator();
        int transX = mOrientation == RIGHT ? mWidth : -mWidth;

        ObjectAnimator anim1View1 = ObjectAnimator.ofFloat(mView1, "translationX", 0, transX);
        anim1View1.setInterpolator(interpolator);

        ObjectAnimator anim1View2 = ObjectAnimator.ofFloat(mView0, "translationX", -transX, 0);
        anim1View2.setInterpolator(interpolator);

        AnimatorSet animSet1 = new AnimatorSet();
        animSet1.playTogether(anim1View1, anim1View2);

        transX = -transX;
        ObjectAnimator anim2View1 = ObjectAnimator.ofFloat(mView1, "translationX", transX, 0);
        anim2View1.setInterpolator(interpolator);

        ObjectAnimator anim2View2 = ObjectAnimator.ofFloat(mView0, "translationX", 0, -transX);
        anim2View2.setInterpolator(interpolator);

        AnimatorSet animSet2 = new AnimatorSet();
        animSet2.playTogether(anim2View1, anim2View2);

        final AnimatorSet setAll = new AnimatorSet();
        setAll.playSequentially(animSet1, animSet2);
        setAll.setDuration(mDuration);
        setAll.start();

        setAll.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setAll.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //center inside
        mWidth = getMeasuredWidth();
        mHeight = (int) (((float)mWidth / mPictureWidth) * mPictureHeight);
        measureChildren(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mView1.layout(0, 0, mWidth, mHeight);
        mView0.layout(0, 0, mWidth, mHeight);
    }
}
