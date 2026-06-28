package com.example.ayominum;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RiwayatMinumActivity extends AppCompatActivity {

    TextView tvTotalMinum, tvStatus;
    ProgressBar progressBar;
    Button btnTambahMinum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_minum);

        tvTotalMinum = findViewById(R.id.tvTotalMinum);
        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);
        btnTambahMinum = findViewById(R.id.btnTambahMinum);

        // Data contoh
        int targetHarian = 2170;
        int totalMinum = 1500;

        updateData(totalMinum, targetHarian);

        btnTambahMinum.setOnClickListener(v -> {
            Toast.makeText(
                    RiwayatMinumActivity.this,
                    "Fitur tambah minum berhasil ditekan",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void updateData(int totalMinum, int targetHarian) {

        tvTotalMinum.setText(totalMinum + "/" + targetHarian + " ml");

        int progress = (totalMinum * 100) / targetHarian;
        progressBar.setProgress(progress);

        if (totalMinum >= targetHarian) {
            tvStatus.setText("Target Harian Tercapai");
        } else {
            tvStatus.setText("Belum Mencapai Target Harian");
        }
    }
}