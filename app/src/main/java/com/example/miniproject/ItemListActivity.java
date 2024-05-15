package com.example.miniproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private DBHelper dbHelper;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.itemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use a simple linear layout manager

        dbHelper = new DBHelper(this);
        List<ListItem> itemList = dbHelper.getAllItemNames(); // Fetching the list of items with only ID and name

        listAdapter = new ListAdapter(itemList, this);
        recyclerView.setAdapter(listAdapter);
    }
}
