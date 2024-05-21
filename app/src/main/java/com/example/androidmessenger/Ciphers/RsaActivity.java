package com.example.androidmessenger.Ciphers;

import static com.example.androidmessenger.R.id.outputText;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmessenger.R;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class RsaActivity extends AppCompatActivity {
    private EditText inputText;
    private Button encryptButton, decryptButton,back_btn,showPublicKeyButton, showPrivateKeyButton;
    private TextView resultText;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] encryptedData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rsa);
        inputText = findViewById(R.id.inputText);
        encryptButton = findViewById(R.id.encode);
        decryptButton = findViewById(R.id.decode);
        resultText = findViewById(outputText);
        back_btn = findViewById(R.id.backButton);
        showPublicKeyButton = findViewById(R.id.showPublicKeyButton);
        showPrivateKeyButton = findViewById(R.id.showPrivateKeyButton);
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputText.getText().toString();
                try {
                    Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                    encryptedData = cipher.doFinal(input.getBytes());
                    String encryptedString = Base64.encodeToString(encryptedData, Base64.DEFAULT);
                    resultText.setText(encryptedString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
                    cipher.init(Cipher.DECRYPT_MODE, privateKey);
                    String encryptedString = Base64.encodeToString(encryptedData, Base64.DEFAULT);
                    byte[] decodedData = Base64.decode(encryptedString, Base64.DEFAULT);
                    byte[] decryptedData = cipher.doFinal(decodedData);
                    resultText.setText(new String(decryptedData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        showPublicKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(publicKey.toString());
            }
        });

        showPrivateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(privateKey.toString());
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}