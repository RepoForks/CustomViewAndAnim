package com.util.customview.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by hai.ning on 16/3/25.
 */
public class ShaderView extends ImageView{

    private BitmapShader shader;

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void createShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
        Matrix matrix = new Matrix();
        float sx = getWidth()/(float)w/2;
        matrix.setScale(sx, getHeight() / (float) h / 2, 0, 0);
        matrix.postTranslate(100f, 0);
        shader.setLocalMatrix(matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (null == shader) {
            createShader();
        }

        paint.setShader(shader);
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, paint);
    }
}
