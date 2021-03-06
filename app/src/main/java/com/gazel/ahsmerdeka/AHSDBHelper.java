package com.gazel.ahsmerdeka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AHSDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "ahs.db";
    private static int DB_VERSION = 1;


    public AHSDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + CustomerContract.CustomerTable.TABLE_NAME +
                " (" +
                CustomerContract.CustomerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CustomerContract.CustomerTable.COLUMN_NAME + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_ADDRESS + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_PHONE + " TEXT" +
                ") ";

        db.execSQL(CREATE_TABLE_CUSTOMERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCustomers(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CustomerContract.CustomerTable.COLUMN_NAME, customer.getName());
        cv.put(CustomerContract.CustomerTable.COLUMN_ADDRESS, customer.getAddress());
        cv.put(CustomerContract.CustomerTable.COLUMN_PHONE, customer.getPhone());

        db.insert(CustomerContract.CustomerTable.TABLE_NAME, null, cv);
    }

    public void updateCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CustomerContract.CustomerTable.COLUMN_NAME, customer.getName());
        cv.put(CustomerContract.CustomerTable.COLUMN_ADDRESS, customer.getAddress());
        cv.put(CustomerContract.CustomerTable.COLUMN_PHONE, customer.getPhone());

        String[] whereArgs = { String.valueOf(customer.getId()) };

        db.update(CustomerContract.CustomerTable.TABLE_NAME, cv, CustomerContract.CustomerTable.COLUMN_ID + " = ?", whereArgs);
    }

    public  void  deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = { String.valueOf(id) };

        db.delete(CustomerContract.CustomerTable.TABLE_NAME, CustomerContract.CustomerTable.COLUMN_ID + " = ?", whereArgs);
    }

    public Customer getCustomer(int id) {
        Customer customer = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                CustomerContract.CustomerTable.COLUMN_NAME,
                CustomerContract.CustomerTable.COLUMN_ID,
                CustomerContract.CustomerTable.COLUMN_ADDRESS,
                CustomerContract.CustomerTable.COLUMN_PHONE,
        };
        String selection = CustomerContract.CustomerTable.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                CustomerContract.CustomerTable.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_NAME));
            int _id   = cursor.getInt(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ID));
            String address = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_PHONE));

            customer = new Customer();
            customer.setName(name);
            customer.setId(_id);
            customer.setAddress(address);
            customer.setPhone(phone);
        }
        return customer;
    }



    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                CustomerContract.CustomerTable.COLUMN_NAME,
                CustomerContract.CustomerTable.COLUMN_ID,
                CustomerContract.CustomerTable.COLUMN_ADDRESS,
                CustomerContract.CustomerTable.COLUMN_PHONE,
        };

        String selection = "";

        Cursor cursor = db.query(
                CustomerContract.CustomerTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_NAME));
                int id   = cursor.getInt(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ID));
                String address = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_PHONE));

                Customer customer = new Customer();
                customer.setName(name);
                customer.setId(id);
                customer.setAddress(address);
                customer.setPhone(phone);

                customers.add(customer);
            } while (cursor.moveToNext());
        }
        return customers;
    }
}
