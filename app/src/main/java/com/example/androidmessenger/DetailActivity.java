package com.example.androidmessenger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Активити для отображения подробной информации о конкретном шифре.
 * @author Иван Минаев
 * @version 1.0
 */
public class DetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle;
    ImageView detailImage;
    /**
     * Вызывается при запуске действия.
     * Этот метод инициализирует представление активити, извлекает данные из intent и устанавливает содержимое представлений.
     *
     * @param savedInstanceState - Если действие повторно инициализируется после предыдущего завершения,
     * то этот пакет содержит данные, которые оно в последний раз предоставляло в onSaveInstanceState().
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        //Извлечь и передать данные через интент
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getInt("Desc"));
            // Извлечь и установить изображение шифра
            Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),bundle.getInt("DescShifr"));
            // Извлечь и установить основное изображение
            Bitmap bitmap2= BitmapFactory.decodeResource(this.getResources(),bundle.getInt("Image"));
            detailImage.setImageBitmap(bitmap2);
            detailTitle.setText(bundle.getString("Title"));

        }
    }
}