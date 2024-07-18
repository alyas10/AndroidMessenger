package com.example.androidmessenger.modelClass;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.androidmessenger.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ScannerActivity extends AppCompatActivity {
    private AppCompatButton inputImageButton;
    private AppCompatButton recognizedTextBtn;
    private ImageView imageIv;
    private EditText recognizedTextEt;
    private static final String TAG = "MAIN_TAG";
    private Bitmap imageBitmap = null;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;
    private String[] cameraPermission;
    private String[] storagePermission;
    private ProgressDialog progressDialog;
    private TextRecognizer textRecognizer;

    private ActivityResultLauncher<String[]> cameraPermissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vzlom);

        inputImageButton = findViewById(R.id.inputImageButton);
        recognizedTextBtn = findViewById(R.id.recognizedTextBtn);
        imageIv = findViewById(R.id.imageIv);
        recognizedTextEt = findViewById(R.id.recognizedTextEt);

        // Инициализируем переменные разрешений
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Пожалуйста, подождите");
        progressDialog.setCanceledOnTouchOutside(false);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        // Инициализируем активити-лаунчер для запроса разрешения на использование камеры
        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allPermissionsGranted = true;
                    for (Boolean value : result.values()) {
                        allPermissionsGranted &= value;
                    } if (allPermissionsGranted) {
                        openCamera();
                    } else {
                        Toast.makeText(this, "Разрешение на использование камеры отклонено", Toast.LENGTH_SHORT).show();
                    }
                });

        // Инициализируем активити-лаунчер для запуска камеры
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Обработка изображения, полученного из камеры
                        Uri imageUri = (Uri) result.getData().getExtras().get("data");
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            imageBitmap = BitmapFactory.decodeStream(inputStream);
                            imageIv.setImageBitmap(imageBitmap);
                            imageIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            imageIv.setAdjustViewBounds(true);
                            recognizeTextFromImage();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "Не удалось сделать снимок", Toast.LENGTH_SHORT).show();
                    }
                });

        // Инициализируем активити-лаунчер для запуска галереи
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Обработка изображения, выбранного из галереи
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            imageBitmap = BitmapFactory.decodeStream(inputStream);
                            imageIv.setImageBitmap(imageBitmap);
                            imageIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            imageIv.setAdjustViewBounds(true);
                            recognizeTextFromImage();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "Не удалось выбрать изображение", Toast.LENGTH_SHORT).show();
                    }
                });

        inputImageButton.setOnClickListener(v -> showInputImageDialog());

        recognizedTextBtn.setOnClickListener(view -> {
            if (imageBitmap != null) {
                recognizeTextFromImage();
            } else {
                Toast.makeText(ScannerActivity.this, "Сначала выберите изображение...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInputImageDialog() {
        PopupMenu popupMenu = new PopupMenu(this, inputImageButton);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Камера");
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Галерея");
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 0) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionLauncher.launch(cameraPermission);
                } else {
                    openCamera();
                } } else if (item.getItemId() == 1) {
                openGallery();
            }
            return true;
        });
        popupMenu.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void recognizeTextFromImage() {
        Log.d(TAG, "recognizeTextFromImage:");
        progressDialog.setMessage("Подготовка изображения...");
        progressDialog.show();

        try {
            InputImage inputImage = InputImage.fromBitmap(imageBitmap, 0);

            progressDialog.setMessage("Распознавание текста...");
            Task<Text> textRecognitionTask = textRecognizer.process(inputImage)
                    .addOnSuccessListener(text -> {
                        progressDialog.dismiss();
                        String recognizedText = text.getText();
                        recognizedTextEt.setText(recognizedText);
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(ScannerActivity.this, "Не удалось распознать текст: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(ScannerActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}