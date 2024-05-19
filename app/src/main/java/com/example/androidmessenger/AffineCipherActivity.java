package com.example.androidmessenger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AffineCipherActivity extends AppCompatActivity {

    private EditText inputText;
    private EditText editTextA;
    private EditText editTextB;
    private Button buttonEncrypt;
    private Button buttonDecrypt;

    private Button backButton;
    private Button hack_btn;
    private TextView outputText;
    private static String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine_cipher);

        inputText = findViewById(R.id.inputText);
        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        buttonEncrypt = findViewById(R.id.encode);
        buttonDecrypt = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plainText = inputText.getText().toString();
                if (TextUtils.isEmpty(plainText)) {
                    inputText.setError("Field is empty!");
                } else if (TextUtils.isEmpty(editTextA.getText().toString())) {
                    editTextA.setError("Field is empty!");
                }
                else if (TextUtils.isEmpty(editTextB.getText().toString())){
                    editTextB.setError("Field is empty!");
                }
                else {
                    int a = Integer.parseInt(editTextA.getText().toString());
                    int b = Integer.parseInt(editTextB.getText().toString());
                    String encryptedText = affineEncrypt(plainText, a, b);
                    outputText.setText(encryptedText);
                }
            }
        });

        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipherText = inputText.getText().toString();
                if (TextUtils.isEmpty(cipherText)) {
                    inputText.setError("Field is empty!");
                } else if (TextUtils.isEmpty(editTextA.getText().toString())) {
                    editTextA.setError("Field is empty!");
                }
                else if (TextUtils.isEmpty(editTextB.getText().toString())){
                    editTextB.setError("Field is empty!");
                }
                else {
                    int a = Integer.parseInt(editTextA.getText().toString());
                    int b = Integer.parseInt(editTextB.getText().toString());
                    String decryptedText = affineDecrypt(cipherText, a, b);
                    outputText.setText(decryptedText);
                }
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hack_btn = findViewById(R.id.button_hack);
        hack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               affineBreak();
            }
        });
    }

    private String affineEncrypt(String text, int a, int b) {
        return processAffineCipher(text, a, b, true);
    }

    private String affineDecrypt(String text, int a, int b) {
        return processAffineCipher(text, a, b, false);
    }

    private String processAffineCipher(String text, int a, int b, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String alphabet = determineAlphabet(text);
        int m = alphabet.length();
        int aInverse = modInverse(a, m);

        for (char character : text.toCharArray()) {
            if (alphabet.indexOf(Character.toLowerCase(character)) != -1) {
                int originalIndex = alphabet.indexOf(Character.toLowerCase(character));
                int newIndex = encrypt ?
                        (a * originalIndex + b) % m :
                        (aInverse * (originalIndex - b + m)) % m;
                char newCharacter = alphabet.charAt(newIndex);
                result.append(Character.isUpperCase(character) ? Character.toUpperCase(newCharacter) : newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Обратный элемент не существует");
    }
    private String determineAlphabet(String text) {
        for (char character : text.toCharArray()) {
            if (ENGLISH_ALPHABET.indexOf(Character.toLowerCase(character)) != -1) {
                return ENGLISH_ALPHABET;
            } else if (RUSSIAN_ALPHABET.indexOf(Character.toLowerCase(character)) != -1) {
                return RUSSIAN_ALPHABET;
            }
        }
        return ENGLISH_ALPHABET; // Default to English if no match found
    }

    private void affineBreak() {
        StringBuilder allShiftsDecoded = new StringBuilder();
        int m = 26;
        String text = inputText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Field is empty!");
            return;
        }
        text = text.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ENGLISH_ALPHABET.contains(String.valueOf(c))) {
                m = 26;
            } else if (RUSSIAN_ALPHABET.contains(String.valueOf(c))) {
                m = 33;
            }
            for (int a = 0; a < m; a++) {
                if (gcd(a, m) == 1) {
                    for (int b = 1; b < m; b++) {
                        try {
                            String decryptedText = affineDecrypt(text, a, b);
                            allShiftsDecoded.append(a).append(" a: ").append(b).append(" b: ").append(decryptedText).append("\n");
                        } catch (Exception e) {
                            // Обработка исключения
                        }
                    }
                }
            }
        }
        outputText.setText(allShiftsDecoded.toString());
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


}
