package com.util.customview.auto_scroll;

import android.app.Activity;
import android.os.Bundle;

import com.util.customview.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AutoScrollActivity extends Activity {

    @InjectView(R.id.auto_scroll_view)
    AutoGroupView autoScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
        ButterKnife.inject(this);

        startAnim();
    }

    private void startAnim() {
        autoScrollView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
