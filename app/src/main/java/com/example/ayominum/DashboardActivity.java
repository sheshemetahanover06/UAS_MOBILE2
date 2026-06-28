package com.example.ayominum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    // Komponen UI
    private TextView tvTime, tvBattery, tvWaterProgress, tvPercentage, tvRemainingTarget, tvTimerReminder, tvStatusHydration;
    private ProgressBar progressBar;
    private Button btnAddWater;
    private CardView btnReminder;

    // Data target dan progres
    private int targetHarian = 2170; // ml
    private int totalMinum = 1250; // ml (default dari layout)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi View
        initViews();

        // Load data dari SharedPreferences
        loadData();

        // Setup data awal
        setupData();

        // Setup click listeners
        setupListeners();

        // Update jam dan baterai (simulasi)
        updateTimeAndBattery();
    }

    private void initViews() {
        // Status bar
        tvTime = findViewById(R.id.tv_time);
        tvBattery = findViewById(R.id.tv_battery);

        // Progress section
        tvWaterProgress = findViewById(R.id.tv_water_progress);
        progressBar = findViewById(R.id.progress_bar);
        tvPercentage = findViewById(R.id.tv_percentage);
        tvRemainingTarget = findViewById(R.id.tv_remaining_target);

        // Cards
        btnReminder = findViewById(R.id.btn_reminder);
        tvTimerReminder = findViewById(R.id.tv_timer_reminder);
        tvStatusHydration = findViewById(R.id.tv_status_hydration);

        // Button
        btnAddWater = findViewById(R.id.btn_add_water);
    }

    private void setupData() {
        // Hitung sisa target
        int sisaTarget = targetHarian - totalMinum;
        if (sisaTarget < 0) sisaTarget = 0;

        // Hitung persentase
        int persentase = (totalMinum * 100) / targetHarian;
        if (persentase > 100) persentase = 100;

        // Update tampilan
        tvWaterProgress.setText(formatNumber(totalMinum) + " / " + formatNumber(targetHarian) + " ml");
        progressBar.setProgress(persentase);
        tvPercentage.setText(persentase + "% Tercapai");
        tvRemainingTarget.setText(formatNumber(sisaTarget) + " ml lagi");

        // Update status hidrasi berdasarkan persentase
        updateHydrationStatus(persentase);
    }

    private void updateHydrationStatus(int persentase) {
        String status;
        int color;

        if (persentase < 30) {
            status = "Kurang Terhidrasi";
            color = Color.parseColor("#F44336"); // Merah
        } else if (persentase < 50) {
            status = "Cukup";
            color = Color.parseColor("#FF9800"); // Oranye
        } else if (persentase < 70) {
            status = "Baik";
            color = Color.parseColor("#2196F3"); // Biru
        } else if (persentase < 90) {
            status = "Baik Sekali";
            color = Color.parseColor("#4CAF50"); // Hijau
        } else {
            status = "Sangat Baik";
            color = Color.parseColor("#2E7D32"); // Hijau tua
        }

        tvStatusHydration.setText(status);
        tvStatusHydration.setTextColor(color);
    }

    private void setupListeners() {
        // Tombol Tambah Minum
        btnAddWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke halaman Tambah Minum
                Intent intent = new Intent(DashboardActivity.this, TambahMinumActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        // Card Pengingat (Notifikasi)
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReminderDialog();
            }
        });
    }

    private void showReminderDialog() {
        // Simulasi dialog pengingat
        Toast.makeText(this, "⏰ Pengingat: Jangan lupa minum air setiap 2 jam!", Toast.LENGTH_LONG).show();
    }

    private void updateTimeAndBattery() {
        // Update jam (real time)
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                tvTime.setText(currentTime);
                handler.postDelayed(this, 60000); // Update setiap menit
            }
        }, 0);

        // Simulasi baterai (akan diisi dari sistem nantinya)
        tvBattery.setText("100%");
    }

    // Method untuk menambah minum (dipanggil dari TambahMinumActivity)
    public void addWater(int jumlahMl) {
        totalMinum += jumlahMl;

        // Cek jika melebihi target
        if (totalMinum > targetHarian) {
            totalMinum = targetHarian;
            Toast.makeText(this, "🎉 Selamat! Target harian tercapai!", Toast.LENGTH_LONG).show();
        }

        // Update tampilan
        setupData();

        // Simpan ke SharedPreferences
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("WaterData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("total_minum", totalMinum);
        editor.putInt("target_harian", targetHarian);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("WaterData", Context.MODE_PRIVATE);
        totalMinum = sharedPreferences.getInt("total_minum", 1250);
        targetHarian = sharedPreferences.getInt("target_harian", 2170);
    }

    private String formatNumber(int number) {
        return String.format("%,d", number).replace(",", ".");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                int jumlahMl = data.getIntExtra("jumlah_ml", 0);
                addWater(jumlahMl);
                Toast.makeText(this, "+" + jumlahMl + " ml air ditambahkan!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Load data terbaru saat halaman dibuka kembali
        setupData(); // Update tampilan
    }
}