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
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)");
        Log.d("DBHelper", "Database table 'user' created successfully.");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS shoplist(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemName TEXT," +
                "itemPrice DOUBLE," +
                "itemStocks INTEGER," + // Changed from INT to INTEGER
                "itemImage BLOB)");
        Log.d("DBHelper", "Database table 'shoplist' created successfully.");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Changed from id INTEGER PRIMARY KEY to id INTEGER PRIMARY KEY AUTOINCREMENT
                "name TEXT," +
                "price DOUBLE," +
                "stock INTEGER)");
        Log.d("DBHelper", "Database table 'cart' created successfully."); // Changed log message from cart_items to cart

        // Insert admin user
        ContentValues adminValues = new ContentValues();
        adminValues.put("username", "Admin");
        adminValues.put("password", "admin");
        long adminInsertResult = MyDB.insert("user", null, adminValues);
        if (adminInsertResult != -1) {
            Log.d("DBHelper", "Admin user inserted successfully.");
        } else {
            Log.e("DBHelper", "Failed to insert admin user.");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists user");
        Log.d("DBHelper", "Database table 'user' dropped successfully.");

        MyDB.execSQL("drop Table if exists shoplist");
        Log.d("DBHelper", "Database table 'shoplist' dropped successfully.");

        MyDB.execSQL("drop Table if exists cart");
        Log.d("DBHelper", "Database table 'cart' dropped successfully.");

        onCreate(MyDB);
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
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("user", "id" + " = ?", new String[]{String.valueOf(id)});
        MyDB.close();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + "user";
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                User user = new User(id, username);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        MyDB.close();
        return userList;
    }

    // Method to get all items from the database
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + "shoplist";

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("itemName"));
                double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("itemPrice"));
                int itemStock = cursor.getInt(cursor.getColumnIndexOrThrow("itemStocks"));
                // If storing image as BLOB, retrieve byte array
                byte[] itemImage = cursor.getBlob(cursor.getColumnIndexOrThrow("itemImage"));

                // Create new Item object and add to list
                Item item = new Item(itemId, itemName, itemPrice, itemStock, itemImage);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        MyDB.close();

        return itemList;
    }

    public List<ListItem> getAllItemNames() {
        List<ListItem> itemList = new ArrayList<>();

        String selectQuery = "SELECT id, itemName FROM shoplist"; // Adjust the query to select only ID and name

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("id")); // Assuming "id" is the column name for item IDs
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("itemName"));

                // Create new ListItem object with only ID and name
                ListItem item = new ListItem(itemId, itemName);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        MyDB.close();

        return itemList;
    }

    public void addCartItem(CartItem cartItem) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cartItem.getItemName());
        values.put("price", cartItem.getItemPrice());
        values.put("stock", cartItem.getItemStock());
        MyDB.insert("cart", null, values);
        MyDB.close();
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + "cart";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int itemStock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));

                CartItem cartItem = new CartItem(itemId, itemName, itemPrice, itemStock);
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return cartItems;
    }

    // Method to validate user
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"id"},
                "username" + "=? AND " + "password" + "=?",
                new String[]{username, password}, null, null, null);

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // Method to update user password
    public void updatePassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update("user", values, "id" + "=?", new String[]{String.valueOf(userId)});
    }

    // Method to remove item from cart
    public void removeItemFromCart(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "id" + "=?", new String[]{String.valueOf(itemId)});
    }

    public void deleteItem(int itemId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("shoplist", "id = ?", new String[]{String.valueOf(itemId)});
        MyDB.close();
    }

}