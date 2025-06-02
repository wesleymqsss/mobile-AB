package com.example.ajudabrasil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextUsernameRegister;
    private EditText editTextPasswordRegister;
    private Button buttonRegister;
    private DataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        database = new DataBase(this);

        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextUsernameRegister = findViewById(R.id.editTextUsernameRegister);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        String username = editTextUsernameRegister.getText().toString().trim();
        String password = editTextPasswordRegister.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Favor, preencher todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, password);
        long result = database.addUser(user);

        if(result != -1){
            Toast.makeText(this, "Usuario cadastrado com sucesso!"+ result, Toast.LENGTH_LONG).show();
            editTextUsernameRegister.setText(" ");
            editTextPasswordRegister.setText(" ");
        }else {
         Toast.makeText(this, "Error ao cadastrar usuario.", Toast.LENGTH_LONG).show();
        }
    }
}