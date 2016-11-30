package com.util.customview.auto_scroll;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.util.customview.R;

/**
 * Created by hai.ning on 16/3/19.
 */
public class AutoGroupView extends LinearLayout {

    private Context mContext;
    private AutoScrollView scrollView0;
    private AutoScrollView scrollView1;
    private AutoScrollView scrollView2;

    public AutoGroupView(Context context) {
        super(context);
    }

    public AutoGroupView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        mContext = context;
        init();
    }

    public void init() {
        setOrientation(VERTICAL);
        
        scrollView0 = new AutoScrollView(mContext);
        scrollView1 = new AutoScrollView(mContext);
        scrollView2 = new AutoScrollView(mContext);

        scrollView0.init(R.mipmap.cycle_back_1, R.mipmap.cycle_back_2, AutoScrollView.LEFT, 15000);
        scrollView1.init(R.mipmap.cycle_back_3, R.mipmap.cycle_back_4, AutoScrollView.RIGHT, 10000);
        scrollView2.init(R.mipmap.cycle_back_5, R.mipmap.cycle_back_6, AutoScrollView.LEFT, 13000);

        addView(scrollView0);
        addView(scrollView1);
        addView(scrollView2);
    }

    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView0.start();
                scrollView1.start();
                scrollView2.start();
            }
        }, 500);
    }
}
