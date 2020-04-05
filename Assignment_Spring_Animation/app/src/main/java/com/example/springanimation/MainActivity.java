package com.example.springanimation;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.SpringAnimation;

public class MainActivity extends AppCompatActivity {

    SpringAnimation springAnimation;
    float anim = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = findViewById(R.id.springer);

        springAnimation = new SpringAnimation(image,SpringAnimation.SCROLL_Y,anim);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("beep","beep");
                springAnimation.start();
                anim = -anim;
                springAnimation.animateToFinalPosition(anim);
                return false;
            }
        });
    }
}
