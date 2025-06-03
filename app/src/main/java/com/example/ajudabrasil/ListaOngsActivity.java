package com.example.ajudabrasil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListaOngsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOngs;
    private OngAdapter ongAdapter;
    private List<User> listaDeOngs;
    private DataBase database;
    private TextView textViewListaVazia;
    private int userIdDoador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_ongs);

        recyclerViewOngs = findViewById(R.id.recyclerViewOngs);
        textViewListaVazia = findViewById(R.id.textViewListaVazia);
        database = new DataBase(this);
        listaDeOngs = new ArrayList<>();
        Intent intent = getIntent();
        userIdDoador = intent.getIntExtra("USER_ID_DOADOR", -1);

        Toolbar toolbar = findViewById(R.id.toolbar_lista_ongs);
        setSupportActionBar(toolbar);

        if (userIdDoador == -1) {
            Toast.makeText(this, "Erro: ID do doador não encontrado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ONG's para doações");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Configurar RecyclerView
        recyclerViewOngs.setLayoutManager(new LinearLayoutManager(this));
        ongAdapter = new OngAdapter(listaDeOngs, this, userIdDoador);
        recyclerViewOngs.setAdapter(ongAdapter);

        carregarOngs();
    }
    private void carregarOngs() {
        List<User> doadores = database.getUsuariosPorTipo("ONG");

        if (doadores != null && !doadores.isEmpty()) {
            listaDeOngs.clear();
            listaDeOngs.addAll(doadores);
            ongAdapter.setOngList(listaDeOngs);
            recyclerViewOngs.setVisibility(View.VISIBLE);
            textViewListaVazia.setVisibility(View.GONE);
        } else {
            recyclerViewOngs.setVisibility(View.GONE);
            textViewListaVazia.setVisibility(View.VISIBLE);
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