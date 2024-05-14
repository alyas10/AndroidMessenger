package com.example.androidmessenger;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CeasesActivity extends AppCompatActivity {

    private EditText inputText, shiftAmount;
    private Button encode,decode;
    private TextView outputText;
    private static String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String englishAlphabetUpper = englishAlphabet.toUpperCase();
    private static String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String russianAlphabetUpper = russianAlphabet.toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encrypt_and_decrypt);

        inputText = findViewById(R.id.inputText);
        shiftAmount = findViewById(R.id.shiftAmount);
        encode = findViewById(R.id.encode);
        decode = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeText();
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodeText();
            }
        });
    }

    public void encodeText() {
        String text = inputText.getText().toString();
        String s = shiftAmount.getText().toString();
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        } else if (TextUtils.isEmpty(s)) {
            shiftAmount.setError("Field is empty!");
            return;
        } else {
            int shift = Integer.parseInt(shiftAmount.getText().toString());
            String encodedText = "";

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isUpperCase(c)  && Character.isAlphabetic(c) && englishAlphabetUpper.contains(String.valueOf(c))) {
                    encodedText += (char) ('A' + (c - 'A' + shift) % 26);
                } else if (Character.isLowerCase(c) && Character.isAlphabetic(c) && englishAlphabet.contains(String.valueOf(c))) {
                    encodedText += (char) ('a' + (c - 'a' + shift) % 26);
                }
                else if (Character.isUpperCase(c)  && Character.isAlphabetic(c) && russianAlphabetUpper.contains(String.valueOf(c))) {
                    int index = (russianAlphabetUpper.indexOf(c) + shift) % 32;
                    encodedText += russianAlphabetUpper.charAt(index);
                }
                else if (Character.isLowerCase(c)  && Character.isAlphabetic(c) && russianAlphabet.contains(String.valueOf(c))) {
                    int index = (russianAlphabet.indexOf(c) + shift) % 32;
                    encodedText += russianAlphabet.charAt(index);
                }
                else {
                    encodedText += c;
                }
            }

            outputText.setText(encodedText);}
    }

    public void decodeText() {
        String text = inputText.getText().toString();
        String s = shiftAmount.getText().toString();
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        }
        else if (TextUtils.isEmpty(s)) {
            shiftAmount.setError("Field is empty!");
            return;
        }
        else{
        int shift = Integer.parseInt(shiftAmount.getText().toString());
        String decodedText = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isUpperCase(c)  && Character.isAlphabetic(c) && englishAlphabetUpper.contains(String.valueOf(c))) {
                decodedText += (char) ('A' + (c - 'A' - shift + 26) % 26);
            } else if (Character.isLowerCase(c) && Character.isAlphabetic(c) && englishAlphabet.contains(String.valueOf(c))) {
                decodedText += (char) ('a' + (c - 'a' - shift + 26) % 26);
            }
            else if (Character.isUpperCase(c)  && Character.isAlphabetic(c) && russianAlphabetUpper.contains(String.valueOf(c))) {
                 int index = (russianAlphabetUpper.indexOf(c) - shift + 33) % 33;
                 decodedText += russianAlphabetUpper.charAt(index);
                }
            else if (Character.isLowerCase(c)  && Character.isAlphabetic(c) && russianAlphabet.contains(String.valueOf(c))) {
                int index = (russianAlphabet.indexOf(c) - shift + 33) % 33;
                decodedText += russianAlphabet.charAt(index);
            }
            else {
                decodedText += c;
            }
        }

        outputText.setText(decodedText);}
    }
    public void goBack(View view) {
        finish();
    }
}
