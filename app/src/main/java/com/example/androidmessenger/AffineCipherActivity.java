package com.example.androidmessenger;

import android.os.Bundle;
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

    private Button goBackbtn;
    private TextView outputText;
    private static String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
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
                int a = Integer.parseInt(editTextA.getText().toString());
                int b = Integer.parseInt(editTextB.getText().toString());
                String plainText = inputText.getText().toString();
                String encryptedText = affineEncrypt(plainText, a, b);
                outputText.setText(encryptedText);
            }
        });

        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(editTextA.getText().toString());
                int b = Integer.parseInt(editTextB.getText().toString());
                String cipherText = inputText.getText().toString();
                String decryptedText = affineDecrypt(cipherText, a, b);
                outputText.setText(decryptedText);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

}
