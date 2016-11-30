package com.util.customview.animation_parabola;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.util.dragbackview.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ParabolaActivity extends Activity {

    @InjectView(R.id.btn_start_parabola)
    Button startParaView;
    @InjectView(R.id.btn_start_center)
    Button startCenterView;
    @InjectView(R.id.view_end)
    ImageView viewEnd;
    @InjectView(R.id.main)
    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parabola);
        ButterKnife.inject(this);
    }

    public void startParabolaAnim(View v) {
        PopCircleAnimation animation = new PopCircleAnimation(this, main, startParaView, viewEnd);
        animation.start();
//        ShoppingAnimation animation = new ShoppingAnimation(this, main, viewEnd);
//        animation.start();
    }
    public void startCenterAnim(View v) {
        ShoppingAnimation animation = new ShoppingAnimation(this, main, viewEnd);
        animation.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
