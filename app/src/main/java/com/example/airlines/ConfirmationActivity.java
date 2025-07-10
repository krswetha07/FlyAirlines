package com.example.airlines;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {
    private ImageView qrCodeImageView;
    private String bookingDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Initialize views
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        TextView routeInfo = findViewById(R.id.tvRouteInfo);
        TextView dateInfo = findViewById(R.id.tvDateInfo);
        TextView timeInfo = findViewById(R.id.tvTimeInfo);
        TextView mealInfo = findViewById(R.id.tvMealInfo);
        TextView basePrice = findViewById(R.id.tvBasePrice);
        TextView totalPrice = findViewById(R.id.tvTotalPrice);
        MaterialButton confirmButton = findViewById(R.id.btnConfirmBooking);
        qrCodeImageView = findViewById(R.id.ivQRCode);

        // Setup toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get flight details from intent
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String flightNumber = intent.getStringExtra("flightNumber");
        String seat = intent.getStringExtra("seat");

        // Set flight details
        if (from != null && to != null) {
            routeInfo.setText(from + " → " + to);
        }
        if (date != null) {
            dateInfo.setText(date);
        }
        if (time != null) {
            timeInfo.setText(time);
        }

        // Set meal preference (default to Veg)
        String meal = "Veg Meal";
        mealInfo.setText("Meal: " + meal);

        // Set price (based on time slot)
        String price = time != null && time.startsWith("18") ? "₹5,499" : "₹4,999";
        basePrice.setText(price);
        
        // Calculate total (base price + ₹500 taxes)
        try {
            double basePriceValue = Double.parseDouble(price.replace("₹", "").replace(",", ""));
            double totalPriceValue = basePriceValue + 500;
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            totalPrice.setText(format.format(totalPriceValue).replace("INR", "₹"));
        } catch (NumberFormatException e) {
            totalPrice.setText(price);
        }

        // Create booking details JSON for QR code
        try {
            JSONObject bookingJson = new JSONObject();
            bookingJson.put("from", from);
            bookingJson.put("to", to);
            bookingJson.put("date", date);
            bookingJson.put("time", time);
            bookingJson.put("flight", flightNumber);
            bookingJson.put("seat", seat);
            bookingJson.put("meal", meal);
            bookingDetails = bookingJson.toString();
            generateQRCode(bookingDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup confirm button
        confirmButton.setOnClickListener(v -> {
            // Save booking to database
            SQLiteHelper dbHelper = new SQLiteHelper(this);
            dbHelper.addBooking(from, to, date, time, flightNumber);
            
            Toast.makeText(this, "Boarding pass downloaded!", Toast.LENGTH_LONG).show();
            // Navigate back to home screen
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        });
    }

    private void generateQRCode(String content) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }
}
