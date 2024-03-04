package com.example.androidmessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CeasesActivity extends AppCompatActivity {

    private EditText inputText, shiftAmount;
    private Button encode,decode;
    private TextView outputText;

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
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        } else {
            int shift = Integer.parseInt(shiftAmount.getText().toString());
            String encodedText = "";

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isUpperCase(c)) {
                    encodedText += (char) ('A' + (c - 'A' + shift) % 26);
                } else if (Character.isLowerCase(c)) {
                    encodedText += (char) ('a' + (c - 'a' + shift) % 26);
                } else {
                    encodedText += c;
                }
            }

            outputText.setText(encodedText);}
    }

    public void decodeText() {
        String text = inputText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        }
        else{
        int shift = Integer.parseInt(shiftAmount.getText().toString());
        String decodedText = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isUpperCase(c)) {
                decodedText += (char) ('A' + (c - 'A' - shift + 26) % 26);
            } else if (Character.isLowerCase(c)) {
                decodedText += (char) ('a' + (c - 'a' - shift + 26) % 26);
            } else {
                decodedText += c;
            }
        }

        outputText.setText(decodedText);}
    }
    public void goBack(View view) {
        finish();
    }
}