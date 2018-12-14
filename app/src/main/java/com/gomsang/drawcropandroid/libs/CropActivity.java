package com.gomsang.drawcropandroid.libs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.gomsang.drawcropandroid.R;

import java.io.FileOutputStream;
import java.io.IOException;

public class CropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        final DrawCropView drawCropView = findViewById(R.id.cropView);

        Uri data = getIntent().getData();

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawCropView.setImageBitmap(bitmap);
        drawCropView.setOnCropListener(result -> {
            try {
                //Write file
                String filename = "bitmap.png";
                FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
                result.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //Cleanup
                stream.close();
                result.recycle();

                Intent intent = new Intent();
                intent.putExtra("BitmapImage", filename);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
