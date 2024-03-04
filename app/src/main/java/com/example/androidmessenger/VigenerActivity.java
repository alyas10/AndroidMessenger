package com.example.androidmessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class VigenerActivity extends AppCompatActivity {
    private EditText inputText, key;
    private Button encode,decode;
    private TextView outputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigener);
        inputText = findViewById(R.id.inputText);
        key = findViewById(R.id.key);
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
        String s_key = key.getText().toString();
        String encodedText = "";
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        } else if (TextUtils.isEmpty(s_key)) {
            key.setError("Field is empty!");
            return;
        } else {
            for (int i = 0, j = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        encodedText += (char) ((((c + s_key.toUpperCase().charAt(j) - 2 * 'A') % 26) + 'A'));
                    } else {
                        encodedText += (char) ((((c + s_key.toLowerCase().charAt(j) - 2 * 'a') % 26) + 'a'));
                    }
                    j = ++j % s_key.length();
                } else {
                    encodedText += c;
                }
            }
        }
            outputText.setText(encodedText);
    }

    public void decodeText() {
        String text = inputText.getText().toString();
        String s_key = key.getText().toString();
        String decodedText = "";
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        } else if (TextUtils.isEmpty(s_key)) {
            key.setError("Field is empty!");
            return;
        } else {
            for (int i = 0, j = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        decodedText += (char) ((((c - s_key.toUpperCase().charAt(j) + 26) % 26) + 'A'));
                    } else {
                        decodedText += (char) ((((c - s_key.toLowerCase().charAt(j) + 26) % 26) + 'a'));
                    }
                    j = ++j % s_key.length();
                } else {
                    decodedText += c;
                }
            }
        }
        outputText.setText(decodedText);
    }

    public void goBack(View view) {
        finish();
    }
}