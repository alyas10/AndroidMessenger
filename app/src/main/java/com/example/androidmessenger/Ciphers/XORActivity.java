package com.example.androidmessenger.Ciphers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmessenger.R;
/**
 * Реализация функций шифрования, дешифрования с помощью шифра гаммирования (XOR).
 * @author Алевтина Ильина
 * @version 1.0
 */
public class XORActivity extends AppCompatActivity {
    private EditText inputText, key;
    private Button encode,decode;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoractivity);
        inputText = findViewById(R.id.inputText);
        key = findViewById(R.id.key);
        encode = findViewById(R.id.encode);
        decode = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = inputText.getText().toString();
                String Key = key.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    inputText.setError("Поле не заполнено!");
                    return;
                } else if (TextUtils.isEmpty(Key)) {
                    key.setError("Поле не заполнено!");
                    return;
                } else {
                    String encryptedText = cipherEncryption(msg, Key);
                    outputText.setText(encryptedText);
                }
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = inputText.getText().toString();
                String Key = key.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    inputText.setError("Поле не заполнено!");
                    return;
                } else if (TextUtils.isEmpty(Key)) {
                    key.setError("Поле не заполнено!");
                    return;
                } else {
                    String decryptedText = cipherDecryption(msg, Key);
                    outputText.setText(decryptedText);
                }
            }
        });
    }
    /**
     * Метод для шифрования текста алгоритмом XOR.
     *
     * @param msg  Исходный текст для шифрования.
     * @param key Ключ для шифрования.
     * @return Зашифрованный текст в шестнадцатеричном формате.
     */
    private String cipherEncryption(String msg, String key) {
        StringBuilder encrypHexa = new StringBuilder();
        int keyItr = 0;
        // Цикл по каждому символу текста
        for (int i = 0; i < msg.length(); i++) {
            // XOR операция над символом текста и символом ключа
            int temp = msg.charAt(i) ^ key.charAt(keyItr);
            // Преобразование результата в шестнадцатеричный формат и добавление в строку
            encrypHexa.append(String.format("%02x", (byte) temp));
            // Переход к следующему символу ключа (с учетом длины ключа)
            keyItr = (keyItr + 1) % key.length();
        }
        return encrypHexa.toString();
    }
    /**
     * Метод для дешифрования текста, зашифрованного алгоритмом XOR.
     *
     * @param encryptedText Зашифрованный текст в шестнадцатеричном формате.
     * @param key           Ключ, использованный для шифрования.
     * @return Дешифрованный текст.
     */
    private String cipherDecryption(String encryptedText, String key) {
        StringBuilder decrypText = new StringBuilder();
        int keyItr = 0;
        // Цикл по парам символов зашифрованного текста (2 символа = 1 байт)
        for (int i = 0; i < encryptedText.length(); i += 2) {
            String output = encryptedText.substring(i, i + 2);
            // Преобразование шестнадцатеричного значения в десятичное
            int decimal = Integer.parseInt(output, 16);
            // XOR операция над десятичным значением и символом ключа
            int temp = decimal ^ key.charAt(keyItr);
            // Преобразование результата в символ и добавление в строку
            decrypText.append((char) temp);
            // Переход к следующему символу ключа (с учетом длины ключа)
            keyItr = (keyItr + 1) % key.length();
        }
        return decrypText.toString();
    }
}