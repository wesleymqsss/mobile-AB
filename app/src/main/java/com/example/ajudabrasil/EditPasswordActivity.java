package com.example.ajudabrasil;

import android.content.Intent;
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

public class EditPasswordActivity extends AppCompatActivity {

    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmNewPassword;
    private Button buttonSavePassword;
    private DataBase database;
    private String currentUserName;
    private int currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_password);

        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        buttonSavePassword = findViewById(R.id.buttonSavePassword);
        database = new DataBase(this);

        Intent intent = getIntent();
        currentUserId = intent.getIntExtra("USER_ID", -1);
        currentUserName = intent.getStringExtra("USER_NAME");

        if(currentUserId == -1 || currentUserName == null || currentUserName.isEmpty()){
            Toast.makeText(this, "Error: dados do usuario nao encontrados", Toast.LENGTH_LONG).show();
            finish(); //fecha activity se nao tiver os dados necessarios
            return;
        }

        buttonSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePassword();
            }
        });
    }

    public void savePassword() {
        String currentPassword = editTextCurrentPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Há Campos vazios! Por favor, preencha os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPassword.equals(confirmNewPassword)){
            Toast.makeText(this, "A nova senha e a confirmação não são iguais.", Toast.LENGTH_SHORT).show();
            editTextConfirmNewPassword.setError("As senhas nao possuem o mesmo valor! Tente novamente.");

            return;
        }

        if(newPassword.length() < 4){
            Toast.makeText(this, "A nova senha deve ter pelo menos 4 caracteres.", Toast.LENGTH_SHORT).show();
            editTextNewPassword.setError("A nova senha deve ter pelo menos 4 caracteres.");
            return;
        }

        User user = database.checkUser(currentUserName, currentPassword);

        if(user == null){
            Toast.makeText(this, "Senha atual incorreta." , Toast.LENGTH_LONG).show();
            editTextCurrentPassword.setError("Senha incorreta.");
            return;
        }

        boolean passwordUpdated = database.updatePassword(currentUserId , newPassword);

        if(passwordUpdated){
            Toast.makeText(this, "Senha atualizada com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Erro ao atualizar a senha.", Toast.LENGTH_LONG).show();
        }
    }
}