package com.todosalau.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.todosalau.chatapp.model.LoginModel;
import com.todosalau.chatapp.presenter.LoginPresenter;
import com.todosalau.chatapp.view.LoginContract;

public class Login extends AppCompatActivity implements LoginContract.View {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private TextView registerText;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.emailEditTextLogin);
        editTextPassword = findViewById(R.id.passwordEditTextLogin);
        btnLogin = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        presenter = new LoginPresenter(this, new LoginModel());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginUser();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegistration();
            }
        });

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getEmail() {
        return editTextEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return editTextPassword.getText().toString().trim();
    }

    private void navigateToRegistration() {
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
        finish();
    }
}