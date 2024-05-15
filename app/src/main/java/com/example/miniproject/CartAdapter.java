package com.example.miniproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;
    private Context context;

    public CartAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemPriceTextView;
        public TextView itemStockTextView;
        public Button removeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemStockTextView = itemView.findViewById(R.id.itemStockTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem cartItem = cartItems.get(position);
        holder.itemNameTextView.setText(cartItem.getItemName());
        holder.itemPriceTextView.setText("Price: RM" + cartItem.getItemPrice());
        holder.itemStockTextView.setText("Stock: " + cartItem.getItemStock());

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove item from the cart
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}