package com.example.miniproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "shop.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)");
        Log.d("DBHelper", "Database table 'user' created successfully.");

        MyDB.execSQL("create Table shoplist(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemName TEXT," +
                "itemPrice DOUBLE," +
                "itemStocks INT," +
                "itemImage BLOB)");
        Log.d("DBHelper", "Database table 'shoplist' created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists user");
        Log.d("DBHelper", "Database table 'translations' dropped successfully.");

        MyDB.execSQL("drop Table if exists shoplist");
        Log.d("DBHelper", "Database table 'translations' dropped successfully.");
    }

    public boolean insertUser(String usernameText, String passwordText) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", usernameText);
        contentValues.put("password", passwordText);
        long result = MyDB.insert("user", null, contentValues);
        MyDB.close();
        if (result == -1) {
            Log.e("DBHelper", "Failed to insert user into database.");
            return false;
        } else {
            Log.d("DBHelper", "User inserted successfully into database.");
            return true;
        }
    }

    public long insertItem(String itemName, double price, int stock, byte[] image) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("itemName", itemName);
        values.put("itemPrice", price);
        values.put("itemStocks", stock);
        values.put("itemImage", image);
        long id = MyDB.insert("shoplist", null, values);
        MyDB.close();
        return id;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        return userExists;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", "id" + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + "user";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                User user = new User(id, username);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Method to get all items from the database
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + "shoplist";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("itemName"));
                double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("itemPrice"));
                int itemStock = cursor.getInt(cursor.getColumnIndexOrThrow("itemStocks"));
                // If storing image as BLOB, retrieve byte array
                byte[] itemImage = cursor.getBlob(cursor.getColumnIndexOrThrow("itemImage"));

                // Create new Item object and add to list
                Item item = new Item(itemName, itemPrice, itemStock, itemImage);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        return itemList;
    }

}