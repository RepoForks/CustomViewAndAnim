package com.util.customview.animation_wave;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Created by hai.ning on 16/3/24.
 *
 * wave animation
 */
public class WaveAnimation {

    AnimatorSet mAnimSets;

    public void start(WaveAnimView view) {
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(view, "waterLevelRatio", 0.5f, 0.0f);
        waterLevelAnim.setDuration(5000);

        ObjectAnimator waterShiftAnim = ObjectAnimator.ofFloat(view, "waterShiftRatio", 0f, 1f);
        waterShiftAnim.setDuration(1000);
        waterShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waterShiftAnim.setInterpolator(new LinearInterpolator());

        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(view, "amplitudeRatio", 0.0001f, 0.1f);
        amplitudeAnim.setDuration(3000);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setInterpolator(new LinearInterpolator());

        mAnimSets = new AnimatorSet();
        mAnimSets.playTogether(waterLevelAnim, waterShiftAnim, amplitudeAnim);
        mAnimSets.start();
    }

    public void cancel() {
        if (null != mAnimSets && mAnimSets.isRunning()) {
            mAnimSets.cancel();
        }
    }
}
