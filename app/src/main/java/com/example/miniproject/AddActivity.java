package com.example.miniproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    private EditText itemInput, priceInput, stockInput;
    private Button btnInsert, btnInsertImg;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemInput = findViewById(R.id.itemInput);
        priceInput = findViewById(R.id.priceInput);
        stockInput = findViewById(R.id.stockInput);
        btnInsert = findViewById(R.id.btnInsert);
        btnInsertImg = findViewById(R.id.btnInsertImg);
        imageView = findViewById(R.id.imageView);

        dbHelper = new DBHelper(this);

        btnInsertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertItem() {
        String itemName = itemInput.getText().toString().trim();
        String priceString = priceInput.getText().toString().trim();
        String stockString = stockInput.getText().toString().trim();

        if (itemName.isEmpty() || priceString.isEmpty() || stockString.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bitmap == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);
        int stock = Integer.parseInt(stockString);
        byte[] imageBytes = getBytesFromBitmap(bitmap);

        long result = dbHelper.insertItem(itemName, price, stock, imageBytes);

        if (result != -1) {
            Toast.makeText(this, "Item inserted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish this activity after insertion
        } else {
            Toast.makeText(this, "Failed to insert item", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}