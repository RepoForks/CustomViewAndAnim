package com.util.customview.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

import com.util.dragbackview.R;

/**
 * Created by hai.ning on 16/3/3.
 */

public class CircleShopBagView extends ImageView {

    private Context mContext;
    private Paint paint;
    private Bitmap mSrc;
    private Bitmap mDst;
    private PorterDuffXfermode porterDuffXfermode;

    public CircleShopBagView(Context context) {
        this(context, null);
    }

    public CircleShopBagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    protected void init() {
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.OVERLAY);

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            setSize(w, h);
        }
    }

    private void setSize(int width, int height) {
        if (width > 0 && height > 0) {
            makeSrc();
            makeDst();
        }
    }

    private void makeDst() {
        if (mDst != null) {
            return;
        }

        Bitmap bm = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);

        int strokeWidth = 3;
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - strokeWidth, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.BLACK);
        c.drawCircle(getWidth() / 2, getHeight() / 2, getWidth()/2 - strokeWidth, paint);
        mDst = bm;
    }

    private void makeSrc() {
        if (mSrc != null) {
            return;
        }

        int w = getWidth()/5*4;
        int h = getHeight()/5*4;
        int left = (getWidth()-w)/2;
        int top = (getHeight()-h)/2;

        Bitmap bm = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Drawable drawable = getDrawable();
        if (null == drawable) {
            Toast.makeText(mContext, "Do you forget to set image source by android:src ?", Toast.LENGTH_SHORT).show();
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            drawable = new BitmapDrawable(getResources(), bm);
        }
        //center inside
        drawable.setBounds(left, top, left + w, top + h);
        drawable.draw(canvas);
        mSrc = bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        paint.reset();

        canvas.drawBitmap(mDst, 0, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(mSrc, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restore();
    }
}