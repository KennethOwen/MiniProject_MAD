package com.example.miniproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Set item data
        holder.itemNameTextView.setText("Name: " + item.getItemName());
        holder.itemPriceTextView.setText("Price: RM " + item.getPrice());
        holder.itemStockTextView.setText("Stocks: " + item.getStock());

        // Load image from byte array
        if (item.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            holder.itemImageView.setImageBitmap(bitmap);
        } else {
            // Set a default image if no image is available
            holder.itemImageView.setImageResource(R.drawable.ayuklogo);
        }

        // Handle add to cart button click
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add item to cart functionality here
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView, itemPriceTextView, itemStockTextView;
        Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemStockTextView = itemView.findViewById(R.id.itemStockTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}

