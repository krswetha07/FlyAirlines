package com.example.airlines;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Random;

public class FlightStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_status);

        // Initialize views
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        TextInputEditText etFlightNumber = findViewById(R.id.etFlightNumber);
        MaterialButton btnCheck = findViewById(R.id.btnCheck);
        MaterialButton btnHome = findViewById(R.id.btnHome);
        TextView tvStatus = findViewById(R.id.tvStatus);

        // Setup toolbar navigation
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup check button
        btnCheck.setOnClickListener(v -> {
            String flightNumber = etFlightNumber.getText().toString().trim();
            if (flightNumber.isEmpty()) {
                etFlightNumber.setError("Please enter flight number");
                return;
            }

            // Simulate flight status check
            String[] statuses = {
                "On Time - Departure at scheduled time",
                "Delayed by 30 minutes",
                "Boarding",
                "Departed",
                "Arrived",
                "Scheduled"
            };

            String[] gates = {"A1", "A2", "B1", "B2", "C1", "C2"};
            Random random = new Random();
            String status = statuses[random.nextInt(statuses.length)];
            String gate = gates[random.nextInt(gates.length)];

            // Display status with gate information
            String statusText = String.format("Flight: %s\nStatus: %s\nGate: %s", 
                flightNumber, status, gate);
            tvStatus.setText(statusText);
            tvStatus.setVisibility(TextView.VISIBLE);
        });

        // Setup home button
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
} 