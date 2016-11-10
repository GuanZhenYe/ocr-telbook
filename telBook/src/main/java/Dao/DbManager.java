package Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbManager {

    private SqliteHelper mSqliteHelper;

    public DbManager(Context context) {
        mSqliteHelper = new SqliteHelper(context);
    }

    public void insertContact(Contact contact) {

        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();

//        ContentValues values=new ContentValues();
//        values.put("name",contact.getName());
//        values.put("company",contact.getCompany());
//
        Log.d("aaa", "insert fail");
        database.execSQL("insert into contact(name,company,telephone,email,business) values(?,?,?,?,?)", new Object[]{
                contact.getName(), contact.getCompany(), contact.getTelephone(), contact.getEmail(), contact.getBusiness()
        });


        Log.d("aaa", "insert success");

        database.close();
    }


    public List<Contact> getContacts() {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from contact", new String[]{});

        List<Contact> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contact contact = new Contact();

            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contact.setCompany(cursor.getString(cursor.getColumnIndex("company")));
            contact.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contact.setBusiness(cursor.getString(cursor.getColumnIndex("business")));
            list.add(contact);
        }
        database.close();
        cursor.close();
        return list;
    }

    public void deleteContact(String name, String tel) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        database.execSQL("DELETE FROM contact WHERE name=? AND telephone=?", new String[]{name, tel});
        database.close();
    }

    //模糊查询
    public List<Contact> fuzzySearchContacts(String name) {
        SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM contact WHERE name LIKE ?", new String[]{"%" + name + "%"});
        List<Contact> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contact contact = new Contact();

            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contact.setCompany(cursor.getString(cursor.getColumnIndex("company")));
            contact.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contact.setBusiness(cursor.getString(cursor.getColumnIndex("business")));
            list.add(contact);
        }
        database.close();
        cursor.close();
        return list;
    }

    public void clearTable() {
        mSqliteHelper.onUpgrade(mSqliteHelper.getWritableDatabase(), 1, 2);
    }
}
