package com.example.airlines;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileactivity);

        Button logoutBtn = findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }
}
