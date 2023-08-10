package com.ojwang.edkins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                // Animation started (optional)
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                // Animation ended, navigate to the main activity
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                // Animation canceled (optional)
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                // Animation repeated (optional)
            }
        });
    }
}