package com.example.airlines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignupRedirect = findViewById(R.id.btnSignupRedirect); // New

        dbHelper = new SQLiteHelper(this);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignupRedirect.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }
}
