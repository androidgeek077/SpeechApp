package com.example.speechapp;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.speechapp.activities.SelectiveActivity;

import me.wangyuwei.particleview.ParticleView;

public class MainActivity extends AppCompatActivity {
    ParticleView particleView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        particleView=(ParticleView)findViewById(R.id.particleView);
        image=(ImageView)findViewById(R.id.image);
        final Boolean session=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("session",false);

        YoYo.with(Techniques.BounceIn).duration(8000).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                final float scale = getBaseContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (300 * scale + 0.5f);
                image.requestLayout();
                image.getLayoutParams().width=pixels;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).playOn(image);
        particleView.startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(MainActivity.this, SelectiveActivity.class));
                finish();
            }

        },8000);



    }
}