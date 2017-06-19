package com.example.peacock.imagestoreapplication.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;

/**
 * Created by peacock on 4/23/16.
 */

public class MyHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static final int DB_VER = 4;
    public static final String KEY_ID = "id";
    public static final String F_NAME = "fname";
    public static final String L_NAME = "lname";
    public static final String DB_NAME = "mydb2";
    public static final String TAB_NAME = "usertab";
    public static final String TAB_NAME1 = "user";
    public static final String PIC = "image";
    public byte[] data;
    public String imgpath;
    public Bitmap b;

    private static final String CREATE_QUERY = " create table " + TAB_NAME + " ( "
            + KEY_ID + " integer primary key autoincrement , "
            + F_NAME + " varchar(20), "
            + L_NAME + " varchar(20) , "
            + PIC + " blob ) ;";

    private static final String CREATE_QUERY1 = " create table " + TAB_NAME1 + " ( "
            + KEY_ID + " INTEGER primary key autoincrement , "
            + F_NAME + " TEXT, "
            + L_NAME + " TEXT , "
            + PIC + " TEXT ) ;";

    public MyHelper(Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TAB_NAME);
        db.execSQL(" drop table if exists " + TAB_NAME1);
        onCreate(db);
    }

    public void open() {
        db = this.getWritableDatabase();
    }


    public void addInfo(String fname, String lname, byte[] img) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(F_NAME, fname);
        cv.put(L_NAME, lname);
        cv.put(PIC, img);
        db.insert(TAB_NAME, null, cv);
        db.close();
    }

    public void addInf(String fname, String lname, String img) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(F_NAME, fname);
        cv.put(L_NAME, lname);
        cv.put(PIC, img);
        db.insert(TAB_NAME1, null, cv);
        db.close();
    }

    public byte[] fetchsingleimg(String id) {
        Log.e("PATH...", id);
        open();
        Cursor c1 = db.query(TAB_NAME, new String[]{PIC}, KEY_ID + " =? ", new String[]{id}, null, null, null);
        Log.e("PATHENH", String.valueOf(c1.getCount()));
        c1.moveToFirst();
        if (null != c1) {
            do {
                data = c1.getBlob(c1.getColumnIndexOrThrow("image"));
                //ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
                //Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            } while (c1.moveToNext());
        }
        c1.close();
        db.close();
        return data;
    }

    public String fetchimgpath(String id) {
        Log.e("PATH...", id);
        db = this.getWritableDatabase();
        Log.e("PATH...", id);
        Cursor c1 = db.query(TAB_NAME1, new String[]{PIC}, KEY_ID + " =? ", new String[]{id}, null, null, null);
        Log.e("PATHENH", String.valueOf(c1.getCount()));
        if (null != c1) {
            if (c1.moveToFirst()) {
                do {
                    imgpath = c1.getString(c1.getColumnIndexOrThrow("image"));
                    //ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
                    //Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                } while (c1.moveToNext());
            }
        }
        c1.close();
        db.close();
        return imgpath;
    }


    public byte[] getimage(String id) {
        db = this.getReadableDatabase();
        Cursor c1 = db.query(TAB_NAME, new String[]{PIC}, KEY_ID + " =? ",
                new String[]{id}, null, null, null);
        if (null != c1) {
            if (c1.moveToFirst()) {
                do {
                    data = c1.getBlob(c1.getColumnIndex(PIC));
                } while (c1.moveToNext());
            }
        }
        c1.close();
        c1.close();
        return data;
    }

    public Bitmap getImage(String id) {
        db = this.getReadableDatabase();
        Cursor c1 = db.query(TAB_NAME, new String[]{PIC}, KEY_ID + " =? ", new String[]{id}, null, null, null);
        if (null != c1) {
            if (c1.moveToFirst()) {
                do {
                    byte[] imgByte = c1.getBlob(c1.getColumnIndex(PIC));
                    b = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                } while (c1.moveToNext());
            }
        }
        c1.close();
        db.close();
        return b;
    }

    public int check() {
        open();
        Cursor c1 = db.rawQuery(" SELECT * FROM " + TAB_NAME, null);
        int i = c1.getCount();
        c1.close();
        db.close();
        return i;
    }

    public int checkpat() {
        open();
        Cursor c1 = db.rawQuery(" SELECT * FROM " + TAB_NAME1, null);
        int i = c1.getCount();
        c1.close();
        db.close();
        return i;
    }
}
