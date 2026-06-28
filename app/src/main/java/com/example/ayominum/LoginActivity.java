package com.example.ayominum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    // Data login default
    private final String DEFAULT_USERNAME = "sera";
    private final String DEFAULT_PASSWORD = "sera123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi View
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Set listener untuk tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi field kosong
        if (username.isEmpty()) {
            etUsername.setError("Username tidak boleh kosong");
            etUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }

        // Validasi username dan password
        if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
            // Login berhasil
            Toast.makeText(LoginActivity.this, "Login Berhasil! Selamat Datang " + username, Toast.LENGTH_SHORT).show();

            // Pindah ke halaman Dashboard
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Menutup activity login agar tidak bisa kembali
        } else {
            // Login gagal
            Toast.makeText(LoginActivity.this, "Login Gagal! Username atau Password salah", Toast.LENGTH_LONG).show();
        }
    }
}