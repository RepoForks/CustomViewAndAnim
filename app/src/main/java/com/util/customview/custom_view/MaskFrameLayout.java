package com.util.customview.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.util.customview.R;


/**
 * Created by haining on 16/11/10.
 *
 * Mask layout that can be customized layout shape.
 */
public class MaskFrameLayout extends FrameLayout {

    private float mBorderRadius;
    private Paint mPaint;
    private Bitmap mMaskBitmap;
    private PorterDuffXfermode mPorterDuffXferMode;

    public MaskFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskFrameLayout);
            mBorderRadius = a.getDimension(R.styleable.MaskFrameLayout_border_radius,
                    getResources().getDimension(R.dimen.mask_layout_default_radius));
            a.recycle();
        } else {
            mBorderRadius = getResources().getDimension(R.dimen.mask_layout_default_radius);
        }

        setDrawingCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 11) {
            //Only works for software layers. This can fix black background problem
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXferMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    /**
     * customize mask shape here
     * @return
     */
    protected Bitmap createMask() {
        if (mMaskBitmap != null) {
            return mMaskBitmap;
        }

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w <= 0 || h <= 0) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawRoundRect(new RectF(0, 0, w, h), mBorderRadius, mBorderRadius, paint);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setSize(w, h);
    }

    private void setSize(int width, int height) {
        if (width > 0 && height > 0) {
            swapBitmapMask(createMask());
        }
    }

    private void swapBitmapMask(Bitmap newMask) {
        if (newMask != null && !newMask.equals(mMaskBitmap)) {
            if (mMaskBitmap != null && !mMaskBitmap.isRecycled()) {
                mMaskBitmap.recycle();
            }
            mMaskBitmap = newMask;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mMaskBitmap != null && mPaint != null) {
            mPaint.setXfermode(mPorterDuffXferMode);
            canvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);
            mPaint.setXfermode(null);
        }
    }
}
