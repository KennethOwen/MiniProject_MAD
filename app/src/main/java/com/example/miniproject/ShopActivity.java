package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private List<CartItem> cartItemsList;
    private DBHelper dbHelper;

    ImageView cartIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dbHelper = new DBHelper(this);
        itemList = dbHelper.getAllItems();
        cartIcon = findViewById(R.id.cartIcon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize cartItemsList somewhere before passing it to ItemAdapter
        cartItemsList = new ArrayList<>(); // Assuming you initialize it here

        itemAdapter = new ItemAdapter(itemList, cartItemsList); // Pass both itemList and cartItemsList
        recyclerView.setAdapter(itemAdapter);
    }
}
