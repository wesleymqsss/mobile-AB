package com.example.ajudabrasil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewWelcomeHome;
    private int currentUserId;
    private String currentUserName;
    private Button buttonVerMinhasDoacoes;
    private Button buttonVerEmpresas;
    private Button buttonVerOngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Pagina inicial");
        }

        textViewWelcomeHome = findViewById(R.id.textViewWelcomeHome);
        buttonVerMinhasDoacoes = findViewById(R.id.buttonVerMinhasDoacoes);
        buttonVerEmpresas = findViewById(R.id.buttonVerEmpresas);
        buttonVerOngs = findViewById(R.id.buttonVerOngs);

        // Obter os dados do usuurio da Intent que chamou a home activity
        Intent intent = getIntent();
        currentUserId = intent.getIntExtra("USER_ID", -1);
        currentUserName =  intent.getStringExtra("USER_NAME");

        if(currentUserId == -1){
            Toast.makeText(this, "Error: usuario nao foi indentificado.", Toast.LENGTH_LONG).show();

            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return;
        }

        if(currentUserName != null && !currentUserName.isEmpty()){
            textViewWelcomeHome.setText("Olá, " + currentUserName + "!");
        }else {
            textViewWelcomeHome.setText("Bem-vindo!");
        }

        buttonVerMinhasDoacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Botão Minhas Doações clicado!", Toast.LENGTH_SHORT).show();
                // No futuro:
                // Intent intentMinhasDoacoes = new Intent(HomeActivity.this, MinhasDoacoesActivity.class);
                // intentMinhasDoacoes.putExtra("USER_ID", currentUserId);
                // startActivity(intentMinhasDoacoes);
            }
        });

        buttonVerEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmpresas = new Intent(HomeActivity.this, ListaDoadoresActivity.class);
                startActivity(intentEmpresas);
            }
        });

        buttonVerOngs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOngs = new Intent(HomeActivity.this, ListaOngsActivity.class);
                intentOngs.putExtra("USER_ID_DOADOR", currentUserId);
                startActivity(intentOngs);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_edit_password) {
           Intent editPasswordIntent = new Intent(HomeActivity.this, EditPasswordActivity.class);
           editPasswordIntent.putExtra("USER_ID", currentUserId);
           editPasswordIntent.putExtra("USER_NAME", currentUserName);
           startActivity(editPasswordIntent);

            return true;
        }else if (itemId == R.id.action_logout){
            Toast.makeText(this,"Opcao sair", Toast.LENGTH_SHORT).show();

            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);

            //limpar dados da sessao
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(loginIntent);
            finish(); //fechar a home activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}