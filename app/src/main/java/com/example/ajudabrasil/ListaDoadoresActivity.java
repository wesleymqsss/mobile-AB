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

public class ListaDoadoresActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoadores;
    private DoadorAdapter doadorAdapter;
    private List<User> listaDeDoadores;
    private DataBase database;
    private TextView textViewListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_doadores);

        Toolbar toolbar = findViewById(R.id.toolbar_lista_doadores);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Empresas Parceiras/Doadores");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerViewDoadores = findViewById(R.id.recyclerViewDoadores);
        textViewListaVazia = findViewById(R.id.textViewListaVazia);
        database = new DataBase(this);
        listaDeDoadores = new ArrayList<>();

        // Configurar RecyclerView
        recyclerViewDoadores.setLayoutManager(new LinearLayoutManager(this));
        doadorAdapter = new DoadorAdapter(listaDeDoadores);
        recyclerViewDoadores.setAdapter(doadorAdapter);

        carregarDoadores();
    }
    private void carregarDoadores() {
        List<User> doadores = database.getUsuariosPorTipo("Doador");

        if (doadores != null && !doadores.isEmpty()) {
            listaDeDoadores.clear();
            listaDeDoadores.addAll(doadores);
            doadorAdapter.notifyDataSetChanged();
            recyclerViewDoadores.setVisibility(View.VISIBLE);
            textViewListaVazia.setVisibility(View.GONE);
        } else {
            recyclerViewDoadores.setVisibility(View.GONE);
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