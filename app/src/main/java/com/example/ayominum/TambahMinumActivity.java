package com.example.ayominum;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TambahMinumActivity extends AppCompatActivity {

    EditText editJenisMinuman, editJumlahMinum,
            editWaktuMinum, editCatatan;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_minum);

        editJenisMinuman = findViewById(R.id.editJenisMinuman);
        editJumlahMinum = findViewById(R.id.editJumlahMinum);
        editWaktuMinum = findViewById(R.id.editWaktuMinum);
        editCatatan = findViewById(R.id.editCatatan);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(v -> {

            String jenis = editJenisMinuman.getText().toString().trim();
            String jumlah = editJumlahMinum.getText().toString().trim();
            String waktu = editWaktuMinum.getText().toString().trim();
            String catatan = editCatatan.getText().toString().trim();

            if (TextUtils.isEmpty(jenis) ||
                    TextUtils.isEmpty(jumlah) ||
                    TextUtils.isEmpty(waktu)) {

                Toast.makeText(
                        TambahMinumActivity.this,
                        "Lengkapi data terlebih dahulu",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Toast.makeText(
                    TambahMinumActivity.this,
                    "Data minum berhasil disimpan",
                    Toast.LENGTH_LONG
            ).show();

            // Kosongkan form setelah simpan
            editJenisMinuman.setText("");
            editJumlahMinum.setText("");
            editWaktuMinum.setText("");
            editCatatan.setText("");
        });
    }
}