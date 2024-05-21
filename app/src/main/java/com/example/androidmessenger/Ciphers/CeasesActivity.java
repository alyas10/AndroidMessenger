package com.example.androidmessenger.Ciphers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmessenger.R;

public class CeasesActivity extends AppCompatActivity {

    private EditText inputText, shiftAmount;
    private Button encode,decode,hack;
    private TextView outputText;
    private static String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String englishAlphabetUpper = englishAlphabet.toUpperCase();
    private static String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String russianAlphabetUpper = russianAlphabet.toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encrypt_and_decrypt);
        hack = findViewById(R.id.button_hack);
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

        hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shiftAmount.getText().clear();
                bruteForceCaesar();
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
    private  void bruteForceCaesar() {
            String text = inputText.getText().toString();
            if (TextUtils.isEmpty(text)) {
                inputText.setError("Field is empty!");
                return;
            }

            // Предполагаем, что englishAlphabetUpper и russianAlphabetUpper уже определены в вашем классе
            StringBuilder allShiftsDecoded = new StringBuilder();

            // Перебор всех возможных сдвигов для английского и русского алфавитов
            int maxShift = Math.max(englishAlphabetUpper.length(), russianAlphabetUpper.length());
            for (int shift = 0; shift < maxShift; shift++) {
                StringBuilder decodedText = new StringBuilder();

                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (Character.isUpperCase(c) && Character.isAlphabetic(c) && englishAlphabetUpper.contains(String.valueOf(c))) {
                        decodedText.append((char) ('A' + (c - 'A' - shift + 26) % 26));
                    } else if (Character.isLowerCase(c) && Character.isAlphabetic(c) && englishAlphabet.contains(String.valueOf(c))) {
                        decodedText.append((char) ('a' + (c - 'a' - shift + 26) % 26));
                    } else if (Character.isUpperCase(c) && Character.isAlphabetic(c) && russianAlphabetUpper.contains(String.valueOf(c))) {
                        int index = (russianAlphabetUpper.indexOf(c) - shift + 33) % 33;
                        decodedText.append(russianAlphabetUpper.charAt(index));
                    } else if (Character.isLowerCase(c) && Character.isAlphabetic(c) && russianAlphabet.contains(String.valueOf(c))) {
                        int index = (russianAlphabet.indexOf(c) - shift + 33) % 33;
                        decodedText.append(russianAlphabet.charAt(index));
                    } else {
                        decodedText.append(c);
                    }
                }

                allShiftsDecoded.append("Сдвиг ").append(shift).append(": ").append(decodedText).append("\n");
            }

            outputText.setText(allShiftsDecoded.toString());
        }
    public void goBack(View view) {
        finish();
    }
}
