package com.example.androidmessenger.Ciphers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmessenger.R;

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
                    inputText.setError("Field is empty!");
                    return;
                } else if (TextUtils.isEmpty(Key)) {
                    key.setError("Field is empty!");
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
                    inputText.setError("Field is empty!");
                    return;
                } else if (TextUtils.isEmpty(Key)) {
                    key.setError("Field is empty!");
                    return;
                } else {
                    String decryptedText = cipherDecryption(msg, Key);
                    outputText.setText(decryptedText);
                }
            }
        });
    }
    private String cipherEncryption(String msg, String key) {
        StringBuilder encrypHexa = new StringBuilder();
        int keyItr = 0;
        for (int i = 0; i < msg.length(); i++) {
            int temp = msg.charAt(i) ^ key.charAt(keyItr);
            encrypHexa.append(String.format("%02x", (byte) temp));
            keyItr = (keyItr + 1) % key.length();
        }
        return encrypHexa.toString();
    }
    private String cipherDecryption(String encryptedText, String key) {
        StringBuilder decrypText = new StringBuilder();
        int keyItr = 0;
        for (int i = 0; i < encryptedText.length(); i += 2) {
            String output = encryptedText.substring(i, i + 2);
            int decimal = Integer.parseInt(output, 16);
            int temp = decimal ^ key.charAt(keyItr);
            decrypText.append((char) temp);
            keyItr = (keyItr + 1) % key.length();
        }
        return decrypText.toString();
    }
}