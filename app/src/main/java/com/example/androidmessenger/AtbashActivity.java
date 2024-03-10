package com.example.androidmessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AtbashActivity extends AppCompatActivity {
    private EditText inputText;
    private Button encode,decode;
    private TextView outputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atbash);
        inputText = findViewById(R.id.inputText);
        encode = findViewById(R.id.encode);
        decode = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    inputText.setError("Field is empty!");
                }
                else {
                    outputText.setText(encodeText(text));
                }
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    inputText.setError("Field is empty!");
                }
                else {
                    outputText.setText(encodeText(text));
                    ;
                }
            }
        });
    }

    public String encodeText(String str) {
        String encodedText = "";
            for (char c : str.toCharArray()) {
                if (Character.isLetter(c)) {
                    char newChar = (char) ('z' - (c - 'a') % 26);
                    if (Character.isUpperCase(c)) {
                        newChar = Character.toUpperCase(newChar);
                    }
                    encodedText += newChar;
                } else {
                    encodedText += c;
                }
            }
        return encodedText;
    }

    public void goBack(View view) {
        finish();
    }
}