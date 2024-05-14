package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText usernameInput, passwordInput, confirmPasswordInput;
    TextView toLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.userName);
        passwordInput = findViewById(R.id.passWord);
        confirmPasswordInput = findViewById(R.id.conPassword);
        btnRegister = findViewById(R.id.btnRegister);
        toLogin = findViewById(R.id.gotoLogin);

        dbHelper = new DBHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();
                if (password.equals(confirmPassword)){
                    Boolean insert = dbHelper.insertUser(username,password);
                        if(insert==true){
                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    Toast.makeText(RegisterActivity.this, "Password is not same.", Toast.LENGTH_SHORT).show();
                }
                usernameInput.setText("");
                passwordInput.setText("");
                confirmPasswordInput.setText("");
                finish();
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}