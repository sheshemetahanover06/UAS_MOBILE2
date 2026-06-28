package com.example.ayominum;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private View circle;
    private ImageView logo;
    private TextView txtTitle;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        circle = findViewById(R.id.circle);
        logo = findViewById(R.id.logo);
        txtTitle = findViewById(R.id.txtTitle);
        rootLayout = findViewById(R.id.rootLayout);

        // Sembunyikan logo dan teks di awal
        logo.setVisibility(View.GONE);
        txtTitle.setVisibility(View.GONE);

        startAnimation();
    }

    private void startAnimation() {

        // Animasi lingkaran bergerak ke bawah
        ObjectAnimator moveCircle =
                ObjectAnimator.ofFloat(circle,
                        "translationY",
                        0f,
                        450f);

        moveCircle.setDuration(1500);

        moveCircle.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                // Hilangkan lingkaran
                circle.setVisibility(View.GONE);

                // Tampilkan logo dan teks
                logo.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);

                // Efek fade in logo
                logo.setAlpha(0f);
                logo.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .start();

                // Efek fade in teks
                txtTitle.setAlpha(0f);
                txtTitle.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .start();

                // Pindah ke Dashboard setelah 2 detik
                new Handler(Looper.getMainLooper()).postDelayed(() -> {

                    Intent intent = new Intent(
                            SplashActivity.this,
                            DashboardActivity.class
                    );

                    startActivity(intent);
                    finish();

                }, 2000);
            }
        });

        moveCircle.start();
    }
}
