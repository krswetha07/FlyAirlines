package com.example.airlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {
    private List<MaterialCardView> seatCards = new ArrayList<>();
    private String selectedSeat = null;
    private String from, to, date, time, flightNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        // Get flight details from intent
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        flightNumber = getIntent().getStringExtra("flightNumber");

        // Set flight details
        TextView tvFlightDetails = findViewById(R.id.tvFlightDetails);
        tvFlightDetails.setText(String.format("%s → %s\n%s %s\nFlight: %s",
            from, to, date, time, flightNumber));

        // Initialize seat grid
        setupSeatGrid();

        // Confirm button
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            if (selectedSeat == null) {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass all details to confirmation screen
            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("flightNumber", flightNumber);
            intent.putExtra("seat", selectedSeat);
            startActivity(intent);
            finish();
        });
    }

    private void setupSeatGrid() {
        String[] seatRows = {"A", "B", "C", "D", "E", "F"};
        int[] seatIds = {
            R.id.seat1A, R.id.seat1B, R.id.seat1C, R.id.seat1D, R.id.seat1E, R.id.seat1F,
            R.id.seat2A, R.id.seat2B, R.id.seat2C, R.id.seat2D, R.id.seat2E, R.id.seat2F,
            R.id.seat3A, R.id.seat3B, R.id.seat3C, R.id.seat3D, R.id.seat3E, R.id.seat3F,
            R.id.seat4A, R.id.seat4B, R.id.seat4C, R.id.seat4D, R.id.seat4E, R.id.seat4F
        };

        for (int i = 0; i < seatIds.length; i++) {
            MaterialCardView seatCard = findViewById(seatIds[i]);
            final String seatNumber = ((i / 6) + 1) + seatRows[i % 6];
            
            seatCard.setOnClickListener(v -> selectSeat(seatCard, seatNumber));
            seatCards.add(seatCard);
        }
    }

    private void selectSeat(MaterialCardView seatCard, String seatNumber) {
        // Reset all seats
        for (MaterialCardView card : seatCards) {
            card.setChecked(false);
        }

        // Select new seat
        seatCard.setChecked(true);
        selectedSeat = seatNumber;
        Toast.makeText(this, "Selected seat: " + seatNumber, Toast.LENGTH_SHORT).show();
    }
} 