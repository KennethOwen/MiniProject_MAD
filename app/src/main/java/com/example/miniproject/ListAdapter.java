package com.example.miniproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> itemList;
    private Context context;
    private DBHelper dbHelper;

    public ListAdapter(List<ListItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListItem item = itemList.get(position);
        holder.itemNameTextView.setText(item.getItemName());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(item.getId());
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemList.size());
                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
