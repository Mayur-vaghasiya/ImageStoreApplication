package com.example.peacock.imagestoreapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peacock.imagestoreapplication.DataBaseHelper.MyDataBase;

import java.io.ByteArrayOutputStream;

public class Main2Activity extends Activity implements View.OnClickListener {
    private ImageView imageview;
    private Button btninsert;
    private Button btnretrive;
    private TextView textView;
    private MyDataBase mdb;
    private SQLiteDatabase db;
    private Cursor c;
    private byte[] img = null;
    private static final String DATABASE_NAME = "ImageDb.db";
    public static final int DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageview = (ImageView) findViewById(R.id.imageview);
        textView = (TextView) findViewById(R.id.textview1);
        btninsert = (Button) findViewById(R.id.btninsert);
        btnretrive = (Button) findViewById(R.id.btnselect);
        imageview.setImageResource(0);
        btninsert.setOnClickListener(this);
        btnretrive.setOnClickListener(this);
        mdb = new MyDataBase(getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);


        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img = bos.toByteArray();
        db = mdb.getWritableDatabase();
    }

    @Override
    public void onClick(View arg0) {

        if (btninsert == arg0) {
            ContentValues cv = new ContentValues();
            cv.put("image", img);
            db.insert("tableimage", null, cv);
            Toast.makeText(this, "inserted successfully", Toast.LENGTH_SHORT).show();
        } else if (btnretrive == arg0) {
            String[] col = {"image"};
            c = db.query("tableimage", col, null, null, null, null, null);

            if (c != null) {
                c.moveToFirst();
                do {
                    img = c.getBlob(c.getColumnIndex("image"));
                } while (c.moveToNext());
            }
            Bitmap b1 = BitmapFactory.decodeByteArray(img, 0, img.length);

            imageview.setImageBitmap(b1);
            Toast.makeText(this, "Retrive successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
