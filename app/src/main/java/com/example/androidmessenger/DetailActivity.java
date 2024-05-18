package com.example.androidmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
public class DetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle;
    ImageView detailImage;
    ImageView detailImage1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        detailImage1 = findViewById(R.id.detailImage1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getInt("Desc"));
            Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),bundle.getInt("DescShifr"));
            Bitmap bitmap2= BitmapFactory.decodeResource(this.getResources(),bundle.getInt("Image"));
            detailImage.setImageBitmap(bitmap2);
            detailImage1.setImageBitmap(bitmap);
            detailTitle.setText(bundle.getString("Title"));

        }
    }
}