package com.example.ajudabrasil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MinhasDoacoesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMinhasDoacoes;
    private MinhaDoacaoAdapter minhaDoacaoAdapter;
    private List<Doacao> listaDeMinhasDoacoes;
    private DataBase databaseHelper;
    private TextView textViewMinhasDoacoesVazio;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_doacoes);

        Toolbar toolbar = findViewById(R.id.toolbar_minhas_doacoes);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Minhas Doações");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerViewMinhasDoacoes = findViewById(R.id.recyclerViewMinhasDoacoes);
        textViewMinhasDoacoesVazio = findViewById(R.id.textViewMinhasDoacoesVazio);
        databaseHelper = new DataBase(this);
        listaDeMinhasDoacoes = new ArrayList<>();

        Intent intent = getIntent();
        currentUserId = intent.getIntExtra("USER_ID", -1);

        if (currentUserId == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        recyclerViewMinhasDoacoes.setLayoutManager(new LinearLayoutManager(this));
        minhaDoacaoAdapter = new MinhaDoacaoAdapter(listaDeMinhasDoacoes, this);
        recyclerViewMinhasDoacoes.setAdapter(minhaDoacaoAdapter);

        carregarMinhasDoacoes();
    }

    private void carregarMinhasDoacoes() {
        List<Doacao> doacoes = databaseHelper.getDoacoesPorUsuario(currentUserId);

        if (doacoes != null && !doacoes.isEmpty()) {
            minhaDoacaoAdapter.setDoacaoList(doacoes);
            recyclerViewMinhasDoacoes.setVisibility(View.VISIBLE);
            textViewMinhasDoacoesVazio.setVisibility(View.GONE);
        } else {
            recyclerViewMinhasDoacoes.setVisibility(View.GONE);
            textViewMinhasDoacoesVazio.setVisibility(View.VISIBLE);
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