package com.reckordp.ijen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PilihanTambahan extends Fragment {

    private final int INDIKATOR_PANEL_ANGLE = 180;
    private boolean hasStarted = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup panel = (ViewGroup) inflater.inflate(R.layout.pilihan_tambahan, container, false);

        FloatingActionButton aksiPanel = panel.findViewById(R.id.aksi_panel_tambah);
        aksiPanel.setOnClickListener(v -> {
            int origin = aksiPanel.getRotation() == 0 ? 0 : INDIKATOR_PANEL_ANGLE;
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    float angle = origin + INDIKATOR_PANEL_ANGLE * interpolatedTime;
                    Log.i("AYY", String.valueOf(angle));
                    aksiPanel.setRotation((int)(angle));
                }
            };
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    hasStarted = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hasStarted = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            if (!hasStarted) aksiPanel.startAnimation(anim);
        });

        ArrayList<String> deretPilihan = new ArrayList();
        for (int i = 0; i < 10; i++) {
            deretPilihan.add(String.valueOf((i + 1) * 1000));
        }

        ListView pilihan = panel.findViewById(R.id.pilihan_jumlah);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_activated_1,
                deretPilihan
        );
        pilihan.setAdapter(adapter);



        return panel;
    }
}
