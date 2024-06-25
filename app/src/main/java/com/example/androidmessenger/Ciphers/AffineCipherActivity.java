package com.example.androidmessenger.Ciphers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmessenger.R;

/**
 * Реализация функций шифрования, дешифрования и взлома аффинного шифра.
 * @author Алевтина Ильина
 * @version 1.0
 */

public class AffineCipherActivity extends AppCompatActivity {
    /** Текстовое поле для ввода текста */
    private EditText inputText;
    /** Текстовое поле для ввода числа a */
    private EditText editTextA;
    /** Текстовое поле для ввода числа b */
    private EditText editTextB;
    /** Кнопка для шифрования */
    private Button buttonEncrypt;
    /** Кнопка для дешифрования */
    private Button buttonDecrypt;
    /** Кнопка для возврата на предыдущую активити*/
    private Button backButton;
    /** Кнопка для взлома */
    private Button hack_btn;
    /** Текстовое поле для вывода рещультата */
    private TextView outputText;
    /** Константы для английского и русского алфавитов*/
    private static String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine_cipher);
        // Инициализация элементов интерфейса
        inputText = findViewById(R.id.inputText);
        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        buttonEncrypt = findViewById(R.id.encode);
        buttonDecrypt = findViewById(R.id.decode);
        outputText = findViewById(R.id.outputText);

        // Обработчик нажатия кнопки "Зашифровать"
        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plainText = inputText.getText().toString();
                if (TextUtils.isEmpty(plainText)) {
                    inputText.setError("Поле не заполнено!");
                } else if (TextUtils.isEmpty(editTextA.getText().toString())) {
                    editTextA.setError("Поле не заполнено!");
                }
                else if (TextUtils.isEmpty(editTextB.getText().toString())){
                    editTextB.setError("Поле не заполнено!");
                }
                else {
                    int a = Integer.parseInt(editTextA.getText().toString());
                    int b = Integer.parseInt(editTextB.getText().toString());
                    String encryptedText = affineEncrypt(plainText, a, b);
                    outputText.setText(encryptedText);
                }
            }
        });
        // Обработчик нажатия кнопки "Расшифровать"
        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipherText = inputText.getText().toString();
                if (TextUtils.isEmpty(cipherText)) {
                    inputText.setError("Поле не заполнено!");
                } else if (TextUtils.isEmpty(editTextA.getText().toString())) {
                    editTextA.setError("Поле не заполнено!");
                }
                else if (TextUtils.isEmpty(editTextB.getText().toString())){
                    editTextB.setError("Поле не заполнено!");
                }
                else {
                    int a = Integer.parseInt(editTextA.getText().toString());
                    int b = Integer.parseInt(editTextB.getText().toString());
                    String decryptedText = affineDecrypt(cipherText, a, b);
                    outputText.setText(decryptedText);
                }
            }
        });
        // Обработчик нажатия кнопки "Назад"
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Обработчик нажатия кнопки "Взлом"
        hack_btn = findViewById(R.id.button_hack);
        hack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               affineBreak();
            }
        });
    }

    /**
     * Шифрует введенный текст с помощью аффинного шифра.
     *
     * @param text - текст, подлежащий шифрованию.
     * @param a - первое значение ключа (коэффициент a).
     * @param b - второе значение ключа (константа b).
     * @return   текст после шифрования.
     */
    private String affineEncrypt(String text, int a, int b) {
        return processAffineCipher(text, a, b, true);
    }

    /**
     * Расшифровывает введенный текст с помощью аффинного шифра.
     *
     * @param text - Зашифрованный текст, который необходимо расшифровать.
     * @param a - первое значение ключа (коэффициент).
     * @param b - второе значение ключа (константа).
     * @return  текст после расшифровки.
     */
    private String affineDecrypt(String text, int a, int b) {
        return processAffineCipher(text, a, b, false);
    }

    /**
     * Применяет аффинный шифр к входному тексту.
     *
     * @param text - входной текст (открытый текст или зашифрованный текст).
     * @param a - первое ключевое значение (коэффициент).
     * @param b - второе ключевое значение (константа).
     * @param  encrypt Определяет, следует ли выполнять шифрование (true) или расшифровку (false).
     * @return  преобразованный текст.
     */
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

    /**
     * Вычисляет элемент, обратный целому числу 'a' по отношению к заданному модулю 'm'.
     *
     * @param  a - целое число, для которого вычисляется обратный элемент.
     * @param  m - модуль.
     * @return  обратный элемент к a по модулю m.
     * @throws IllegalArgumentException, если обратный элемент не существует.
     */
    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Обратный элемент не существует");
    }

    /**
     * Определяет алфавит (английский или русский) на основе введенного текста.
     *
     * @param  text - вводимый текст.
     * @return  соответствующий алфавит (ENGLISH_ALPHABET или RUSSIAN_ALPHABET).
     */
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

    /**
     * Пытается взломать аффинный шифр c помощью брутфорса (перебора всех возможных комбинаций).
     */
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

    /**
     * Вычисляет наибольший общий делитель (НОД) двух целых чисел.
     *
     * @param a - первое целое число.
     * @param b - второе целое число.
     * @return  НОД 'a' и 'b'.
     */
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
