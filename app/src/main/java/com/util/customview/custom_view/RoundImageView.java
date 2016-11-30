package com.util.customview.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.util.dragbackview.R;


/**
 * Created by hai.ning on 16/3/24.
 */
public class RoundImageView extends ImageView {

    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;

    private int mBitmapWidth;
    private int mBitmapHeight;

    private int mImgType;
    private float borderRadius;

    public RoundImageView(Context context) {
        this(context, null);
    }
    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mImgType = a.getInt(R.styleable.RoundImageView_type, 0);
        borderRadius = a.getDimension(R.styleable.RoundImageView_radius, 0);
        a.recycle();
    }

    public Bitmap createBitmap() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }

        mBitmapWidth = drawable.getIntrinsicWidth();
        mBitmapHeight = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    public Paint createShaderPaint() {
        Bitmap bitmap = createBitmap();
        if (null == bitmap) {
            return new Paint();
        }

        float scaleX, scaleY;

        //缩放XY，使图片填满整个区域
        if (mImgType == TYPE_CIRCLE) {
            //圆形ImageView时，有可能造成图片变形，所以要使用偏正方形的图片
            int minSize = Math.min(getWidth(), getHeight());
            scaleX = minSize / (float) mBitmapWidth;
            scaleY = minSize/ (float) mBitmapHeight;
        } else {
            scaleX = getWidth() / (float) mBitmapWidth;
            scaleY = getHeight() / (float) mBitmapHeight;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImgType == TYPE_CIRCLE) {
            int minSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(minSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(minSize, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mImgType == TYPE_ROUND) {
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectF, borderRadius, borderRadius, createShaderPaint());
        } else {
            int radius = Math.min(getWidth(), getHeight())/2;
            canvas.drawCircle(radius, radius, radius, createShaderPaint());
        }
    }
}
