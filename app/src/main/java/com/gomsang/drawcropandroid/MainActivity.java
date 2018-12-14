package com.gomsang.drawcropandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gomsang.drawcropandroid.libs.CropActivity;

import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int CROP_IMAGE = 2;

    ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectImageButton = findViewById(R.id.select_image);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        resultImage = findViewById(R.id.result_image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Intent cropIntent = new Intent(MainActivity.this, CropActivity.class);
            cropIntent.setData(data.getData());
            startActivityForResult(cropIntent, CROP_IMAGE);
        }
        else if (requestCode == CROP_IMAGE) {
            String filename = data.getStringExtra("BitmapImage");
            Bitmap bitmap = null;
            try {
                FileInputStream is = this.openFileInput(filename);
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultImage.setImageBitmap(bitmap);
        }
    }
}