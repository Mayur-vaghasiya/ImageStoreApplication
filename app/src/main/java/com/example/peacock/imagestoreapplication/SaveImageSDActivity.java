package com.example.peacock.imagestoreapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.peacock.imagestoreapplication.DataBaseHelper.MyHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveImageSDActivity extends AppCompatActivity {
    byte img[];
    EditText ed1, ed2;
    ImageView imageView;
    String str1, str2, str3 = null;
    Bitmap bitmap = null;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String fileManagerString, imagePath;
    private String selectedImagePath = "";
    public static int count = 0;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image_sd);

        ed1 = (EditText) findViewById(R.id.et1);
        ed2 = (EditText) findViewById(R.id.et2);
        imageView = (ImageView) findViewById(R.id.iv1);

        // for cemara permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    public void selectimg(View v) {
        final CharSequence[] items = {"Take from Camera", "Select from Gallery", "Cancel"};
        //final CharSequence[] items = {getResources().getResourceName(R.string.Takephoto),getResources().getResourceName(R.string.TakefromGalllery) , getResources().getResourceName(R.string.Cancel)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(SaveImageSDActivity.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.drawable.pk);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take from Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Select from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            }

        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            img = bos.toByteArray();
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpeg");
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            img = bos.toByteArray();
            fo.write(bos.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }

    public void save(View v) {

            /*OutputStream fOut = null;
            String strDirectory = Environment.getExternalStorageDirectory().toString();

            File f = new File(strDirectory, "myimg.JPEG");
            try {
                fOut = new FileOutputStream(f);

                *//**Compress image**//*
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();

                *//*Update image to gallery*//*
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        f.getAbsolutePath(), f.getName(), f.getName());
                Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        //Chech sdcard is plugin or not

       /* Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isSDPresent)
        {
            // yes SD-card is present
        } else {
            // Sorry
        }*/

        OutputStream fOut = null;
        final String dir = Environment.getExternalStorageDirectory().toString() + "/pic1Folder/";
        File newdir = new File(dir);
        newdir.mkdirs();

        // count++;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        //String  currentTimeStamp = dateFormat.format(new Date());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String file = "Myimage" + count + ".jpg";

        file = dir + "IMG" + timeStamp + ".jpg";


        Log.e("Imagepath", file);
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut = new FileOutputStream(newfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    newfile.getAbsolutePath(), newfile.getName(), newfile.getName());
            Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


       /* File filepath = Environment.getExternalStorageDirectory();
        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/SaveImage/");
        dir.mkdirs();
        count++;
        // Create a name for the saved image
        File file = new File(dir, "Myimage" + count + ".jpg");

        // Show a toast message on successful save
        Toast.makeText(SaveImageSDActivity.this, "Image Saved to SD Card",
                Toast.LENGTH_SHORT).show();
        try {

            OutputStream output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output);
            output.flush();
            output.close();
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), file.getName(), file.getName());
            Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        str1 = ed1.getText().toString();
        str2 = ed2.getText().toString();
        MyHelper myhlp = new MyHelper(this);
        myhlp.addInf(str1, str2, file);
        Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();

    }
}
