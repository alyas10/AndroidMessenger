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
 * Реализация функций шифрования, дешифрования шифра Атбаш.
 * @author Алевтина Ильина
 * @version 1.0
 */

public class AtbashActivity extends AppCompatActivity {
    /** Текстовое поле для ввода текста */
    private EditText inputText;
    /** Кнопки для шифрования и дешифрования */
    private Button encode,decode;
    /** Текстовое поле для вывода рещультата */
    private TextView outputText;
    /** Константы для английского и русского алфавитов*/
    private static String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String englishAlphabetUpper = englishAlphabet.toUpperCase();
    private static String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String russianAlphabetUpper = russianAlphabet.toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atbash);
        // Инициализация элементов интерфейса
        inputText = findViewById(R.id.inputText);
        encode = findViewById(R.id.encode);
        decode = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);
        // Обработчик нажатия кнопки "Зашифровать"
        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    inputText.setError("Поле не заполнено!");
                }
                else {
                    outputText.setText(encodeText(text));
                }
            }
        });
        // Обработчик нажатия кнопки "Расшифровать"
        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    inputText.setError("Поле не заполнено!");
                }
                else {
                    outputText.setText(encodeText(text));
                }
            }
        });

    }

    /**
     * Шифрует и дешифрует введенный текст, используя шифр Atbash.
     *
     * @param  str - входной текст.
     * @return  шифрованный или расшифрованный текст.
     */
    public String encodeText(String str) {
        String encodedText = "";
        for (char c : str.toCharArray()) {
            if (Character.isAlphabetic(c) && Character.isLowerCase(c) && englishAlphabet.contains(String.valueOf(c))) {
                encodedText += (char) ('z' - c + 'a');
            }
            else if (Character.isAlphabetic(c) && Character.isUpperCase(c) && englishAlphabetUpper.contains(String.valueOf(c))) {
                encodedText += (char) ('Z' - c + 'А');
            }
            else if (Character.isAlphabetic(c) && Character.isLowerCase(c) && russianAlphabet.contains(String.valueOf(c))) {
                int index = russianAlphabet.length() - russianAlphabet.indexOf(c) -1;
                encodedText += russianAlphabet.charAt(index);
            }
            else if (Character.isAlphabetic(c) && Character.isUpperCase(c) && russianAlphabetUpper.contains(String.valueOf(c))) {
                int index = russianAlphabetUpper.length() - russianAlphabetUpper.indexOf(c) - 1;
                encodedText += russianAlphabetUpper.charAt(index);
            }
            else {
                encodedText += c;
            }
        }
        return encodedText;
    }

    /**
     * Завершает текущее действие и возвращается к предыдущей активити.
     */
    public void goBack(View view) {
        finish();
    }
}