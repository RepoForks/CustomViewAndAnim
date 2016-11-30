package com.util.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.util.customview.animation_parabola.ParabolaActivity;
import com.util.customview.animation_wave.WaveAnimActivity;
import com.util.customview.auto_scroll.AutoScrollActivity;
import com.util.customview.custom_view.CustomViewActivity;
import com.util.dragbackview.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterShapeImage(View v) {
        startActivity(new Intent(this, CustomViewActivity.class));
    }
    public void enterParabolaAnim(View v) {
        startActivity(new Intent(this, ParabolaActivity.class));
    }
    public void enterAutoScroll(View v) {
        startActivity(new Intent(this, AutoScrollActivity.class));
    }
    public void enterWaveAnimation(View v) {
        startActivity(new Intent(this, WaveAnimActivity.class));
    }
}
