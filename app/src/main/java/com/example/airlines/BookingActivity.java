package com.example.airlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;
    private String from, to, date;
    private String selectedTime = null;
    private String selectedFlightNumber = null;
    private MaterialCardView cardMorning, cardEvening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dbHelper = new SQLiteHelper(this);

        // Get route details
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        date = getIntent().getStringExtra("date");

        // Initialize views
        TextView tvRoute = findViewById(R.id.tvRoute);
        TextView tvDate = findViewById(R.id.tvDate);
        cardMorning = findViewById(R.id.cardMorning);
        cardEvening = findViewById(R.id.cardEvening);
        Button btnBook = findViewById(R.id.btnBook);

        // Set route details
        tvRoute.setText(from + " → " + to);
        tvDate.setText(date);

        // Generate random flight numbers
        String morningFlight = generateFlightNumber();
        String eveningFlight = generateFlightNumber();

        // Set flight details
        ((TextView) findViewById(R.id.tvMorningFlight)).setText("Flight: " + morningFlight);
        ((TextView) findViewById(R.id.tvEveningFlight)).setText("Flight: " + eveningFlight);

        // Setup flight selection
        cardMorning.setOnClickListener(v -> selectFlight(cardMorning, "09:00", morningFlight));
        cardEvening.setOnClickListener(v -> selectFlight(cardEvening, "18:00", eveningFlight));

        // Setup booking
        btnBook.setOnClickListener(v -> {
            if (selectedTime == null) {
                Toast.makeText(this, "Please select a flight", Toast.LENGTH_SHORT).show();
                return;
            }

            // Navigate to seat selection
            Intent intent = new Intent(this, SeatSelectionActivity.class);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("date", date);
            intent.putExtra("time", selectedTime);
            intent.putExtra("flightNumber", selectedFlightNumber);
            startActivity(intent);
            finish();
        });
    }

    private void selectFlight(MaterialCardView card, String time, String flightNumber) {
        // Reset selection
        cardMorning.setChecked(false);
        cardEvening.setChecked(false);

        // Set new selection
        card.setChecked(true);
        selectedTime = time;
        selectedFlightNumber = flightNumber;
    }

    private String generateFlightNumber() {
        String[] airlines = {"AA", "UA", "DL", "BA"};
        Random random = new Random();
        return airlines[random.nextInt(airlines.length)] + (1000 + random.nextInt(9000));
    }
}
