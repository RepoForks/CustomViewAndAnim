package com.util.customview.animation_wave;

import android.app.Activity;
import android.os.Bundle;

import com.util.dragbackview.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WaveAnimActivity extends Activity {

    @InjectView(R.id.wave_view)
    WaveAnimView waveAnimView;

    WaveAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_anim);
        ButterKnife.inject(this);

        animation = new WaveAnimation();
        animation.start(waveAnimView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        animation.cancel();
    }
}
