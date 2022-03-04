package com.reckordp.ijen;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private final int SALDO_AWAL = 10000;
    private final Fragment pilihan = new PilihanTambahan();

    private Button aksi_tambah;
    private int jumlahSaldo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aksi_tambah = findViewById(R.id.tambah_button);
        aksi_tambah.setOnClickListener(v -> {
            aksi_tambah.setEnabled(false);
            aktifkanPanelPenambahan();
        });

        tambahSaldo(SALDO_AWAL);
    }

    public void pembayaranDipilih(int tambahan) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.remove(pilihan);
        trans.commit();
        tambahSaldo(tambahan);
    }

    private void aktifkanPanelPenambahan() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.panel_serbaguna, pilihan);
        trans.commit();
    }

    private void tambahSaldo(int tambahan) {
        TextView jumlahSaldoText = findViewById(R.id.jumlah_saldo);
        int saldoAwal = jumlahSaldo;
        jumlahSaldo += tambahan;

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                float saldoInterpolated = interpolatedTime * tambahan + saldoAwal;
                jumlahSaldoText.setText(String.valueOf((int)saldoInterpolated));
            }
        };

        anim.setStartOffset(500);
        anim.setDuration(2500);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                aksi_tambah.setVisibility(View.VISIBLE);
                aksi_tambah.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        jumlahSaldoText.startAnimation(anim);
    }
}