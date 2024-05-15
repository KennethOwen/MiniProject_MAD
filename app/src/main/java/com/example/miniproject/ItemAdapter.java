package com.example.miniproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> itemList;
    private List<CartItem> cartItemsList;

    public ItemAdapter(List<Item> itemList, List<CartItem> cartItemsList) {
        this.itemList = itemList;
        this.cartItemsList = cartItemsList;
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

        holder.itemNameTextView.setText("Name: " + item.getItemName());
        holder.itemPriceTextView.setText("Price: RM " + item.getPrice());
        holder.itemStockTextView.setText("Stocks: " + item.getStock());

        if (item.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            holder.itemImageView.setImageBitmap(bitmap);
        } else {
            holder.itemImageView.setImageResource(R.drawable.ayuklogo);
        }

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartItem(item, v.getContext());

                Intent intent = new Intent(v.getContext(), CartActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void addCartItem(Item item, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.addCartItem(new CartItem(item.getItemId(), item.getItemName(), item.getPrice(), item.getStock()));
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
