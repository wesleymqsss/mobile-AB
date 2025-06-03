package com.example.ajudabrasil;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_ongs);

        Toolbar toolbar = findViewById(R.id.toolbar_lista_ongs);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ONG's para doações");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerViewOngs = findViewById(R.id.recyclerViewOngs);
        textViewListaVazia = findViewById(R.id.textViewListaVazia);
        database = new DataBase(this);
        listaDeOngs = new ArrayList<>();

        // Configurar RecyclerView
        recyclerViewOngs.setLayoutManager(new LinearLayoutManager(this));
        ongAdapter = new OngAdapter(listaDeOngs);
        recyclerViewOngs.setAdapter(ongAdapter);

        carregarDoadores();
    }
    private void carregarDoadores() {
        List<User> doadores = database.getUsuariosPorTipo("ONG");

        if (doadores != null && !doadores.isEmpty()) {
            listaDeOngs.clear();
            listaDeOngs.addAll(doadores);
            ongAdapter.notifyDataSetChanged();
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