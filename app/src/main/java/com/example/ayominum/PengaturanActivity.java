package com.example.ayominum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PengaturanActivity extends AppCompatActivity {

    private TextView tvTime, tvBattery, tvTargetHarian, tvIntervalNotifikasi, tvNama, tvEmail;
    private Button btnEdit;
    private LinearLayout navBeranda, navRiwayat, navPengaturan;

    private SharedPreferences sharedPreferences;
    private SharedPreferences userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("WaterData", Context.MODE_PRIVATE);
        userSession = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        // Inisialisasi View
        initViews();

        // Load data
        loadData();

        // Setup click listeners
        setupListeners();
    }

    private void initViews() {
        // Status bar
        tvTime = findViewById(R.id.tv_time);
        tvBattery = findViewById(R.id.tv_battery);

        // Setting items
        tvTargetHarian = findViewById(R.id.tvTargetHarian);
        tvIntervalNotifikasi = findViewById(R.id.tvIntervalNotifikasi);
        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);

        // Button
        btnEdit = findViewById(R.id.btnEdit);

        // Bottom Navigation
        navBeranda = findViewById(R.id.navBeranda);
        navRiwayat = findViewById(R.id.navRiwayat);
        navPengaturan = findViewById(R.id.navPengaturan);
    }

    private void loadData() {
        // Load target harian
        int targetHarian = sharedPreferences.getInt("target_harian", 2170);
        tvTargetHarian.setText(formatNumber(targetHarian) + " ml");

        // Load interval notifikasi
        int interval = sharedPreferences.getInt("interval_notifikasi", 2);
        tvIntervalNotifikasi.setText("Setiap " + interval + " Jam");

        // Load profil user
        String nama = userSession.getString("username", "Seravanya");
        String email = sharedPreferences.getString("user_email", "sera@gmail.com");

        tvNama.setText(nama);
        tvEmail.setText(email);

        // Update status bar (simulasi)
        tvTime.setText("9:41");
        tvBattery.setText("100%");
    }

    private void setupListeners() {
        // Tombol Edit Pengaturan
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        // Bottom Navigation - Beranda
        navBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Bottom Navigation - Riwayat
        navRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, RiwayatMinumActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Bottom Navigation - Pengaturan (sudah di halaman ini)
        navPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PengaturanActivity.this, "Anda sudah di halaman Pengaturan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Pengaturan");

        // Inflate layout dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_pengaturan, null);
        builder.setView(dialogView);

        // Inisialisasi komponen dialog
        EditText etTargetHarian = dialogView.findViewById(R.id.etTargetHarian);
        EditText etIntervalNotifikasi = dialogView.findViewById(R.id.etIntervalNotifikasi);
        EditText etNama = dialogView.findViewById(R.id.etNama);
        EditText etEmail = dialogView.findViewById(R.id.etEmail);

        // Set nilai saat ini
        int targetSekarang = sharedPreferences.getInt("target_harian", 2170);
        int intervalSekarang = sharedPreferences.getInt("interval_notifikasi", 2);
        String namaSekarang = userSession.getString("username", "Seravanya");
        String emailSekarang = sharedPreferences.getString("user_email", "sera@gmail.com");

        etTargetHarian.setText(String.valueOf(targetSekarang));
        etIntervalNotifikasi.setText(String.valueOf(intervalSekarang));
        etNama.setText(namaSekarang);
        etEmail.setText(emailSekarang);

        // Tombol Simpan
        builder.setPositiveButton("Simpan", (dialog, which) -> {
            try {
                int targetBaru = Integer.parseInt(etTargetHarian.getText().toString());
                int intervalBaru = Integer.parseInt(etIntervalNotifikasi.getText().toString());
                String namaBaru = etNama.getText().toString().trim();
                String emailBaru = etEmail.getText().toString().trim();

                // Validasi input
                if (targetBaru <= 0) {
                    Toast.makeText(this, "Target harus lebih dari 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (intervalBaru <= 0) {
                    Toast.makeText(this, "Interval harus lebih dari 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (namaBaru.isEmpty()) {
                    Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Simpan ke SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("target_harian", targetBaru);
                editor.putInt("interval_notifikasi", intervalBaru);
                editor.putString("user_email", emailBaru);
                editor.apply();

                // Simpan nama ke user session
                SharedPreferences.Editor sessionEditor = userSession.edit();
                sessionEditor.putString("username", namaBaru);
                sessionEditor.apply();

                // Update tampilan
                loadData();

                Toast.makeText(this, "Pengaturan berhasil disimpan!", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Masukkan angka yang valid", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    // Method untuk logout
    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Hapus session
                    SharedPreferences.Editor editor = userSession.edit();
                    editor.clear();
                    editor.apply();

                    // Pindah ke Login
                    Intent intent = new Intent(PengaturanActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(this, "Anda telah logout", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private String formatNumber(int number) {
        return String.format("%,d", number).replace(",", ".");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}