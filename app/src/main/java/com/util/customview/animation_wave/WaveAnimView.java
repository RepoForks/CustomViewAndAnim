package com.util.customview.animation_wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hai.ning on 16/3/23.
 */
public class WaveAnimView extends View {

    private float mDefaultAmplitudeRatio = 0.5f;
    private int mDefaultWaterLevel;

    //water level
    private float mWaterLevelRatio;
    //wave shift
    private float mWaterShiftRatio;
    //wave amplitude
    private float mAmplitudeRatio;

    private BitmapShader mWaveShader;

    public WaveAnimView(Context context, AttributeSet set) {
        super(context, set);
    }

    public void setWaterLevelRatio(float waterLevelRatio) {
        mWaterLevelRatio = waterLevelRatio;
        invalidate();
    }
    public void setWaterShiftRatio(float waterShiftRatio) {
        mWaterShiftRatio = waterShiftRatio;
        invalidate();
    }
    public void setAmplitudeRatio(float amplitudeRatio) {
        mAmplitudeRatio = amplitudeRatio;
        invalidate();
    }

    private Bitmap createShader() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);

        int beginX, beginY;
        int endY = getHeight();
        double angle = 2* Math.PI/getWidth();//2 cycle wave in view's width
        mDefaultWaterLevel = getHeight()/2;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#A0FFFFFF"));
        for (beginX=0; beginX<getWidth()+1; beginX++) {
            double radian = beginX * angle;
            beginY = mDefaultWaterLevel + (int)(mDefaultWaterLevel * mDefaultAmplitudeRatio * Math.sin(radian));
            canvas.drawLine(beginX, beginY, beginX, endY, paint);
        }

        paint.setColor(Color.parseColor("#50FFFFFF"));
        int offset = getWidth()/8;
        for (beginX=0; beginX<getWidth()+1; beginX++) {
            double radian = (beginX+offset) * angle;
            beginY = mDefaultWaterLevel + (int)(mDefaultWaterLevel * mDefaultAmplitudeRatio * Math.sin(radian));
            canvas.drawLine(beginX, beginY, beginX, endY, paint);
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (null == mWaveShader) {
            createShader();
        }
        Matrix matrix = new Matrix();
        matrix.setScale(1.0f, mAmplitudeRatio / mDefaultAmplitudeRatio, 0, mDefaultWaterLevel);
        //matrix's replace principle, so should use postTranslate
        matrix.postTranslate(mWaterShiftRatio * getWidth(), mWaterLevelRatio * getHeight());
        mWaveShader.setLocalMatrix(matrix);
        paint.setShader(mWaveShader);

        int radius = Math.min(getWidth(), getHeight())/2;
        canvas.drawCircle(radius, radius, radius, paint);
    }
}
