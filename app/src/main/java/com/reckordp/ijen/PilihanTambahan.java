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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PilihanTambahan extends Fragment {

    private final int INDIKATOR_PANEL_ANGLE = 180;
    private final int INVALID_SELECTION = -1;

    private boolean expanded = false;
    private boolean hasStarted = false;
    private int selected = INVALID_SELECTION;
    private int totalPilihanHeight;
    private ViewGroup panel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        panel = (ViewGroup)inflater.inflate(R.layout.pilihan_tambahan,
                container, false);
        attachAksiPanelOnClickListener();
        fillListViewPilihan();
        return panel;
    }

    // We expand the list if expanded is false
    void animationForPilihan(float interpolatedTime) {
        ListView pilihan = panel.findViewById(R.id.pilihan_jumlah);
        ViewGroup.LayoutParams params = pilihan.getLayoutParams();
        if (expanded) {
            params.height = (int)(totalPilihanHeight * (1.0 - interpolatedTime));
        } else {
            params.height = (int)(totalPilihanHeight * interpolatedTime);
        }
        pilihan.setLayoutParams(params);
        pilihan.invalidate();
        pilihan.requestLayout();
    }

    void attachAksiPanelOnClickListener() {
        FloatingActionButton aksiPanel = panel.findViewById(R.id.aksi_panel_tambah);
        aksiPanel.setOnClickListener(v -> {
            int origin = aksiPanel.getRotation() == 0 ? 0 : INDIKATOR_PANEL_ANGLE;
            Button tombolBayar = panel.findViewById(R.id.bayar);

            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    float angle = origin + INDIKATOR_PANEL_ANGLE * interpolatedTime;
                    aksiPanel.setRotation((int)(angle % 360));
                    if (hasStarted) animationForPilihan(interpolatedTime);
                }
            };
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    hasStarted = true;
                    if (expanded) tombolBayar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hasStarted = false;
                    expanded = aksiPanel.getRotation() != 0;
                    if (expanded) tombolBayar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (!hasStarted) aksiPanel.startAnimation(anim);
        });
    }

    void fillListViewPilihan() {
        ArrayList<String> deretPilihan = new ArrayList<>();
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
        pilihan.setOnItemClickListener((parent, view, position, id) -> selected = position);

        Button bayar = panel.findViewById(R.id.bayar);
        bayar.setOnClickListener(v -> {
            if (selected != INVALID_SELECTION) {
                MainActivity activity = (MainActivity) getActivity();
                String dipilih = deretPilihan.get(selected);
                assert activity != null;
                selected = INVALID_SELECTION;
                activity.pembayaranDipilih(Integer.parseInt(dipilih));
            }
        });

        pilihan.measure(0, 0);
        totalPilihanHeight = pilihan.getMeasuredHeight() * deretPilihan.size();
        totalPilihanHeight += pilihan.getDividerHeight() * (deretPilihan.size() - 1);

        if (!expanded) {
            ViewGroup.LayoutParams params = pilihan.getLayoutParams();
            params.height = 0;
            pilihan.setLayoutParams(params);
            pilihan.invalidate();
            pilihan.requestLayout();
            bayar.setVisibility(View.GONE);
        }
    }
}
