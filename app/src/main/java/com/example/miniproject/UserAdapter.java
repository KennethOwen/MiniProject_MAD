package com.example.miniproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnUpdateClickListener onUpdateClickListener;

    public UserAdapter(List<User> userList, OnDeleteClickListener onDeleteClickListener, OnUpdateClickListener onUpdateClickListener) {
        this.userList = userList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());

        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(user);
            }
        });

        holder.updateButton.setOnClickListener(v -> {
            if (onUpdateClickListener != null) {
                onUpdateClickListener.onUpdateClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        Button deleteButton;
        Button updateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            updateButton = itemView.findViewById(R.id.updateButton);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(User user);
    }

    public interface OnUpdateClickListener {
        void onUpdateClick(User user);
    }
}