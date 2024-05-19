package com.example.androidmessenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText email, password;

    private TextView goToRegister;
    Button login_btn;
    ImageButton ShowHide_btn;
    FirebaseAuth auth;

    FirebaseUser firebaseUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password.setInputType(129);

        login_btn = findViewById(R.id.login_btn);

        goToRegister = findViewById(R.id.go_to_register_activity_tv);
        ShowHide_btn = findViewById(R.id.showHideBtn);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "Поле не может быть пустым", Toast.LENGTH_SHORT).show();
                } /*else if (!firebaseUser.isEmailVerified()) {
                    sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Для входа на вашу почту были высланы инструкции для подтверждения.", Toast.LENGTH_SHORT).show();
                } */else {
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Аутентификация провалена, попробуйте еще раз", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


                goToRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
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
    }

    private void sendEmailVerification() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        firebaseUser.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(LoginActivity.this, "На вашу почту были высланы инструкции для подтверждения регистрации", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Возникла ошибка отправки, попробуйте еще раз "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
