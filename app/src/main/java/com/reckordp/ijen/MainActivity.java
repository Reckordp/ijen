package com.reckordp.ijen;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView jumlahSaldo = findViewById(R.id.jumlah_saldo);
        int saldo = 10000;

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float saldoInterpolated = interpolatedTime * saldo;
                jumlahSaldo.setText(String.valueOf((int)saldoInterpolated));
            }
        };

        anim.setStartOffset(1000);
        anim.setDuration(3000);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Button btn = findViewById(R.id.tambah_button);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(v -> {
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        jumlahSaldo.startAnimation(anim);
    }
}