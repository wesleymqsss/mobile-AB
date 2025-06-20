package com.example.ajudabrasil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button buttonGoToRegister;
    private Button buttonGoToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        buttonGoToRegister = findViewById(R.id.buttonGoToRegister);
        buttonGoToLogin = findViewById(R.id.buttonGoToLogin);

        buttonGoToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent intent =  new Intent(MainActivity.this, RegistrarActivity.class);
               startActivity(intent);
            }
        });

        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}