package com.example.androidmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Chats.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, email, password;
    Button register_btn;
    ImageButton back_btn;
    ImageButton ShowHide_btn;
    FirebaseAuth auth;
    DatabaseReference reference;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password.setInputType(129);

        register_btn = findViewById(R.id.sign_up_btn);
        back_btn = findViewById(R.id.back_btn);

        ShowHide_btn = findViewById(R.id.showHideBtn);
        auth = FirebaseAuth.getInstance();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Поле не может быть пустым", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Пароль должен содержать 8 символов", Toast.LENGTH_SHORT).show();
                } else if (isValidPassword(txt_password) != true) {
                    Toast.makeText(RegisterActivity.this, "Пароль должен содержать 1 цифру, 1 заглавную латинскую букву, 1 спецсимвол, не содержать пробелов", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
        ShowHide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переключение между показом и скрытием пароля
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Показать пароль
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ShowHide_btn.setImageResource(R.drawable.baseline_check_24);
                } else {
                    // Скрыть пароль
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ShowHide_btn.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void register(final String username, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Вы будете направлены на страницу атворизации", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Регистрация под таким логином или паролем недоступна", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



         public boolean isValidPassword(String password) {
             String regex = "^(?=.*[0-9])"
                     + "(?=.*[a-z])(?=.*[A-Z])"
                     + "(?=.*[@#$%^&+=?!])"
                     + "(?=\\S+$).{8,20}$";
             Pattern pattern = Pattern.compile(regex);
             Matcher matcher = pattern.matcher(password);
             return matcher.matches();
         }
    }