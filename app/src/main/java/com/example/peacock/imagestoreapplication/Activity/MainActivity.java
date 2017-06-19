package com.example.peacock.imagestoreapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.peacock.imagestoreapplication.MyFileContentProvider;
import com.example.peacock.imagestoreapplication.R;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private final int CAMERA_RESULT = 1;
    private final String Tag = getClass().getName();
    Button button1;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        button1.setOnClickListener(this);
    }
    public void onClick(View v) {
        PackageManager pm = getPackageManager();

        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
            startActivityForResult(i, CAMERA_RESULT);
        } else {
            Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
        }
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(Tag, "Receive the camera result");
        if (resultCode == RESULT_OK && requestCode == CAMERA_RESULT) {
            File out = new File(getFilesDir(), "newImage.jpg");
            if (!out.exists()) {
                Toast.makeText(getBaseContext(),"Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            imageView1.setImageBitmap(mBitmap);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView1 = null;
    }
}
