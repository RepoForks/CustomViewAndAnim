package com.util.customview.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by hai.ning on 16/3/3.
 */

public class OvalPhotoView extends ImageView {

    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;

    public OvalPhotoView(Context context) {
        this(context, null);
    }

    public OvalPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        setDrawingCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 11) {
            //Only works for software layers. This can fix black background problem
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(createDes(), 0, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(createSrc(), 0, 0, paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    protected Bitmap createSrc() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0,0, getWidth(), getHeight()), paint);
        return bitmap;
    }

    protected Bitmap createDes() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Drawable drawable = getDrawable();
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}