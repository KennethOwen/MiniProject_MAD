package com.example.miniproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItems = new ArrayList<>();

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Retrieve cart items from the database
        cartItems.addAll(dbHelper.getAllCartItems());

        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass dbHelper to CartAdapter
        cartAdapter = new CartAdapter(cartItems, dbHelper);
        recyclerView.setAdapter(cartAdapter);

        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckoutConfirmation();
            }
        });
    }

    public void checkout(View view) {
        showCheckoutConfirmation();
    }

    private void showCheckoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thanks For Purchasing")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Perform any additional action after checkout
                        finish(); // Finish the activity after checkout
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
