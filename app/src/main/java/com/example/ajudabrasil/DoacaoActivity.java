package com.example.ajudabrasil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoacaoActivity extends AppCompatActivity {
    private TextView textViewNomeOngDoacao;
    private EditText editTextValorDoacao;
    private Button buttonConfirmarDoacao;
    private DataBase database;
    private int ongIdRecebedora;
    private String ongNomeRecebedora;
    private int userIdDoador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doacao);

        Toolbar toolbar = findViewById(R.id.toolbar_doacao);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Realizar Doação");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewNomeOngDoacao = findViewById(R.id.textViewNomeOngDoacao);
        editTextValorDoacao = findViewById(R.id.editTextValorDoacao);
        buttonConfirmarDoacao = findViewById(R.id.buttonConfirmarDoacao);
        database = new DataBase(this);

        Intent intent = getIntent();
        ongIdRecebedora = intent.getIntExtra("ONG_ID", -1);
        ongNomeRecebedora = intent.getStringExtra("ONG_NAME");
        userIdDoador = intent.getIntExtra("USER_ID_DOADOR", -1);

        if (ongIdRecebedora == -1 || userIdDoador == -1 || ongNomeRecebedora == null) {
            Toast.makeText(this, "Erro: Dados da ONG ou doador não encontrados.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        textViewNomeOngDoacao.setText(ongNomeRecebedora);

        buttonConfirmarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDoacao();
            }
        });
    }

    private void registrarDoacao(){
        String valorDoacaoStr = editTextValorDoacao.getText().toString().trim();
        if (valorDoacaoStr.isEmpty()) {
            editTextValorDoacao.setError("Valor é obrigatório");
            Toast.makeText(this, "Por favor, insira o valor da doação.", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorDoacaoStr);
            if (valor <= 0) {
                editTextValorDoacao.setError("Valor deve ser maior que zero");
                Toast.makeText(this, "Valor da doação deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            editTextValorDoacao.setError("Valor inválido");
            Toast.makeText(this, "Por favor, insira um valor numérico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dataAtual = sdf.format(new Date());

        Doacao novaDoacao = new Doacao(userIdDoador, ongIdRecebedora, valor, dataAtual, ongNomeRecebedora);
        long resultado = database.addDoacao(novaDoacao);

        if (resultado != -1) {
            Toast.makeText(this, "Doação registrada com sucesso! Obrigado.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao registrar doação. Tente novamente.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}