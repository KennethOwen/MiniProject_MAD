package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button btnLogin;
    TextView toRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.userName);
        passwordInput = findViewById(R.id.passWord);
        btnLogin = findViewById(R.id.btnLogin);
        toRegister = findViewById(R.id.gotoRegister);

        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                boolean userExists = dbHelper.checkUser(username, password);

                if (userExists) {
                    if (username.equals("Admin")) {
                        Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent shopIntent = new Intent(LoginActivity.this, ShopActivity.class);
                        startActivity(shopIntent);
                    }
                } else {
                    // User authentication failed
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
                usernameInput.setText("");
                passwordInput.setText("");
                finish();
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}