package com.example.airlines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {

    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnSignup = findViewById(R.id.btnSignup);
        Button btnLoginRedirect = findViewById(R.id.btnLoginRedirect); // New

        dbHelper = new SQLiteHelper(this);

        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.insertUser(username, password)) {
                Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        });

        btnLoginRedirect.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}
