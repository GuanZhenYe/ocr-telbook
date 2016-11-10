package Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "contacts.db";
    private static final int VERSON = 1;

    public SqliteHelper(Context context) {
        super(context, DBNAME, null, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS contact(_id integer primary key autoincrement," +
                "name text ,company text,telephone text,email text,business text)";
        db.execSQL(sql);
        Log.d("aaa","create success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists contact";
        db.execSQL(sql);

        String sql1 = "CREATE TABLE IF NOT EXISTS contact(_id integer primary key autoincrement," +
                "name text ,company text,telephone text,email text,business text)";
        db.execSQL(sql1);
    }
}
