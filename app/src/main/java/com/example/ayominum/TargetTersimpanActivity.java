package com.example.ayominum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TargetTersimpanActivity extends AppCompatActivity {

    TextView tvTargetValue;
    Button btnBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_tersimpan);

        tvTargetValue = findViewById(R.id.tv_target_value);
        btnBackToDashboard = findViewById(R.id.btn_back_to_dashboard);

        // Ambil data target dari Activity sebelumnya
        Intent intent = getIntent();
        String target = intent.getStringExtra("TARGET_MINUM");

        if (target != null) {
            tvTargetValue.setText(target + " ml / hari");
        }

        btnBackToDashboard.setOnClickListener(v -> {

            Intent dashboard = new Intent(
                    TargetTersimpanActivity.this,
                    LoginActivity.class
            );

            startActivity(dashboard);
            finish();
        });
    }
}