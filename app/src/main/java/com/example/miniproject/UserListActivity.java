package com.example.miniproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnDeleteClickListener, UserAdapter.OnUpdateClickListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private DBHelper dbHelper;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DBHelper(this);
        userList = dbHelper.getAllUsers();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, this, this);
        recyclerView.setAdapter(userAdapter);

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onDeleteClick(User user) {
        dbHelper.deleteUser(user.getId());
        userList.remove(user);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateClick(User user) {
        showUpdatePasswordDialog(user);
    }

    private void showUpdatePasswordDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_password, null);
        builder.setView(dialogView);

        EditText currentPasswordEditText = dialogView.findViewById(R.id.currentPasswordEditText);
        EditText newPasswordEditText = dialogView.findViewById(R.id.newPasswordEditText);
        builder.setPositiveButton("Update", (dialog, which) -> {
            String currentPassword = currentPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();

            if (dbHelper.validateUser(user.getUsername(), currentPassword)) {
                dbHelper.updatePassword(user.getId(), newPassword);
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
