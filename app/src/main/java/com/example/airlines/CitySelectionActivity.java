package com.example.airlines;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CitySelectionActivity extends AppCompatActivity {
    private AutoCompleteTextView etFrom, etTo;
    private AutoCompleteTextView etDate;
    private String selectedDate;

    private static final String[] CITIES = new String[] {
        "Delhi (DEL)", "Mumbai (BOM)", "Bangalore (BLR)", "Chennai (MAA)", 
        "Kolkata (CCU)", "Hyderabad (HYD)", "Ahmedabad (AMD)", "Pune (PNQ)",
        "Goa (GOI)", "Jaipur (JAI)", "Lucknow (LKO)", "Kochi (COK)",
        "Thiruvananthapuram (TRV)", "Guwahati (GAU)", "Bhubaneswar (BBI)", "Varanasi (VNS)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        // Initialize views
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        etDate = findViewById(R.id.etDate);
        Button btnSearch = findViewById(R.id.btnSearch);

        // Setup city adapters
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            CITIES
        );
        etFrom.setAdapter(adapter);
        etTo.setAdapter(adapter);

        // Setup date picker
        etDate.setOnClickListener(v -> showDatePicker());

        btnSearch.setOnClickListener(v -> {
            String from = etFrom.getText().toString().trim();
            String to = etTo.getText().toString().trim();

            if (from.isEmpty() || to.isEmpty() || selectedDate == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (from.equals(to)) {
                Toast.makeText(this, "Source and destination cannot be same", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass data to BookingActivity
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                Calendar selectedCal = Calendar.getInstance();
                selectedCal.set(year, month, dayOfMonth);
                
                // Format the date in Indian style (dd-MM-yyyy)
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("en", "IN"));
                selectedDate = sdf.format(selectedCal.getTime());
                etDate.setText(selectedDate);
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        
        // Set maximum date to 6 months from now
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.MONTH, 6);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        
        datePickerDialog.show();
    }
}
