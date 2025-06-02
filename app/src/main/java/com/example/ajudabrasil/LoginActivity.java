package com.example.ajudabrasil;

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

public class LoginActivity extends AppCompatActivity {

    private DataBase database;
    private EditText editTextUsernameLogin;
    private EditText editTextPasswordLogin;
    private Button buttonLogin;
    private TextView textViewGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        database = new DataBase(this);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        editTextUsernameLogin = findViewById(R.id.editTextUsernameLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewGoToRegister = findViewById(R.id.textViewGoToRegister);
        textViewGoToRegister = findViewById(R.id.textViewGoToRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });

        textViewGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String username = editTextUsernameLogin.getText().toString().trim();
        String password = editTextPasswordLogin.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Favor, preencher todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = database.checkUser(username, password);

        if (user != null) {
            Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
                Toast.makeText(this, "Login falhou. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
        }
    }
}