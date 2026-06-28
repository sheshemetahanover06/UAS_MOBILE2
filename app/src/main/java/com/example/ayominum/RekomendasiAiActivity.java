package com.example.ayominum;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class RekomendasiAiActivity extends AppCompatActivity {

    TextView tvTargetUtama, tvBeratBadan, tvAktivitas,
            tvCuaca, tvTotalKebutuhan;

    MaterialButton btnSimpanTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi_ai);

        // Inisialisasi komponen
        tvTargetUtama = findViewById(R.id.tvTargetUtama);
        tvBeratBadan = findViewById(R.id.tvBeratBadan);
        tvAktivitas = findViewById(R.id.tvAktivitas);
        tvCuaca = findViewById(R.id.tvCuaca);
        tvTotalKebutuhan = findViewById(R.id.tvTotalKebutuhan);
        btnSimpanTarget = findViewById(R.id.btnSimpanTarget);

        // Contoh data pengguna
        int beratBadan = 42;
        String aktivitas = "Sedang";
        String cuaca = "Panas";

        // Perhitungan AI sederhana
        int kebutuhanDasar = beratBadan * 35; // 42 x 35 = 1470

        int tambahanAktivitas = 0;
        if (aktivitas.equalsIgnoreCase("Ringan")) {
            tambahanAktivitas = 150;
        } else if (aktivitas.equalsIgnoreCase("Sedang")) {
            tambahanAktivitas = 300;
        } else if (aktivitas.equalsIgnoreCase("Berat")) {
            tambahanAktivitas = 500;
        }

        int tambahanCuaca = 0;
        if (cuaca.equalsIgnoreCase("Dingin")) {
            tambahanCuaca = 100;
        } else if (cuaca.equalsIgnoreCase("Normal")) {
            tambahanCuaca = 200;
        } else if (cuaca.equalsIgnoreCase("Panas")) {
            tambahanCuaca = 400;
        }

        int totalKebutuhan = kebutuhanDasar
                + tambahanAktivitas
                + tambahanCuaca;

        // Menampilkan hasil
        tvTargetUtama.setText(totalKebutuhan + " ml / hari");

        tvBeratBadan.setText(
                "Berat badan (" + beratBadan + "kg x 35ml)"
        );

        tvAktivitas.setText(
                "Tingkat aktivitas (" + aktivitas + ")"
        );

        tvCuaca.setText(
                "Cuaca / suhu (" + cuaca + ")"
        );

        tvTotalKebutuhan.setText(
                "= " + totalKebutuhan + " ml"
        );

        // Tombol Simpan
        btnSimpanTarget.setOnClickListener(v -> {
            Toast.makeText(
                    RekomendasiAiActivity.this,
                    "Target berhasil disimpan!",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}