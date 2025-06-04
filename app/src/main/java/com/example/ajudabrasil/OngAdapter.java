package com.example.ajudabrasil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<OngAdapter.OngViewHolder> {

    private List<User> ongList;
    private Context context;

    private int userIdDoador;

    public OngAdapter(List<User> ongList, Context context, int userIdDoador) {
        this.ongList = ongList;
        this.context = context;
        this.userIdDoador = userIdDoador;
    }

    @NonNull
    @Override
    public OngViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ong, parent, false);
        return new OngViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OngViewHolder holder, int position) {
        User currentUser = ongList.get(position);
        holder.textViewNomeOng.setText(currentUser.getUsername());
        holder.textViewTipoCadastroOng.setText("Tipo: " + currentUser.getTipoCadastro());

        holder.buttonRealizarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doacaoIntent = new Intent(context, DoacaoActivity.class);
                doacaoIntent.putExtra("ONG_ID", currentUser.getId());
                doacaoIntent.putExtra("ONG_NAME", currentUser.getUsername());
                doacaoIntent.putExtra("USER_ID_DOADOR", userIdDoador);
                context.startActivity(doacaoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ongList != null ? ongList.size() : 0;
    }

    static class OngViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomeOng;
        TextView textViewTipoCadastroOng;
        Button buttonRealizarDoacao;

        public OngViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeOng = itemView.findViewById(R.id.textViewNomeOng);
            textViewTipoCadastroOng = itemView.findViewById(R.id.textViewTipoCadastroOng);
            buttonRealizarDoacao = itemView.findViewById(R.id.buttonRealizarDoacao);
        }
    }

    public void setOngList(List<User> ongList) {
        this.ongList = ongList;
        notifyDataSetChanged();
    }
}
