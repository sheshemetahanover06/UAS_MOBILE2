package com.example.ayominum;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class TargetMinumActivity extends AppCompatActivity {

    EditText etBeratBadan, etUsia;
    Spinner spinnerJenisKelamin, spinnerAktivitas, spinnerCuaca;
    TextView tvRekomendasiAI;
    MaterialButton btnHitungRekomendasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_minum_harian);

        etBeratBadan = findViewById(R.id.etBeratBadan);
        etUsia = findViewById(R.id.etUsia);
        spinnerJenisKelamin = findViewById(R.id.spinnerJenisKelamin);
        spinnerAktivitas = findViewById(R.id.spinnerAktivitas);
        spinnerCuaca = findViewById(R.id.spinnerCuaca);
        tvRekomendasiAI = findViewById(R.id.tvRekomendasiAI);
        btnHitungRekomendasi = findViewById(R.id.btnHitungRekomendasi);

        // Data Spinner
        String[] jenisKelamin = {"Laki-laki", "Perempuan"};
        String[] aktivitas = {"Ringan", "Sedang", "Berat"};
        String[] cuaca = {"Dingin", "Normal", "Panas"};

        spinnerJenisKelamin.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        jenisKelamin));

        spinnerAktivitas.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        aktivitas));

        spinnerCuaca.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        cuaca));

        btnHitungRekomendasi.setOnClickListener(v -> hitungRekomendasi());
    }

    private void hitungRekomendasi() {

        String beratText = etBeratBadan.getText().toString();

        if (beratText.isEmpty()) {
            tvRekomendasiAI.setText("Masukkan berat badan");
            return;
        }

        double berat = Double.parseDouble(beratText);

        int kebutuhanDasar = (int) (berat * 35);

        String aktivitas =
                spinnerAktivitas.getSelectedItem().toString();

        String cuaca =
                spinnerCuaca.getSelectedItem().toString();

        int tambahanAktivitas = 0;
        int tambahanCuaca = 0;

        switch (aktivitas) {
            case "Ringan":
                tambahanAktivitas = 150;
                break;

            case "Sedang":
                tambahanAktivitas = 300;
                break;

            case "Berat":
                tambahanAktivitas = 500;
                break;
        }

        switch (cuaca) {
            case "Dingin":
                tambahanCuaca = 100;
                break;

            case "Normal":
                tambahanCuaca = 200;
                break;

            case "Panas":
                tambahanCuaca = 400;
                break;
        }

        int total =
                kebutuhanDasar +
                        tambahanAktivitas +
                        tambahanCuaca;

        tvRekomendasiAI.setText(total + " ml / hari");
    }
}