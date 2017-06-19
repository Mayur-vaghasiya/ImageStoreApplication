package com.example.peacock.imagestoreapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.peacock.imagestoreapplication.DataBaseHelper.MyHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class getimageActivity extends AppCompatActivity {
    private EditText editid;
    private ImageView imagephoto;
    private Button btngetimage;
    private SQLiteDatabase db;
    private MyHelper dhleper;
    private String id;
    private byte[] img;
    private Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getimage);
        editid = (EditText) findViewById(R.id.Editid);
        imagephoto = (ImageView) findViewById(R.id.imageshow);
        btngetimage = (Button) findViewById(R.id.btnshow);


        dhleper = new MyHelper(this);
        btngetimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = editid.getText().toString().trim();
                Log.e("ID1", id);
                int i = dhleper.check();
                int ID = Integer.parseInt(id);

                /*if (ID <= i) {
                    db = dhleper.getWritableDatabase();
                    Cursor c1 = db.query(dhleper.TAB_NAME, new String[]{dhleper.PIC}, dhleper.KEY_ID + " =? ", new String[]{id}, null, null, null);
                    if (c1.moveToFirst()) {
                        byte[] imgByte = c1.getBlob(c1.getColumnIndex(dhleper.PIC));
                        c1.close();
                        Bitmap theImage = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                        imagephoto.setImageBitmap(theImage);
                        Toast.makeText(getimageActivity.this, "Retrive successfully", Toast.LENGTH_SHORT).show();
                    }
                    if (c1 != null && !c1.isClosed()) {
                        c1.close();
                    }
                    db.close();
                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }*/

                if (ID <= i) {
                    b = dhleper.getImage(id);
                    imagephoto.setImageBitmap(b);
                    Toast.makeText(getimageActivity.this, "Retrive PHOTO successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }

                /*if (ID <= i) {
                    byte[] byteImage = dhleper.fetchsingleimg(id);
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(byteImage);
                    Bitmap theImage1 = BitmapFactory.decodeStream(imageStream);
                    imagephoto.setImageBitmap(theImage1);
                    Toast.makeText(getimageActivity.this, "Retrive successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }*/


               /* if (ID <= i) {
                    byte[] byteImage = dhleper.getimage(id);
                    Bitmap theImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                    imagephoto.setImageBitmap(theImage);
                    Toast.makeText(getimageActivity.this, "Retrive successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }*/


                /*if (ID <= i) {
                    db = dhleper.getWritableDatabase();
                    Cursor c1 = db.rawQuery(" SELECT * FROM " + dhleper.TAB_NAME + " where " + dhleper.KEY_ID + "=?", new String[]{id});
                    if (c1 != null) {
                        c1.moveToFirst();
                        do {
                            img = c1.getBlob(c1.getColumnIndex(MyHelper.PIC));
                        } while (c1.moveToNext());
                    }
                    Bitmap b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                    imagephoto.setImageBitmap(b1);
                    Toast.makeText(getimageActivity.this, "Retrive successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }*/


                /*
               // === use For get path from Database  ===
               int I = dhleper.checkpat();
                if (ID <= I) {
                    String imgpath = dhleper.fetchimgpath(id);
                    File imgFile = new File(imgpath);
                    if (imgFile.exists()) {
                        Bitmap bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imagephoto.setImageBitmap(bMap);
                        Toast.makeText(getimageActivity.this, "Retrive Image successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getimageActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }*/
            }

        });
    }
}
